import java.util.Objects;

public class Nombre {
    private String nombre;
    private String apellidomaterno;
    private String apellidopaterno;
    private Tratamiento tratamiento;

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellido) {
        this.apellidomaterno = apellido;
    }


    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    @Override
    public String toString() {
        return nombre +  " " + " "+  apellidomaterno +" " + "  " +  apellidopaterno;

    }

    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (otro == null || getClass() != otro.getClass()) return false;
        Nombre nombre = (Nombre) otro;
        return Objects.equals(tratamiento, nombre.tratamiento) &&
                Objects.equals(nombre, nombre.nombre) &&
                Objects.equals(apellidopaterno, nombre.apellidopaterno) &&
                Objects.equals(apellidomaterno, nombre.apellidomaterno);

    }
}
