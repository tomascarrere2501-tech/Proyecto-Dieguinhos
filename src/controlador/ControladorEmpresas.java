package controlador;

import excepciones.SistemaVentaPasajesException;
import modelo.*;
import utilidades.*;

import java.util.ArrayList;
import java.util.Date;
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
        String[][] matriz = new String[empresas.size()][3];

        for (int i = 0; i < empresas.size(); i++) {
            Empresa empresa = empresas.get(i);

            matriz[i][0] = empresa.getRut().toString();
            matriz[i][1] = empresa.getNombre();
            matriz[i][2] = empresa.getUrl();
        }

        return matriz;
    }

    public String[][] listLlegadasSalidasTerminal(String nombre, Date fecha) {
        if (!findTerminal(nombre).isPresent()) {
            throw new SistemaVentaPasajesException("No existe un terminal con el nombre dado");
        }

        return new String[0][0];
    }

    public String[][] listVentasEmpresa(Rut rut) {
        if (!findEmpresa(rut).isPresent()) {
            throw new SistemaVentaPasajesException("No existe una empresa con el rut indicado");
        }

        return new String[0][0];
    }

    protected Optional<Empresa> findEmpresa(Rut rut) {
        for (Empresa empresa : empresas) {
            if (empresa.getRut().equals(rut)) {
                return Optional.of(empresa);
            }
        }

        return Optional.empty();
    }

    protected Optional<Terminal> findTerminal(String nombre) {
        for (Terminal terminal : terminales) {
            if (terminal.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(terminal);
            }
        }

        return Optional.empty();
    }

    protected Optional<Terminal> findTerminalPorComuna(String comuna) {
        for (Terminal terminal : terminales) {
            if (terminal.getDireccion().getComuna().equalsIgnoreCase(comuna)) {
                return Optional.of(terminal);
            }
        }

        return Optional.empty();
    }

    protected Optional<Bus> findBus(String patente) {
        for (Empresa empresa : empresas) {
            Optional<Bus> bus = empresa.getBus(patente);

            if (bus.isPresent()) {
                return bus;
            }
        }

        return Optional.empty();
    }

    protected Optional<Conductor> findConductor(IdPersona id, Rut rutEmpresa) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmpresa);

        if (empresaOpt.isPresent()) {
            return empresaOpt.get().getConductor(id);
        }

        return Optional.empty();
    }

    protected Optional<Auxiliar> findAuxiliar(IdPersona id, Rut rutEmpresa) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmpresa);

        if (empresaOpt.isPresent()) {
            return empresaOpt.get().getAuxiliar(id);
        }

        return Optional.empty();
    }
}
