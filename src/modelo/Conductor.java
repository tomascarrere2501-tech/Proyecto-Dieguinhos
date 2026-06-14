package modelo;
import utilidades.*;

import java.io.Serializable;

public class Conductor extends Tripulante implements Serializable {
    private static final long serialVersionUID = 1L;
    public Conductor(  IdPersona id , Nombre nombre ,  Direccion dir  ){
        super(id,nombre,dir);

    }

    @Override
    public void addViaje(Viaje viaje) {
        super.addViaje(viaje);
    }
    public int getNroViajes(){
        return super.getNroViajes();
    }

}
