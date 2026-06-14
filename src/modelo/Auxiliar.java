package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Direccion;

import java.io.Serializable;

public class Auxiliar extends Tripulante implements Serializable {
    private static final long serialVersionUID = 1L;

    public Auxiliar(IdPersona id, Nombre nombre, Direccion direccion) {
        super(id, nombre, direccion);
    }

    @Override
    public void addViaje(Viaje viaje) {
        super.addViaje(viaje);
    }

    @Override
    public int getNroViajes() {
        return super.getNroViajes();
    }
}