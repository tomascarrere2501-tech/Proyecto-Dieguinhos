public class Pasajero{
    private Nombre nomContacto;
    private String fonoContacto;

    public Pasajero(Nombre nomContacto, String fonoContacto){}
    //Metodo 1
    public Nombre getNomContacto() {
        return nomContacto;
    }

    //Metodo 2
    public void setNomContacto(Nombre nom) {
        this.nomContacto = nom;
    }

    //Metodo 3
    public String getFonoContacto() {
        return fonoContacto;
    }

    //Metodo 4
    public void setFonoContacto(String fono) {
        this.fonoContacto = fono;
    }
}
