import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Viaje {

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
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public LocalTime getHora() {
        return this.hora;
    }

    public int getPrecio() {
        return this.precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Bus getBus() {
        return this.bus;
    }

    public void addPasaje(Pasaje pasaje) {
        this.listaPasajes.add(pasaje);
    }

    public int getNroAsientosDisponibles() {
        if (this.bus != null) {
            return this.bus.getNroAsientos() - this.listaPasajes.size();
        }
        return 0;
    }

    public boolean existeDisponibilidad() {
        if (this.getNroAsientosDisponibles() > 0) {
            return true;
        }
        return false;
    }

    public String[][] getListaPasajeros() {
        return new String[0][0];
    }

    public String[][] getAsientos() {
        return new String[0][0];
    }
}
