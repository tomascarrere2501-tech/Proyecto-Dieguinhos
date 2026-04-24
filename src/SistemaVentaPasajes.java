import java.time.LocalDate;
import java.time.LocalTime;

public class SistemaVentaPasajes {
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
                return false; // Ya existe un viaje idéntico
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

            matriz[i][0] = v.getFecha().toString();

            matriz[i][1] = v.getHora().toString();

            matriz[i][2] = String.valueOf(v.getPrecio());

            matriz[i][3] = String.valueOf(v.getNroAsientosDisponibles());

            matriz[i][4] = v.getBus().getPatente();
        }

        return matrizViajes;
    }
}