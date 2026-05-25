package modelo;

import utilidades.*;
import java.util.*;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;
    private List<Bus> buses;
    private List<Tripulante> tripulantes;


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
            for (Viaje v : b.getViaje()) {
                for (Venta venta : v.getVentas()) {
                    ventasUnicas.add(venta);
                }
            }
        }
        return ventasUnicas.toArray(new Venta[0]);
    }
    public Bus getBus(String patente) {
        for(Bus b : buses) {
            if (b.getPatente().equals(patente)) {
                return b;
            }
        }
        System.out.println("No existe bus con la patente indicada");
        return null;
    }

    public Bus getAuxiliar(IdPersona id) {
        for(Bus p : buses) {
            if (p.getPatente().equals(id)) {
                return p;
            }
        }
        System.out.println("No existe bus con la patente indicada");
        return null;
    }
}