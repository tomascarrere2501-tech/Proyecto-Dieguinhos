package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {
    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    private ArrayList<Pasaje> pasajes;
    private Pago pago; 

    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha, Cliente cliente) {
        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.pasajes = new ArrayList<>();
        this.pago = null; 

        cliente.addVenta(this);
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {
        Pasaje nuevoPasaje = new Pasaje(asiento, viaje, pasajero, this);
        this.pasajes.add(nuevoPasaje);
    }

    public Pasaje[] getPasajes() {
        return pasajes.toArray(new Pasaje[0]);
    }

    public int getMonto() {
        int total = 0;
        for (Pasaje p : pasajes) {
            total += p.getViaje().getPrecio();
        }
        return total;
    }

    public boolean pagaMonto() {
        if (this.pago != null) {
            return false; 
        }
        this.pago = new PagoEfectivo(getMonto());
        return true;
    }

    public boolean pagaMonto(long nroTarjeta) {
        if (this.pago != null) {
            return false; 
        }
        this.pago = new PagoTarjeta(getMonto(), nroTarjeta);
        return true;
    }

    public int getMontoPagado() {
        if (this.pago != null) {
            return this.pago.getMonto();
        }
        return 0;
    }

    public String getTipoPago() {
        if (this.pago == null) {
            return null;
        }
        if (this.pago instanceof PagoEfectivo) {
            return "efectivo";
        } else if (this.pago instanceof PagoTarjeta) {
            return "tarjeta";
        }
        return null;
    }
}
