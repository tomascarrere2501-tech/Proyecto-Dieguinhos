public class Pasaporte {

    private String numero;
    private String nacionalidad;

    public Pasaporte(String num, String nacionalidad) {
        this.numero = num;
        this.nacionalidad = nacionalidad;
    }

    public String getNumero() {
        return numero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public static Pasaporte of(String num, String nacionalidad) {
        return new Pasaporte(num, nacionalidad);
    }
}
