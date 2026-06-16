package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import java.io.Serializable;
import java.util.Optional;

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private final IdPersona idPersona;
    private final Nombre nombreCompleto;
    private final String telefono; 
  
    public Persona(IdPersona id, Nombre nombre, String telefono) {
        this.idPersona = id;
        this.nombreCompleto = nombre;
        this.telefono = telefono;
    }
  
    public Persona(IdPersona id, Nombre nombre) {
        this(id, nombre, null);
    }

    public IdPersona getIdPersona() {
        return idPersona;
    }

    public Nombre getNombreCompleto() {
        return nombreCompleto;
    }

    public Optional<String> getTelefono() {
        return Optional.ofNullable(telefono);
    }
  
    public Persona withTelefono(String nuevoTelefono) {
        return new Persona(this.idPersona, this.nombreCompleto, nuevoTelefono);
    }

    @Override
    public String toString() {
        return idPersona.toString() + ", " + nombreCompleto.toString() + getTelefono().map(t -> ", " + t).orElse(""); 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return this.idPersona.equals(persona.idPersona);
    }
}
