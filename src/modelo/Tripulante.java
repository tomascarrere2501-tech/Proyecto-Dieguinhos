package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Direccion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tripulante extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private Direccion direccion;
    private List<Viaje> viajesRealizados;

    public Tripulante(IdPersona id, Nombre nombre, Direccion direccion) {
        super(id, nombre);
        this.direccion = direccion;
        this.viajesRealizados = new ArrayList<>();
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void addViaje(Viaje viaje) {
        this.viajesRealizados.add(viaje);
    }

    public int getNroViajes() {
        return this.viajesRealizados.size();
    }
    
    public void mostrarViajes() {
        this.viajesRealizados.forEach(viaje -> System.out.println(viaje.toString()));
    }
    
    public List<Viaje> getViajesRealizados() {
        return this.viajesRealizados.stream().collect(Collectors.toList());
    }
    
    public List<Viaje> filtrarViajesPorDestino(String destinoBuscado) {
        return this.viajesRealizados.stream().filter(viaje -> viaje.getDestino().equalsIgnoreCase(destinoBuscado)).collect(Collectors.toList());
    }
}
