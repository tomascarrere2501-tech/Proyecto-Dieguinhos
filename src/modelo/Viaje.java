package modelo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Viaje {
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private int duracion;
    private Bus bus;
    private Auxiliar auxiliar;
    private ArrayList<Conductor> conductores;
    private Terminal terminalSalida;
    private Terminal terminalLlegada;
    private ArrayList<Pasaje> listaPasajes;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus ) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
        this.duracion = duracion;
        this.listaPasajes = new ArrayList<>();
        this.auxiliar = auxiliar;
        this.terminalSalida = terminalSalida;
        this.terminalLlegada = terminalLlegada;
        this.conductores = new ArrayList<>();

        for( Conductor  c : conductores ){
            this.conductores.add(c);
            c.addViaje(this);
        }

        bus.addViaje(this);
        auxiliar.addViaje(this);
        terminalSalida.addSalida(this);
        terminalLlegada.addLlegada(this);
    }

    public LocalDate getFecha() { return this.fecha; }
    public LocalTime getHora() { return this.hora; }
    public int getPrecio() { return this.precio; }
    public void setPrecio(int precio) { this.precio = precio; }
    public int getDuracion() { return duracion; }
    public Bus getBus() { return this.bus; }
    public Auxiliar getAuxiliar() { return auxiliar; }
    public Conductor[] getConductores() { return conductores.toArray(new Conductor[0]); }
    public Terminal getTerminalSalida() { return terminalSalida; }
    public Terminal getTerminalLlegada() { return terminalLlegada; }

    public void addPasaje(Pasaje pasaje) {
        this.listaPasajes.add(pasaje);
    }
    public int getNroAsientosDisponibles() {
        return this.bus.getNroAsientos() - this.listaPasajes.size();
    }

    public boolean existeDisponibilidad(int cant) {
        return getNroAsientosDisponibles() >= cant;
    }
    public LocalDateTime getFechaHoraTermino() {
        LocalDateTime salida = LocalDateTime.of(fecha, hora);
        return salida.plusMinutes(duracion);
    }

    public String[][] getListaPasajeros() {
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
    public Venta[] getVentas() {
        Set<Venta> ventasUnicas = new HashSet<>();
        for (Pasaje p : listaPasajes) {
            ventasUnicas.add(p.getVenta());
        }
        return ventasUnicas.toArray(new Venta[0]);
    }
    public String[] getAsientos() {
        int totalAsientos = this.bus.getNroAsientos();
        String[] asientos = new String[totalAsientos];
        for (int i = 0; i < totalAsientos; i++) {
            int numeroAsiento = i + 1;
            boolean ocupado = false;
            for (Pasaje p : listaPasajes) {
                if (p.getAsiento() == numeroAsiento) {
                    ocupado = true;
                    break;
                }
            }
            asientos[i] = ocupado ? "*" : String.valueOf(numeroAsiento);
        }
        return asientos;
    }
}
