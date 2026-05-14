import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SistemaVentaPasajes {
    private static SistemaVentaPasajes instancia;

    private List<Pasajero> pasajeros;
    private List<Bus> buses;
    private List<Viaje> viajes;
    private List<Venta> ventas;
    private List<Cliente> clientes;

    private SistemaVentaPasajes(){
        this.clientes = new ArrayList<>();
        this.viajes = new ArrayList<>();
        this.buses = new ArrayList<>();
        this.pasajeros = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }
    public static SistemaVentaPasajes getInstance() {
        if (instancia== null) {
            instancia = new SistemaVentaPasajes();
        }
        return instancia;
    }
    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {
        if (findPasajero(id) != null) {
            return false;
        }
        Pasajero nvoPasajero = new Pasajero(id, nom);
        nvoPasajero.setTelefono(fono);
        nvoPasajero.setNomContacto(nomContacto);
        nvoPasajero.setFonoContacto(fonoContacto);

        return this.pasajeros.add(nvoPasajero);
    }

    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus) {
        Bus busEncontrado = findBus(patBus);

        if (busEncontrado == null) {
            return false;
        }

        for (Viaje v : viajes) {
            if (v.getFecha().equals(fecha) &&
                    v.getHora().equals(hora) &&
                    v.getBus().getPatente().equals(patBus)) {
                return false;
            }
        }

        Viaje nuevoViaje = new Viaje(fecha, hora, precio, busEncontrado);
        return this.viajes.add(nuevoViaje);
    }

    public int getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Venta ventaEncontrada = findVenta(idDocumento, tipo);
        if (ventaEncontrada != null) {
            return ventaEncontrada.getMonto();
        }
        return 0;
    }

    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero) {

        Venta ventaEncontrada = null;
        for (Venta v : ventas) {
            if (v.getIdDocumento().equals(idDoc)) {
                ventaEncontrada = v;
                break;
            }
        }

        Viaje viajeEncontrado = findViaje(fecha.toString(), hora.toString(), patBus);
        Pasajero pasajeroEncontrado = findPasajero(idPasajero);

        if (ventaEncontrada == null || viajeEncontrado == null || pasajeroEncontrado == null) {
            return false;
        }

        ventaEncontrada.createPasaje(asiento, viajeEncontrado, pasajeroEncontrado);
        return true;
    }

    public String[][] listViajes() {
        String[][] matrizViajes = new String[this.viajes.size()][5];
        for (int i = 0; i < viajes.size(); i++) {
            Viaje v = viajes.get(i);
            matrizViajes[i][0] = v.getFecha().toString();
            matrizViajes[i][1] = v.getHora().toString();
            matrizViajes[i][2] = String.valueOf(v.getPrecio());
            matrizViajes[i][3] = String.valueOf(v.getNroAsientosDisponibles());
            matrizViajes[i][4] = v.getBus().getPatente();
        }
        return matrizViajes;
    }

    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email) {
        if (findCliente(id) != null) {
            return false;
        }
        Cliente nuevoCliente = new Cliente(id, nom, email);
        nuevoCliente.setTelefono(fono);
        return clientes.add(nuevoCliente);
    }

    public boolean createBus(String patente, String marca, String modelo, int nroAsiento) {
        if (findBus(patente) != null) {
            return false;
        }
        Bus nuevoBus = new Bus(patente, nroAsiento);
        nuevoBus.setMarca(marca);
        nuevoBus.setModelo(modelo);
        return buses.add(nuevoBus);
    }

    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente) {
        if (findVenta(idDoc, tipo) != null) {
            return false;
        }
        Cliente cliente = findCliente(idCliente);
        if (cliente == null) {
            return false;
        }

        Venta nuevaVenta = new Venta(idDoc, tipo, fechaVenta, cliente);
        return ventas.add(nuevaVenta);
    }

    public String[] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus) {
        Viaje viaje = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viaje == null) {
            return new String[0];
        }

        String[][] asientosInfo = viaje.getAsientos();
        String[] resultado = new String[asientosInfo.length];
        for (int i = 0; i < asientosInfo.length; i++) {
            resultado[i] = asientosInfo[i][1];
        }
        return resultado;
    }

    public String getNombrePasajero(IdPersona idPersona) {
        Pasajero pasajero = findPasajero(idPersona);
        if (pasajero != null) {
            return pasajero.getNombreCompleto().toString();
        }
        return null;
    }

    public String[][] listVentas() {
        String[][] matrizVentas = new String[ventas.size()][7];
        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);
            matrizVentas[i][0] = v.getIdDocumento();
            matrizVentas[i][1] = v.getTipo().toString();
            matrizVentas[i][2] = v.getFecha().toString();
            matrizVentas[i][3] = v.getCliente().getIdPersona().toString();
            matrizVentas[i][4] = v.getCliente().getNombreCompleto().toString();
            matrizVentas[i][5] = String.valueOf(v.getPasajes().length);
            matrizVentas[i][6] = String.valueOf(v.getMonto());
        }
        return matrizVentas;
    }

    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus) {
        Viaje viaje = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viaje == null) {
            return new String[0][0];
        }
        return viaje.getListaPasajeros();
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje) {
        List<Viaje> filtrados = new ArrayList<>();
        for (Viaje v : viajes) {
            if (v.getFecha().equals(fechaViaje)) {
                filtrados.add(v);
            }
        }

        if (filtrados.isEmpty()) {
            return new String[0][0];
        }

        String[][] horarios = new String[filtrados.size()][4];
        for (int i = 0; i < filtrados.size(); i++) {
            Viaje v = filtrados.get(i);
            horarios[i][0] = v.getBus().getPatente();
            horarios[i][1] = v.getHora().toString();
            horarios[i][2] = String.valueOf(v.getPrecio());
            horarios[i][3] = String.valueOf(v.getNroAsientosDisponibles());
        }
        return horarios;
    }

    private Cliente findCliente(IdPersona id) {
        for (Cliente c : clientes) {
            if (c.getIdPersona().equals(id)) {
                return c;
            }
        }
        return null;
    }

    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        for (Venta v : ventas) {
            if (v.getIdDocumento().equals(idDocumento) && v.getTipo() == tipoDocumento) {
                return v;
            }
        }
        return null;
    }

    private Bus findBus(String patente) {
        for (Bus b : buses) {
            if (b.getPatente().equals(patente)) {
                return b;
            }
        }
        return null;
    }

    private Viaje findViaje(String fecha, String hora, String patenteBus) {
        for (Viaje v : viajes) {
            if (v.getFecha().toString().equals(fecha) &&
                    v.getHora().toString().equals(hora) &&
                    v.getBus().getPatente().equals(patenteBus)) {
                return v;
            }
        }
        return null;
    }

    private Pasajero findPasajero(IdPersona idPersona) {
        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(idPersona)) {
                return p;
            }
        }
        return null;
    }
}