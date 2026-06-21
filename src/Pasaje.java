package modelo;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Pasaje implements Serializable {
    private static final long serialVersionUID = 1L;
    private long numero;
    private int asiento;
    private Viaje viaje;
    private Pasajero pasajero;
    private Venta venta;

    public Pasaje(int asiento, Viaje viaje, Pasajero pasajero, Venta venta) {
        this.asiento = asiento;
        this.viaje = viaje;
        this.pasajero = pasajero;
        this.venta = venta;
        this.numero = System.currentTimeMillis();

        viaje.addPasaje(this);
    }

    public long getNumero() {
        return numero;
    }

    public int getAsiento() {
        return asiento;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Venta getVenta() {
        return venta;
    }


    @Override
    public String toString() {
        String nombreEmpresa = viaje.getBus().getEmpresa().getNombre().toUpperCase();
        String nombrePasajero = pasajero.getNombreCompleto().toString().toUpperCase();
        String idPasajero = pasajero.getIdPersona().toString();
        String patenteBus = viaje.getBus().getPatente().toUpperCase();
        String terminalOrigen = viaje.getTerminalSalida().getNombre().toUpperCase();
        String terminalDestino = viaje.getTerminalLlegada().getNombre().toUpperCase();
        String fecha = viaje.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = viaje.getHora().toString();
        int valorPagado = viaje.getPrecio();

        StringBuilder sb = new StringBuilder();
        sb.append("\n+------------------------------------------+\n");
        sb.append("|            PASAJE ELECTRÓNICO            |\n");
        sb.append("+------------------------------------------+\n");
        sb.append(String.format("| %-40s |%n", "EMPRESA: " + nombreEmpresa));
        sb.append(String.format("| Nº Pasaje: %-29d |%n", numero));
        sb.append("+------------------------------------------+\n");
        sb.append(String.format("| Pasajero: %-30s |%n", nombrePasajero));
        sb.append(String.format("| ID/RUT:   %-30s |%n", idPasajero));
        sb.append("+------------------------------------------+\n");
        sb.append(String.format("| Recorrido: %-29s |%n", terminalOrigen));
        sb.append(String.format("| Hacia:     %-29s |%n", terminalDestino));
        sb.append("+------------------------------------------+\n");
        sb.append(String.format("| Fecha: %-11s | Hora: %-12s |%n", fecha, hora));
        sb.append(String.format("| Bus:   %-11s | Asiento: %-9d |%n", patenteBus, asiento));
        sb.append("+------------------------------------------+\n");
        sb.append(String.format("| TOTAL PAGADO:                 $%,-10d |%n", valorPagado));
        sb.append("+------------------------------------------+\n");

        return sb.toString();
    }
}