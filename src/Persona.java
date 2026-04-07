import java.util.Objects;

public class Persona {
    private idPersona IdPersona;
    private nombreCompleto Nombre;
    private String telefono;
    public Persona(IdPersona id, Nombre nombre){
        this.IdPersona= id;
        this.Nombre= nombre;
    }

    public idPersona getIdPersona() {
        return IdPersona;
    }
    public Nombre getNombreCompleto(){
        return Nombre;

    }

    public void setNombreComleto(Nombre nombreCompleto){
        this.Nombre=nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(IdPersona, persona.IdPersona) && Objects.equals(Nombre, persona.Nombre) && Objects.equals(telefono, persona.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdPersona, Nombre, telefono);
    }
}
