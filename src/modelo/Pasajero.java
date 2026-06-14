package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;

import java.io.Serializable;

public class Pasajero extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private Nombre nomContacto;
    private String fonoContacto;

    public Pasajero(IdPersona id, Nombre nombre) {
        super(id, nombre);
    }

    public Nombre getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(Nombre nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getFonoContacto() {
        return fonoContacto;
    }

    public void setFonoContacto(String fonoContacto) {
        this.fonoContacto = fonoContacto;
    }
}