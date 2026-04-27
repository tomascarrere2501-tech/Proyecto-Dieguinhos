public class Pasaporte implements IdPersona {
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

    @Override
    public String toString() {
        return numero + " " + nacionalidad;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (otro == null || getClass() != otro.getClass()) return false;
        Pasaporte pasaporte = (Pasaporte) otro;
        return this.numero.equals(pasaporte.numero) && this.nacionalidad.equals(pasaporte.nacionalidad);
    }
}