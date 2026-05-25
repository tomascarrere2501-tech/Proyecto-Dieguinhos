package modelo;
import utilidades.*;
public class Conductor extends Tripulante {
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
