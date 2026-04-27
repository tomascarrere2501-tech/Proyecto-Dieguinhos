import java.util.Objects;

public class Nombre {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Tratamiento tratamiento;

    public Nombre(Tratamiento tratamiento, String nombres, String apellidoPaterno, String apellidoMaterno) {
        this.tratamiento = tratamiento;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    @Override
    public String toString() {
        return tratamiento + " " + nombres + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (otro == null || getClass() != otro.getClass()) return false;


        Nombre otroNombre = (Nombre) otro;

        return Objects.equals(nombres, otroNombre.nombres) &&
                Objects.equals(apellidoPaterno, otroNombre.apellidoPaterno) &&
                Objects.equals(apellidoMaterno, otroNombre.apellidoMaterno);
    }
}
