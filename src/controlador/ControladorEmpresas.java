package controlador;

import excepciones.SistemaVentaPasajesException;
import modelo.*;
import utilidades.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorEmpresas {

    private static ControladorEmpresas instance;
    private List<Empresa> empresas;
    private List<Terminal> terminales;

    private ControladorEmpresas() {
        this.empresas = new ArrayList<>();
        this.terminales = new ArrayList<>();
    }

    public static ControladorEmpresas getInstance() {
        if (instance == null) {
            instance = new ControladorEmpresas();
        }
        return instance;
    }

    public void createEmpresa(Rut rut, String nombre, String url) {
        if (findEmpresa(rut).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe empresa con el rut indicado");
        }

        Empresa nuevaEmpresa = new Empresa(rut, nombre);
        nuevaEmpresa.setUrl(url);
        empresas.add(nuevaEmpresa);
    }

    public void createBus(String pat, String marca, String modelo, int nroAsientos, Rut rutEmp) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmp);

        if (!empresaOpt.isPresent()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }

        if (findBus(pat).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe bus con la patente indicada");
        }

        Empresa empresa = empresaOpt.get();
        Bus bus = new Bus(pat, marca, modelo, nroAsientos, empresa);
        empresa.addBus(bus);
    }

    public void createTerminal(String nombre, Direccion direccion) {
        if (findTerminal(nombre).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe terminal con el nombre indicado");
        }

        if (findTerminalPorComuna(direccion.getComuna()).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe terminal en la comuna indicada");
        }

        Terminal terminal = new Terminal(nombre, direccion);
        terminales.add(terminal);
    }

    public void hireConductorForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmp);

        if (!empresaOpt.isPresent()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }

        Empresa empresa = empresaOpt.get();
        boolean agregado = empresa.addConductor(id, nom, dir);

        if (!agregado) {
            throw new SistemaVentaPasajesException("Ya esta contratado conductor o auxiliar con el id dado en la empresa senalada");
        }
    }

    public void hireAuxiliarForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmp);

        if (!empresaOpt.isPresent()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }

        Empresa empresa = empresaOpt.get();
        boolean agregado = empresa.addAuxiliar(id, nom, dir);

        if (!agregado) {
            throw new SistemaVentaPasajesException("Ya esta contratado auxiliar o conductor con el id dado en la empresa senalada");
        }
    }

    public String[][] listEmpresas() {
        String[][] matriz = new String[empresas.size()][6];

        for (int i = 0; i < empresas.size(); i++) {
            Empresa empresa = empresas.get(i);
            matriz[i][0] = empresa.getRut().toString();
            matriz[i][1] = empresa.getNombre();
            matriz[i][2] = empresa.getUrl();
            matriz[i][3] = String.valueOf(empresa.getTripulantes().length);
            matriz[i][4] = String.valueOf(empresa.getBuses().length);
            matriz[i][5] = String.valueOf(empresa.getVentas().length);
        }

        return matriz;
    }

    public String[][] listLlegadasSalidasTerminal(String nombre, LocalDate fecha) {
        Optional<Terminal> terminalOpt = findTerminal(nombre);
        if (!terminalOpt.isPresent()) {
            throw new SistemaVentaPasajesException("No existe un terminal con el nombre dado");
        }

        Terminal terminal = terminalOpt.get();
        List<String[]> viajesTerm = new ArrayList<>();

        for (Viaje v : terminal.getSalidas()) {
            if (v.getFecha().equals(fecha)) {
                viajesTerm.add(new String[]{
                        "Salida",
                        v.getHora().toString(),
                        v.getBus().getPatente(),
                        v.getBus().getEmpresa().getNombre(),
                        String.valueOf(v.getListaPasajeros().length)
                });
            }
        }

        for (Viaje v : terminal.getLlegadas()) {
            if (v.getFecha().equals(fecha)) {
                viajesTerm.add(new String[]{
                        "Llegada",
                        v.getFechaHoraTermino().toLocalTime().toString(),
                        v.getBus().getPatente(),
                        v.getBus().getEmpresa().getNombre(),
                        String.valueOf(v.getListaPasajeros().length)
                });
            }
        }

        return viajesTerm.toArray(new String[0][0]);
    }

    public String[][] listVentasEmpresa(Rut rut) {
        Optional<Empresa> empresaOpt = findEmpresa(rut);
        if (!empresaOpt.isPresent()) {
            throw new SistemaVentaPasajesException("No existe una empresa con el rut indicado");
        }

        Venta[] ventas = empresaOpt.get().getVentas();
        String[][] matriz = new String[ventas.length][4];

        for (int i = 0; i < ventas.length; i++) {
            Venta v = ventas[i];
            matriz[i][0] = v.getFecha().toString();
            matriz[i][1] = v.getTipo().toString();
            matriz[i][2] = String.valueOf(v.getMontoPagado());
            matriz[i][3] = v.getTipoPago() != null ? v.getTipoPago() : "Pendiente";
        }

        return matriz;
    }

    public Optional<Empresa> findEmpresa(Rut rut) {
        for (Empresa empresa : empresas) {
            if (empresa.getRut().equals(rut)) {
                return Optional.of(empresa);
            }
        }
        return Optional.empty();
    }

    public Optional<Terminal> findTerminal(String nombreOComuna) {
        for (Terminal terminal : terminales) {
            if (terminal.getNombre().equalsIgnoreCase(nombreOComuna) ||
                    terminal.getDireccion().getComuna().equalsIgnoreCase(nombreOComuna)) {
                return Optional.of(terminal);
            }
        }
        return Optional.empty();
    }

    public Optional<Terminal> findTerminalPorComuna(String comuna) {
        for (Terminal terminal : terminales) {
            if (terminal.getDireccion().getComuna().equalsIgnoreCase(comuna)) {
                return Optional.of(terminal);
            }
        }
        return Optional.empty();
    }

    public Optional<Bus> findBus(String patente) {
        for (Empresa empresa : empresas) {
            for (Bus bus : empresa.getBuses()) {
                if (bus.getPatente().equalsIgnoreCase(patente)) {
                    return Optional.of(bus);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Conductor> findConductor(IdPersona id, Rut rutEmpresa) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmpresa);
        if (empresaOpt.isPresent()) {
            for (Tripulante t : empresaOpt.get().getTripulantes()) {
                if (t instanceof Conductor && t.getIdPersona().equals(id)) {
                    return Optional.of((Conductor) t);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Auxiliar> findAuxiliar(IdPersona id, Rut rutEmpresa) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmpresa);
        if (empresaOpt.isPresent()) {
            for (Tripulante t : empresaOpt.get().getTripulantes()) {
                if (t instanceof Auxiliar && t.getIdPersona().equals(id)) {
                    return Optional.of((Auxiliar) t);
                }
            }
        }
        return Optional.empty();
    }
}