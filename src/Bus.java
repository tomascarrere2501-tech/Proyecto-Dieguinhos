import java.util.ArrayList;

public class Bus {

    //Atributos
    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private ArrayList<Viaje> listaViajes;

    public Bus(String patente, int nroAsientos) {
        this.patente = patente;
        this.nroAsientos = nroAsientos;
        this.listaViajes = new ArrayList<>();
    } //Constructor

    public String getPatente() {
        return patente;
    } //Metodo getPatente

    public String getMarca() {
        return marca;
    } //Metodo getMarca

    public void setMarca(String marca) {
        this.marca = marca;
    } //Metodo setMarca

    public String getModelo() {
        return modelo;
    } //Metodo getModelo

    public void setModelo(String modelo) {
        this.modelo = modelo;
    } //Metodo setModelo

    public int getNroAsientos() {
        return nroAsientos;
    } //Metodo getNroAsientos

    public void addViaje(Viaje viaje) {
        this.listaViajes.add(viaje);
    } //Metodo addViaje
}
