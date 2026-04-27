public class Rut implements IdPersona {
    private int numero;
    private char dv;

    public Rut(int numero, char dv){
        this.numero= numero;
        this.dv= dv;
    }

    public int getNumero() {
        return numero;
    }

    public char getDv() {
        return dv;
    }

    public static Rut of(String rutConDv){
        try {
            String[] partes = rutConDv.split("-");


            String numeroSinPuntos = partes[0].replace(".", "");

            int numero = Integer.parseInt(numeroSinPuntos);
            char dv = partes[1].charAt(0);

            return new Rut(numero, dv);
        } catch (Exception e) {

            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%,d-%c", numero, dv).replace(',', '.');
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (otro == null || getClass() != otro.getClass()) return false;

        Rut rutOtro = (Rut) otro;
        return this.numero == rutOtro.numero && this.dv == rutOtro.dv;
    }
}