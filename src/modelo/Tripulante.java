package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Direccion;
import java.util.ArrayList;
import java.util.List;

public class Tripulante extends Persona {
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
}