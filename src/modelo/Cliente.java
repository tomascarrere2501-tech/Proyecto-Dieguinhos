package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;

import java.io.Serializable;

public class Cliente extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;

    public Cliente(IdPersona id, Nombre nom, String email) {
        super(id, nom);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}