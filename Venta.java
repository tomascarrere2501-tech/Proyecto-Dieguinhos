import java.time.LocalDate;
import java.util.Arraylist;
public class Venta {
    private  String iddocumento;
    private tipo Tipodocummento;
    private LocalDate fecha;

    private Cliente cliente;
    private ArrayList<Pasaje> pasajesInternos;

    public Venta ( tipo Tipodocumento, String iddocumento, LocalDate fecha , Cliente cliente ){
        this. iddocumento= iddocumento;
        this.fecha=fecha;
        this.Tipodocummento= Tipodocumento;
        this.cliente = cliente;
        this.pasajesInternos = new ArrayList<>();

       // Asocia el cliente a la venta,ocupandose  objeto Cliente 
        // agregue esta venta en su colección."

        cli.addVenta(this);

    }

    public String getIddocumento() {
        return iddocumento;
    }

    public tipo getTipodocummento() {
        return Tipodocummento;
    }


    public LocalDate getFecha() {
        return fecha;
    }


    public getCliente(){
        return Cliente ;
    }

    public void createPasaje( int asiento, Viaje viaje,Pasajero pasajero ){

        // esto es para "Crea un objeto Pasaje, a partir de los datos recibidos  agregándolo a su colección"
        // se  asume que el constructor de Pasaje recibe (int, Viaje, Pasajero, Venta) según el UML
        
        Pasaje nuevoPasaje = new Pasaje(asiento, viaje, pasajero, this);
        this.pasajesInternos.add(nuevoPasaje);


    }

     public Pasaje []  getPasajes(){
        return pasajesInternos.toArray(new Pasaje[0]);
     }

     public int  getMonto(){
        nt total = 0;
    for (Pasaje p : pasajesInternos) {
        // Obtenemos el viaje asociado al pasaje y sumamos su precio
        total += p.getViaje().getPrecio();
        
     }
     return total;

}
