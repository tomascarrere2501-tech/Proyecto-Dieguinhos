public class Rut {
    private int numero;
    private char dv;

    private Rut(int numero, char dv){
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
        String[] partes = rutConDv.split("-");

        int numero = Integer.parseInt(partes[0]);
        char dv = partes[1].charAt(0);

        return new Rut(numero, dv);
    }
}