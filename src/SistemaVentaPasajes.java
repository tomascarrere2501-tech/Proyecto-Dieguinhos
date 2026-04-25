import java.time.LocalDate;
import java.time.LocalTime;

public class SistemaVentaPasajes {
    private List<Pasajero> pasajeros = new ArrayList<>();
    private List<Bus> buses = new ArrayList<>();
    private List<Viaje> viajes = new ArrayList<>();
    private List<Venta> ventas = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto){
        if(findPasajero(id) != null){
            return false;
        }
        Pasajero nvoPasajero = new Pasajero(id, nom);
        nvoPasajero.setTelefono(fono);
        nvoPasajero.setNombreContacto(nomContacto);
        nvoPasajero.setFonoContacto(fonoContacto);

        //retorna nuevo pasajero y lo guarda en la lista pasajero
        return this.pasajeros.add(nvoPasajero);
    }//createCliente
    public Pasajero findPasajero(IdPersona idPersona){
        for(int i =0; i<pasajeros.size; i++){
            Pasajero p = this.pasajeros.get(i);
            if (p.getIdPersona().equals(idPersona)){
                return p;
            }
        }
        return null;
    }//findPasajero
    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus){
        Bus busBuscado = null;
        for (int i = 0; i < this.buses.size(); i++) {
            Bus b = this.buses.get(i);
            if (b.getPatente().equals(patBus)) {
                busEncontrado = b;
                break;
            }
        }
        if (busEncontrado == null) {
            return false;
        }
        for (int j = 0; j < this.viajes.size(); j++) {
            Viaje v = this.viajes.get(j);
            if (v.getFecha().equals(fecha) &&
                    v.getHora().equals(hora) &&
                    v.getBus().getPatente().equals(patBus)) {
                return false;
            }
        }

        Viaje nuevoViaje = new Viaje(fecha, hora, precio, busEncontrado);
        return this.viajes.add(nuevoViaje);
    }
    public Viaje findViaje(String fecha, String hora, String patenteBus) {
        for (int i = 0; i < this.viajes.size(); i++) {
            Viaje v = this.viajes.get(i);

            if (v.getFecha().toString().equals(fecha) &&
                    v.getHora().toString().equals(hora) &&
                    v.getBus().getPatente().equals(patenteBus)) {

                return v;
            }
        }
        return null;
    }
    public String[] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus) {
        Viaje viajeEncontrado = null;

        for (int i = 0; i < this.viajes.size(); i++) {
            Viaje v = this.viajes.get(i);
            if (v.getFecha().equals(fecha) &&
                    v.getHora().equals(hora) &&
                    v.getBus().getPatente().equals(patBus)) {
                viajeEncontrado = v;
                break;
            }
        }
        if (viajeEncontrado == null) {
            return new String[0];
        }

        return viajeEncontrado.getAsientos();
    }
    public int getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Venta ventaEncontrada = null;

        for (int i = 0; i < this.ventas.size(); i++) {
            Venta v = this.ventas.get(i);

            if (v.getIdDocumento().equals(idDocumento) && v.getTipo() == tipo) {
                ventaEncontrada = v;
                break;
            }
        }
        if (ventaEncontrada != null) {
            return ventaEncontrada.getMonto();
        }

        return 0;
    }
    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero) {

        Venta ventaEncontrada = null;
        for (int i = 0; i < this.ventas.size(); i++) {
            if (this.ventas.get(i).getIdDocumento().equals(idDoc)) {
                ventaEncontrada = this.ventas.get(i);
                break;
            }
        }

        Viaje viajeEncontrado = null;
        for (int j = 0; j < this.viajes.size(); j++) {
            Viaje v = this.viajes.get(j);
            if (v.getFecha().equals(fecha) && v.getHora().equals(hora) && v.getBus().getPatente().equals(patBus)) {
                viajeEncontrado = v;
                break;
            }
        }

        Pasajero pasajeroEncontrado = null;
        for (int k = 0; k < this.pasajeros.size(); k++) {
            if (this.pasajeros.get(k).getIdPersona().equals(idPasajero)) {
                pasajeroEncontrado = this.pasajeros.get(k);
                break;
            }
        }

        if (ventaEncontrada == null || viajeEncontrado == null || pasajeroEncontrado == null) {
            return false;
        }

        ventaEncontrada.createPasaje(asiento, viajeEncontrado, pasajeroEncontrado);

        return true;
    }
    public String[][] listViajes() {
        int filas = this.viajes.size();
        String[][] matrizViajes = new String[filas][5];

        for (int i = 0; i < filas; i++) {
            Viaje v = this.viajes.get(i);

            matrizViajes[i][0] = v.getFecha().toString();

            matrizViajes[i][1] = v.getHora().toString();

            matrizViajes[i][2] = String.valueOf(v.getPrecio());

            matrizViajes[i][3] = String.valueOf(v.getNroAsientosDisponibles());

            matrizViajes[i][4] = v.getBus().getPatente();
        }

        return matrizViajes;
    }
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
        return true;

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
        if (pasajero != null) {
            Nombre nombre = pasajero.getNombreCompleto();
            return nombre.toString();
        } else {
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
        for (Cliente c : clientes ){
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