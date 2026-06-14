package modelo;

import utilidades.*;

import java.io.Serializable;
import java.util.*;

public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;

    // Atributos
    private Rut rut;
    private String nombre;
    private String url;
    private List<Bus> buses;
    private List<Tripulante> tripulantes;

    // Constructor
    public Empresa(Rut rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
        this.buses = new ArrayList<>();
        this.tripulantes = new ArrayList<>();
    }

    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addBus(Bus bus) {
        this.buses.add(bus);
    }

    public Bus[] getBuses() {
        return this.buses.toArray(new Bus[0]);
    }

    public boolean addConductor(IdPersona id, Nombre nom, Direccion dir) {
        for (Tripulante t : tripulantes) {
            if (t.getIdPersona().equals(id)) {
                return false;
            }
        }
        this.tripulantes.add(new Conductor(id, nom, dir));
        return true;
    }

    public boolean addAuxiliar(IdPersona id, Nombre nom, Direccion dir) {
        for (Tripulante t : tripulantes) {
            if (t.getIdPersona().equals(id)) {
                return false;
            }
        }
        this.tripulantes.add(new Auxiliar(id, nom, dir));
        return true;
    }

    public Tripulante[] getTripulantes() {
        return this.tripulantes.toArray(new Tripulante[0]);
    }

    public Venta[] getVentas() {
        Set<Venta> ventasUnicas = new HashSet<>();
        for (Bus b : buses) {
            for (Viaje v : b.getViajes()) {
                for (Venta venta : v.getVentas()) {
                    ventasUnicas.add(venta);
                }
            }
        }
        return ventasUnicas.toArray(new Venta[0]);
    }

}