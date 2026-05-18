import excepciones.SistemaVentaPasajesException;
import java.util.Optional;
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
        if (instancia == null) {
            instancia = new SistemaVentaPasajes();
        }
        return instancia;
    }

    // listo
    public void createCliente(IdPersona id, Nombre nom, String fono, String email) {
        if (findCliente(id).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe cliente con el id indicado");
        }
        Cliente nuevoCliente = new Cliente(id, nom, email);
        nuevoCliente.setTelefono(fono);
        this.clientes.add(nuevoCliente);
    }

    // listo
    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {
        if (findPasajero(id).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe pasajero con el id indicado");
        }
        Pasajero nvoPasajero = new Pasajero(id, nom);
        nvoPasajero.setTelefono(fono);
        nvoPasajero.setNomContacto(nomContacto);
        nvoPasajero.setFonoContacto(fonoContacto);
        this.pasajeros.add(nvoPasajero);
    }

    // listo
    public void createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus) {
        Optional<Bus> busOpt = findBus(patBus);

        if (busOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe bus con la patente indicada");
        }

        for (Viaje v : viajes) {
            if (v.getFecha().equals(fecha) &&
                    v.getHora().equals(hora) &&
                    v.getBus().getPatente().equals(patBus)) {
                throw new SistemaVentaPasajesException("Ya existe viaje con fecha, hora y patente de bus indicados");
            }
        }

        Viaje nuevoViaje = new Viaje(fecha, hora, precio, busOpt.get());
        this.viajes.add(nuevoViaje);
    }

    public int getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Optional<Venta> ventaOpt = findVenta(idDocumento, tipo);
        if (ventaOpt.isPresent()) {
            return ventaOpt.get().getMonto();
        }
        return 0;
    }

    // listo
    public void vendePasaje(String idDoc, TipoDocumento tipo, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero) {
        Optional<Venta> ventaOpt = findVenta(idDoc, tipo);
        if (ventaOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe venta con el id y tipo de documento indicados");
        }

        Optional<Pasajero> pasajeroOpt = findPasajero(idPasajero);
        if (pasajeroOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe pasajero con el id indicado");
        }

        Optional<Viaje> viajeOpt = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viajeOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente de bus indicados");
        }

        ventaOpt.get().createPasaje(asiento, viajeOpt.get(), pasajeroOpt.get());
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

    // listo
    public void createBus(String patente, String marca, String modelo, int nroAsiento) {
        if (findBus(patente).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe bus con la patente indicada");
        }
        Bus nuevoBus = new Bus(patente, nroAsiento);
        nuevoBus.setMarca(marca);
        nuevoBus.setModelo(modelo);
        this.buses.add(nuevoBus);
    }

    // listo
    public void iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente) {
        if (findVenta(idDoc, tipo).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe venta con el id y tipo de documento indicados");
        }

        Optional<Cliente> clienteOpt = findCliente(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe cliente con id indicado");
        }

        Cliente cliente = clienteOpt.get();

        Venta nuevaVenta = new Venta(idDoc, tipo, fechaVenta, cliente);
        this.ventas.add(nuevaVenta);
    }

    public String[] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus) {
        Optional<Viaje> viajeOpt = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viajeOpt.isEmpty()) {
            return new String[0];
        }

        String[][] asientosInfo = viajeOpt.get().getAsientos();
        String[] resultado = new String[asientosInfo.length];
        for (int i = 0; i < asientosInfo.length; i++) {
            resultado[i] = asientosInfo[i][1];
        }
        return resultado;
    }

    public String getNombrePasajero(IdPersona idPersona) {
        Optional<Pasajero> pasajeroOpt = findPasajero(idPersona);
        if (pasajeroOpt.isPresent()) {
            return pasajeroOpt.get().getNombreCompleto().toString();
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

    // listo
    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus) {
        Optional<Viaje> viajeOpt = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viajeOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente de bus indicados");
        }
        return viajeOpt.get().getListaPasajeros();
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

    // listo
    private Optional<Cliente> findCliente(IdPersona id) {
        for (Cliente c : clientes) {
            if (c.getIdPersona().equals(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    // listo
    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        for (Venta v : ventas) {
            if (v.getIdDocumento().equals(idDocumento) && v.getTipo() == tipoDocumento) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    // listo
    private Optional<Bus> findBus(String patente) {
        for (Bus b : buses) {
            if (b.getPatente().equals(patente)) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    // listo
    private Optional<Viaje> findViaje(String fecha, String hora, String patenteBus) {
        for (Viaje v : viajes) {
            if (v.getFecha().toString().equals(fecha) &&
                    v.getHora().toString().equals(hora) &&
                    v.getBus().getPatente().equals(patenteBus)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    // listo
    private Optional<Pasajero> findPasajero(IdPersona idPersona) {
        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(idPersona)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}