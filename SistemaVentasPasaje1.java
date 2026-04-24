import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class SistemaVentasPasajes1 {



    public boolean crateCliente(IdPersona id, Nombre nom, String fono, String email){

        if(findCliente(id)!= null){
            return false;
        }
        Cliente nuevoCliente= new Cliente(id,nom,email);
        nuevoCliente.setTelefono(fono);
        clientes.add(nuevoCliente);
        return  true;

    }

    public boolean createBus(String patente, String marca, String modelo, int nroAsiento){
        if( findBus(patente)!=null){
            return false;
        }
        Bus nuevoBus = new Bus( patente, nroAsiento);
        nuevoBus.setMarca(marca);
        nuevoBus.setModelo(modelo);
        buses.add(nuevoBus);
        return  true;
    }

    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente){
        if(findVenta(idDoc,tipo)!= null){
            return false;
        }
        Cliente cliente= findCliente(idCliente);
        if(cliente==null){
            return false;
        }

        Venta nuevaVenta= new Venta(idDoc, tipo,fechaVenta,cliente);
        ventas.add(nuevaVenta);
        return false;

    }

    public String [][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus){
            Viaje viaje= findViaje(fecha.ToString(), hora.ToString(), patBus );

            if(viaje==null){
                return  new String[0][0];
            }
            return viaje.getAsientos;

    }

    public String getNombrePasajero(IdPersona idPersona){
        Pasajero pasajero= findPasajero(idPersona);
        if(pasajero==null){
            return null;
        }
    }

    public String [][] listVentas(){
        String [][] matrizVentas= new String[ventas.size()][7];

        for (int i=0; i < matrizVentas.size(); i++){
            Venta v = ventas.get(i);
            matrizVentas[i][0]= v.getIdDocumento();
            matrizVentas[i][1]= v.gettipo().toString();
            matrizVentas[i][2]= v.getFecha().toString();
            matrizVentas[i][3]= v.getCliente().getIdPersona().toString();
            matrizVentas[i][4]= v.getCliente().getNombreCompleto().toString();
            matrizVentas[i][5]= String.valueOf(v.getPasajes().length);
            matrizVentas[i][6]= String.valueOf(v.getMonto());
        }
        return  matrizVentas;
    }

    public String [][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus){
        Viaje viaje= findViaje(fecha.toString(), hora.toString(), patBus);
        if(viaje== null){
            return  new String[0][0];
        }
        return viaje.getListaPasajeros();

    }
    private Cliente findCliente(IdPersona id){
        for (Cliente c : cliente ){
            if(c.getIdPersona().equals(id)){
                return c;
            }
        }
        return  null;
    }
    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento){
        for(Venta v : ventas ){
            if(v.getIdDocumento().equals(idDocumento)&& v.getTipo()==tipoDocumento){
                return  v;
            }

        }
        return  null;
    }
    private Bus findBus(String patente){
        for(Bus b: buses){
            if(b.getPatente().equals(patente)){
                return  b;
            }
        }
        return  null;
    }


}
