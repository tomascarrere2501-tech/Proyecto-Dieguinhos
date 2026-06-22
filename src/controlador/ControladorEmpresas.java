package controlador;

import excepciones.SVPException;
import modelo.*;
import utilidades.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ControladorEmpresas implements Serializable {
    private static final long serialVersionUID = 1L;

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
            throw new SVPException("Ya existe empresa con el rut indicado");
        }

        Empresa nuevaEmpresa = new Empresa(rut, nombre);
        nuevaEmpresa.setUrl(url);
        empresas.add(nuevaEmpresa);
    }

    public void createBus(String pat, String marca, String modelo, int nroAsientos, Rut rutEmp) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmp);

        if (!empresaOpt.isPresent()) {
            throw new SVPException("No existe empresa con el rut indicado");
        }

        if (findBus(pat).isPresent()) {
            throw new SVPException("Ya existe bus con la patente indicada");
        }

        Empresa empresa = empresaOpt.get();
        Bus bus = new Bus(pat, marca, modelo, nroAsientos, empresa);
        empresa.addBus(bus);
    }

    public void createTerminal(String nombre, Direccion direccion) {
        if (findTerminal(nombre).isPresent()) {
            throw new SVPException("Ya existe terminal con el nombre indicado");
        }

        if (findTerminalPorComuna(direccion.getComuna()).isPresent()) {
            throw new SVPException("Ya existe terminal en la comuna indicada");
        }

        Terminal terminal = new Terminal(nombre, direccion);
        terminales.add(terminal);
    }

    public void hireConductorForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmp);

        if (!empresaOpt.isPresent()) {
            throw new SVPException("No existe empresa con el rut indicado");
        }

        Empresa empresa = empresaOpt.get();
        boolean agregado = empresa.addConductor(id, nom, dir);

        if (!agregado) {
            throw new SVPException("Ya esta contratado conductor o auxiliar con el id dado en la empresa senalada");
        }
    }

    public void hireAuxiliarForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmp);

        if (!empresaOpt.isPresent()) {
            throw new SVPException("No existe empresa con el rut indicado");
        }

        Empresa empresa = empresaOpt.get();
        boolean agregado = empresa.addAuxiliar(id, nom, dir);

        if (!agregado) {
            throw new SVPException("Ya esta contratado auxiliar o conductor con el id dado en la empresa senalada");
        }
    }

    public String[][] listEmpresas() {
        return empresas.stream()
                .map(e -> new String[]{
                        e.getRut().toString(),
                        e.getNombre(),
                        e.getUrl(),
                        String.valueOf(e.getTripulantes().length),
                        String.valueOf(e.getBuses().length),
                        String.valueOf(e.getVentas().length)
                })
                .toArray(String[][]::new);
    }

    public String[][] listLlegadasSalidasTerminal(String nombre, LocalDate fecha) {
        Terminal terminal = findTerminal(nombre)
                .orElseThrow(() -> new SVPException("No existe un terminal con el nombre dado"));

        Stream<String[]> salidas = Arrays.stream(terminal.getSalidas())
                .filter(v -> v.getFecha().equals(fecha))
                .map(v -> new String[]{
                        "Salida", v.getHora().toString(), v.getBus().getPatente(),
                        v.getBus().getEmpresa().getNombre(), String.valueOf(v.getListaPasajeros().length)
                });

        Stream<String[]> llegadas = Arrays.stream(terminal.getLlegadas())
                .filter(v -> v.getFecha().equals(fecha))
                .map(v -> new String[]{
                        "Llegada", v.getFechaHoraTermino().toLocalTime().toString(), v.getBus().getPatente(),
                        v.getBus().getEmpresa().getNombre(), String.valueOf(v.getListaPasajeros().length)
                });

        return Stream.concat(salidas, llegadas).toArray(String[][]::new);
    }

    public String[][] listVentasEmpresa(Rut rut) {
        Empresa empresa = findEmpresa(rut)
                .orElseThrow(() -> new SVPException("No existe una empresa con el rut indicado"));

        return Arrays.stream(empresa.getVentas())
                .map(v -> new String[]{
                        v.getFecha().toString(),
                        v.getTipo().toString(),
                        String.valueOf(v.getMontoPagado()),
                        v.getTipoPago() != null ? v.getTipoPago() : "Pendiente"
                })
                .toArray(String[][]::new);
    }

    public Optional<Empresa> findEmpresa(Rut rut) {
        return empresas.stream()
                .filter(emp -> emp.getRut().equals(rut))
                .findFirst();
    }

    public Optional<Terminal> findTerminal(String nombreOComuna) {
        return terminales.stream()
                .filter(t -> t.getNombre().equalsIgnoreCase(nombreOComuna) ||
                        t.getDireccion().getComuna().equalsIgnoreCase(nombreOComuna))
                .findFirst();
    }

    public Optional<Terminal> findTerminalPorComuna(String comuna) {
        return terminales.stream()
                .filter(t -> t.getDireccion().getComuna().equalsIgnoreCase(comuna))
                .findFirst();
    }

    public Optional<Bus> findBus(String patente) {
        return empresas.stream()
                .flatMap(emp -> Arrays.stream(emp.getBuses()))
                .filter(bus -> bus.getPatente().equalsIgnoreCase(patente))
                .findFirst();
    }

    public Optional<Conductor> findConductor(IdPersona id, Rut rutEmpresa) {
        return findEmpresa(rutEmpresa)
                .flatMap(empresa -> Arrays.stream(empresa.getTripulantes())
                        .filter(t -> t instanceof Conductor && t.getIdPersona().equals(id))
                        .map(t -> (Conductor) t)
                        .findFirst()
                );
    }

    public Optional<Auxiliar> findAuxiliar(IdPersona id, Rut rutEmpresa) {
        return findEmpresa(rutEmpresa)
                .flatMap(empresa -> Arrays.stream(empresa.getTripulantes())
                        .filter(t -> t instanceof Auxiliar && t.getIdPersona().equals(id))
                        .map(t -> (Auxiliar) t)
                        .findFirst()
                );
    }

    protected void setDatosIniciales(Object[] objetos) {
        this.empresas.clear();
        this.terminales.clear();

        Arrays.stream(objetos)
                .filter(obj -> obj instanceof Empresa)
                .map(obj -> (Empresa) obj)
                .forEach(this.empresas::add);

        Arrays.stream(objetos)
                .filter(obj -> obj instanceof Terminal)
                .map(obj -> (Terminal) obj)
                .forEach(this.terminales::add);
    }

    protected void setInstanciaPersistente(ControladorEmpresas instanciaRecuperada) {
        ControladorEmpresas.instance = instanciaRecuperada;
    }
}