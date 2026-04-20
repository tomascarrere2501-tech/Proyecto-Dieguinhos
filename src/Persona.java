import java.util.Objects;

public class Persona {
    private IdPersona idPersona ;
    private Nombre nombreCompleto ;
    private String telefono;
    public Persona(IdPersona id, Nombre nombre){
        this.idPersona= id;
        this.nombreCompleto= nombre;
    }

    public IdPersona getIdPersona() {
        return idPersona;
    }
    public Nombre getNombreCompleto(){
        return nombreCompleto;

    }

    public void setNombreComleto(Nombre nombreCompleto){
        this.nombreCompleto=nombreCompleto;
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
        return Objects.equals(idPersona, persona.idPersona) && Objects.equals(nombreCompleto, persona.nombreCompleto) && Objects.equals(telefono, persona.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersona, nombreCompleto, telefono);
    }
}
//corregido por tomasCarrere