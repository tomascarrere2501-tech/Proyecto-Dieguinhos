import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Viaje {
    //Atributos
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private Bus bus;
    private ArrayList<Pasaje> listaPasajes;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
        this.listaPasajes = new ArrayList<>();
    } //Constructor

    public LocalDate getFecha() {
        return this.fecha;
    } //Metodo getFecha

    public LocalTime getHora() {
        return this.hora;
    } //Metodo getHora

    public int getPrecio() {
        return this.precio;
    } //Metodo getPrecio

    public void setPrecio(int precio) {
        this.precio = precio;
    } //Metodo setPrecio

    public Bus getBus() {
        return this.bus;
    } //Metodo getBus

    public void addPasaje(Pasaje pasaje) {
        this.listaPasajes.add(pasaje);
    } //Metodo addPasaje

    public int getNroAsientosDisponibles() {
        if (this.bus != null) {
            return this.bus.getNroAsientos() - this.listaPasajes.size();
        }
        return 0;
    } //Metodo getNroAsientosDisponibles

    public boolean existeDisponibilidad() {
        if (this.getNroAsientosDisponibles() > 0) {
            return true;
        }
        return false;
    } //Metodo existeDisponibilidad

    public String[][] getListaPasajeros() {
        return new String[0][0];
    } //getListaPasajeros

    public String[][] getAsientos() {
        return new String[0][0];
    } //getAsientos
}
