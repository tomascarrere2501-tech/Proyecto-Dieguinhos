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

    public int getNumero() {
        return (int) numero;
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
        String patenteBus = viaje.getBus().getPatente();
        String terminalOrigen = viaje.getTerminalSalida().getNombre().toUpperCase();
        String terminalDestino = viaje.getTerminalLlegada().getNombre().toUpperCase();
        String fecha = viaje.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = viaje.getHora().toString();
        int valorPagado = viaje.getPrecio();

        String resultado = "";
        resultado += "-------------------- PASAJE ELECTRÓNICO --------------------\n";
        resultado += String.format("%-20s %s\n", "Nombre Empresa", "Número de pasaje");
        resultado += String.format("%-20s %d\n", nombreEmpresa, numero);
        resultado += String.format("%-35s %s\n", "Nombre Pasajero", "RUT/Pasaporte");
        resultado += String.format("%-35s %s\n", nombrePasajero, idPasajero);
        resultado += String.format("%-20s %-15s %s\n", "Patente bus", "Asiento", "Valor Pagado");
        resultado += String.format("%-20s %-15d %d\n", patenteBus, asiento, valorPagado);
        resultado += String.format("%-20s %-20s %-10s %s\n", "Terminal origen", "Terminal destino", "Fecha", "Hora");
        resultado += String.format("%-20s %-20s %-10s %s\n", terminalOrigen, terminalDestino, fecha, hora);
        resultado += "------------------------------------------------------------\n";

        return resultado;
    }
}
