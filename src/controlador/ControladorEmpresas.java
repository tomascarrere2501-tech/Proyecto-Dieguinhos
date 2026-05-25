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

    public void createBus(String patente, String marca, String modelo, int nroAsientos, Rut rutEmp) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmp);
        if (!empresaOpt.isPresent()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }

        for (Empresa e : empresas) {
            if (e.getBus(patente).isPresent()) {
                throw new SistemaVentaPasajesException("Ya existe bus con la patente indicada");
            }
        }

        Empresa empresa = empresaOpt.get();
        empresa.addBus(new Bus(patente, marca, modelo, nroAsientos, empresa));
    }

    public void createTerminal(String nombre, Direccion direccion) {
        if (findTerminal(nombre).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe terminal con el nombre indicado");
        }
        if (findTerminalByComuna(direccion.getComuna()).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe terminal en la comuna indicada");
        }
        terminales.add(new Terminal(nombre, direccion));
    }

    public void hireConductorForEmpresa(Rut rutEmp, IdPersona idPersona, Nombre nom, Direccion dir) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmp);
        if (!empresaOpt.isPresent()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }
        Empresa empresa = empresaOpt.get();
        boolean agregado = empresa.addConductor(idPersona, nom, dir);
        if (!agregado) {
            throw new SistemaVentaPasajesException("Ya esta contratado conductor/auxiliar con el id dado en la empresa senalada");
        }
    }

    public void hireAuxiliarForEmpresa(Rut rutEmp, IdPersona idPersona, Nombre nom, Direccion dir) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmp);
        if (!empresaOpt.isPresent()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }
        Empresa empresa = empresaOpt.get();
        boolean agregado = empresa.addAuxiliar(idPersona, nom, dir);
        if (!agregado) {
            throw new SistemaVentaPasajesException("Ya esta contratado auxiliar/conductor con el id dado en la empresa senalada");
        }
    }

    public Optional<Empresa> findEmpresa(Rut rut) {
        for (Empresa e : empresas) {
            if (e.getRut().equals(rut)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    public Optional<Terminal> findTerminal(String nombre) {
        for (Terminal t : terminales) {
            if (t.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public Optional<Terminal> findTerminalByComuna(String comuna) {
        for (Terminal t : terminales) {
            if (t.getDireccion().getComuna().equalsIgnoreCase(comuna)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public Optional<Conductor> findConductor(IdPersona idPersona, Rut rutEmpresa) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmpresa);
        if (empresaOpt.isPresent()) {
            return empresaOpt.get().getConductor(idPersona);
        }
        return Optional.empty();
    }

    public Optional<Auxiliar> findAuxiliar(IdPersona idPersona, Rut rutEmpresa) {
        Optional<Empresa> empresaOpt = findEmpresa(rutEmpresa);
        if (empresaOpt.isPresent()) {
            return empresaOpt.get().getAuxiliar(idPersona);
        }
        return Optional.empty();
    }

    public String[][] listEmpresas() {
        return new String[0][0];
    }

    public String[][] listLlegadasSalidasTerminal(String nombreTerminal, LocalDate fecha) {
        if (!findTerminal(nombreTerminal).isPresent()) {
            throw new SistemaVentaPasajesException("No existe un terminal con el nombre dado");
        }
        return new String[0][0];
    }

    public String[][] listVentasEmpresa(Rut rutEmp) {
        if (!findEmpresa(rutEmp).isPresent()) {
            throw new SistemaVentaPasajesException("No existe una empresa con el rut indicado");
        }
        return new String[0][0];
    }
}