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

        bus.addViaje(this);
    }

    public LocalDate getFecha() { return this.fecha; }
    public LocalTime getHora() { return this.hora; }
    public int getPrecio() { return this.precio; }
    public void setPrecio(int precio) { this.precio = precio; }
    public Bus getBus() { return this.bus; }

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
        return this.getNroAsientosDisponibles() > 0;
    }

    public String[][] getListaPasajeros() {
        // CORRECCIÓN: Según PDF pág 14, deben ser 4 columnas (ID, nombre, nombre contacto, teléfono contacto)
        String[][] matrizPasajeros = new String[this.listaPasajes.size()][4];
        for (int i = 0; i < this.listaPasajes.size(); i++) {
            Pasaje p = this.listaPasajes.get(i);
            matrizPasajeros[i][0] = p.getPasajero().getIdPersona().toString();
            matrizPasajeros[i][1] = p.getPasajero().getNombreCompleto().toString();
            matrizPasajeros[i][2] = p.getPasajero().getNomContacto().toString();
            matrizPasajeros[i][3] = p.getPasajero().getFonoContacto();
        }
        return matrizPasajeros;
    }

    public String[][] getAsientos() {
        int totalAsientos = this.bus.getNroAsientos();
        String[][] asientos = new String[totalAsientos][2];

        for (int i = 0; i < totalAsientos; i++) {
            int numeroAsiento = i + 1;
            asientos[i][0] = String.valueOf(numeroAsiento);

            boolean ocupado = false;
            for (Pasaje p : listaPasajes) {
                if (p.getAsiento() == numeroAsiento) {
                    ocupado = true;
                    break;
                }
            }
            asientos[i][1] = ocupado ? "Ocupado" : "Libre";
        }
        return asientos;
    }
}