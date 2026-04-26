import java.time.LocalDate;
import java.util.ArrayList; 

public class Venta {
    private String idDocumento; 
    private TipoDocumento tipo; 
    private LocalDate fecha;

    private Cliente cliente;
    private ArrayList<Pasaje> pasajesInternos;

    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha, Cliente cliente) {
        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.pasajesInternos = new ArrayList<>();

        // nueva modificacion de  Asocia el cliente a la venta
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
        this.pasajesInternos.add(nuevoPasaje);
        // neuva modificacion de  viaje debe conocer pasajes para saber si hay  disponiblidad
        viaje.addPasaje(nuevoPasaje);//faltaba esto
    }

    public Pasaje[] getPasajes() {
        return pasajesInternos.toArray(new Pasaje[0]);
    }

    public int getMonto() {
        int total = 0; 
        for (Pasaje p : pasajesInternos) {
            total += p.getViaje().getPrecio();
        }
        return total;
    }
}
