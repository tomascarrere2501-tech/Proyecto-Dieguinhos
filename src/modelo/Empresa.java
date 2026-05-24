import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

public class Empresa {
    private Rut rut ;
    private String nombre;
    private String url;
    private List<Bus> buses;
    private List<Tripulante> tripulantes;

    public Empresa(Rut rut , String nombre, String url ){
        this.rut = rut;
        this.nombre = nombre;
        this.url = url;

    }

    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

 public void addBus( Bus bus){
    this.Buses.add(bus);
 }

    public Optional<Bus> getBus(String patente) {
        for (Bus b : buses) {
            if (b.getPatente().equalsIgnoreCase(patente)) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }


 public  Bus[] getBuses(){
        return this.buses.toArray(new Bus[0]);
 }



 public boolean addConductor(IdPersona id , Nombre nom , Direccion direccion){
        for( Tripulante t : tripulantes){
            if (t.getIdPersona().equals(id)){
                return false;
            }
        }

     public boolean addAuxiliar(IdPersona id, Nombre nom, Direccion direccion) {
         for (Tripulante t : tripulantes) {
             if (t.getIdPersona().equals(id)) return false; // Ya existe
         }
         this.tripulantes.add(new Auxiliar(id, nom, direccion));
         return true;
 }
     public Tripulante[] getTripulantes() {
         return this.tripulantes.toArray(new Tripulante[0]);
     }

     public Venta[] getVentas() {
         Set<Venta> ventasUnicas = new HashSet<>();
         for (Bus b : buses) {
             for (Viaje v : b.getViajes()) {
                 for (Venta venta : v.getVentas()) {
                     ventasUnicas.add(venta);
                 }
             }
         }
         return ventasUnicas.toArray(new Venta[0]);
     }    }


}
