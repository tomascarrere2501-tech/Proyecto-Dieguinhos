import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class SistemaVentasPasajes {


    public boolean crateCliente(IdPersona id, Nombre nom, String fono, String email){
        return true;
    }
    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto){
        return true;
    }
    public boolean createBus(String patente, String marca, String modelo, int nroAsiento){
        return true;
    }
    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus){
        return true;
    }
    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente){
        return true;
    }
    public String [][] getHorariosDeViaje(LocalDate fecha){
        return null;
    }
    public String [][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus){
        return null;
    }
    public int getMontoVenta(String idDocumento, TipoDocumento tipo){
        return 0;
    }
    public String getNombrePasajero(IdPersona idPersona){
        return null;
    }
    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patBus, int asiento, idPasajero){
        return true;
    }
    public String [][] listVentas(){
        return null;
    }
    public String [][] listViajes(){
        return null;
    }
    public String [][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus){
        return null;
    }
    private Cliente findCliente(IdPersona id){
        return null;
    }
    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento){
        return null;
    }
    private Bus findBus(String patente){
        return null;
    }
    public Viaje findViaje(String fecha, String hora, String patenteBus){
        return null;
    }
    public Pasajero findPasajero(IdPersona idPersona){
        return null;
    }


}
