public class Pasajero {
    //Atributos
    private Nombre nomContacto;
    private String fonoContacto;

    //Constructor
    public Pasajero(Nombre nomContacto, String fonoContacto){}
    //Metodo getNomContacto
    public Nombre getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(Nombre nom) {
        this.nomContacto = nom;
    } //Metodo setNomContacto
    
    public String getFonoContacto() {
        return fonoContacto;
    }  //Metodo getFonoContacto

    public void setFonoContacto(String fono) {
        this.fonoContacto = fono;
    } //Metodo setFonoContacto
}
