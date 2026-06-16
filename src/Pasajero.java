package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import java.io.Serializable;
import java.util.Optional;

public class Pasajero extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private Nombre nomContacto;
    private String fonoContacto;

    public Pasajero(IdPersona id, Nombre nombre) {
        super(id, nombre);
    }
  
    public Optional<Nombre> getNomContacto() {
        return Optional.ofNullable(nomContacto); [cite: 255]
    }

    public void setNomContacto(Nombre nomContacto) {
        this.nomContacto = nomContacto;
    }

    public Optional<String> getFonoContacto() {
        return Optional.ofNullable(fonoContacto); [cite: 255]
    }

    public void setFonoContacto(String fonoContacto) {
        this.fonoContacto = fonoContacto;
    }
}
