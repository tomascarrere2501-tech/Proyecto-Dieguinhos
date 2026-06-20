package controlador;

import persistencia.IOSVP;
import excepciones.SVPException;
import modelo.*;
import utilidades.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaVentaPasajes implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private static SistemaVentaPasajes instancia;

    private List<Pasajero> pasajeros;
    private List<Viaje> viajes;
    private List<Venta> ventas;
    private List<Cliente> clientes;

    private SistemaVentaPasajes() {
        this.clientes = new ArrayList<>();
        this.viajes = new ArrayList<>();
        this.pasajeros = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public static SistemaVentaPasajes getInstance() {
        if (instancia == null) {
            instancia = new SistemaVentaPasajes();
        }
        return instancia;
    }

    public void createCliente(IdPersona id, Nombre nom, String fono, String email) {
        if (findCliente(id).isPresent()) {
            throw new SVPException("Ya existe cliente con el id indicado");
        }
        Cliente nuevoCliente = new Cliente(id, nom, email);
        nuevoCliente.setTelefono(fono);
        this.clientes.add(nuevoCliente);
    }

    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {
        if (findPasajero(id).isPresent()) {
            throw new SVPException("Ya existe pasajero con el id indicado");
        }
        Pasajero nvoPasajero = new Pasajero(id, nom);
        nvoPasajero.setTelefono(fono);
        nvoPasajero.setNomContacto(nomContacto);
        nvoPasajero.setFonoContacto(fonoContacto);
        this.pasajeros.add(nvoPasajero);
    }

    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion, String patBus, IdPersona idAuxiliar, IdPersona[] idsConductores, String comunaSalida, String comunaLlegada) {
        Bus bus = findBus(patBus).orElseThrow(() -> new SVPException("No existe bus con la patente indicada"));
        ControladorEmpresas ctrlEmpresas = ControladorEmpresas.getInstance();

        Auxiliar auxiliar = ctrlEmpresas.findAuxiliar(idAuxiliar, bus.getEmpresa().getRut())
                .orElseThrow(() -> new SVPException("No existe auxiliar con el id indicado en la empresa con el rut indicado"));

        Conductor[] conductoresViaje = new Conductor[idsConductores.length];
        for (int i = 0; i < idsConductores.length; i++) {
            conductoresViaje[i] = ctrlEmpresas.findConductor(idsConductores[i], bus.getEmpresa().getRut())
                    .orElseThrow(() -> new SVPException("No existe conductor con el id indicado en la empresa con el rut indicado"));
        }

        Terminal termSalida = ctrlEmpresas.findTerminal(comunaSalida)
                .orElseThrow(() -> new SVPException("No existe terminal de salida en la comuna indicada"));

        Terminal termLlegada = ctrlEmpresas.findTerminal(comunaLlegada)
                .orElseThrow(() -> new SVPException("No existe terminal de llegada en la comuna indicada"));

        if (findViaje(fecha, hora, patBus).isPresent()) {
            throw new SVPException("Ya existe viaje con fecha, hora y patente de bus indicados");
        }

        Viaje nuevoViaje = new Viaje(fecha, hora, precio, duracion, bus, auxiliar, conductoresViaje, termSalida, termLlegada);
        this.viajes.add(nuevoViaje);
    }

    public void iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaViaje, String comunaSalida, String comunaLlegada, IdPersona idCliente, int nroPasajes) {
        if (findVenta(idDoc, tipo).isPresent()) {
            throw new SVPException("Ya existe venta con el id y tipo de documento indicados");
        }

        Cliente cliente = findCliente(idCliente)
                .orElseThrow(() -> new SVPException("No existe cliente con id indicado"));

        boolean hayDisponibilidad = viajes.stream()
                .anyMatch(v -> v.getFecha().equals(fechaViaje) &&
                        v.getTerminalSalida().getDireccion().getComuna().equalsIgnoreCase(comunaSalida) &&
                        v.getTerminalLlegada().getDireccion().getComuna().equalsIgnoreCase(comunaLlegada) &&
                        v.existeDisponibilidad(nroPasajes));

        if (!hayDisponibilidad) {
            throw new SVPException("No existen viajes disponibles en la fecha y con terminales en las comunas de salida y llegada indicados");
        }

        Venta nuevaVenta = new Venta(idDoc, tipo, LocalDate.now(), cliente);
        this.ventas.add(nuevaVenta);
    }

    public void vendePasaje(String idDoc, TipoDocumento tipo, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero) {
        Venta venta = findVenta(idDoc, tipo).orElseThrow(() -> new SVPException("No existe venta con el id y tipo de documento indicados"));
        Pasajero pasajero = findPasajero(idPasajero).orElseThrow(() -> new SVPException("No existe pasajero con el id indicado"));
        Viaje viaje = findViaje(fecha, hora, patBus).orElseThrow(() -> new SVPException("No existe viaje con la fecha, hora y patente de bus indicados"));

        venta.createPasaje(asiento, viaje, pasajero);
    }

    public void pagaVenta(String idDocumento, TipoDocumento tipoDoc) {
        Venta venta = findVenta(idDocumento, tipoDoc).orElseThrow(() -> new SVPException("No existe venta con el id y tipo de documento indicados"));
        if (!venta.pagaMonto()) {
            throw new SVPException("La venta ya fue pagada");
        }
    }

    public void pagaVenta(String idDocumento, TipoDocumento tipoDoc, long numeroTarjeta) {
        Venta venta = findVenta(idDocumento, tipoDoc).orElseThrow(() -> new SVPException("No existe venta con el id y tipo de documento indicados"));
        if (!venta.pagaMonto(numeroTarjeta)) {
            throw new SVPException("La venta ya fue pagada");
        }
    }

    public Optional<Integer> getMontoVenta(String idDocumento, TipoDocumento tipo) {
        return findVenta(idDocumento, tipo).map(Venta::getMonto);
    }

    public Optional<String> getNombrePasajero(IdPersona idPersona) {
        return findPasajero(idPersona).map(p -> p.getNombreCompleto().toString());
    }

    public String[] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus) {
        return findViaje(fecha, hora, patBus).map(Viaje::getAsientos).orElse(new String[0]);
    }

    public String[][] listPasajerosViaje(LocalDate fecha, LocalTime hora, String patente) {
        Viaje viaje = findViaje(fecha, hora, patente).orElseThrow(() -> new SVPException("No existe viaje con la fecha, hora y patente de bus indicados"));
        return viaje.getListaPasajeros();
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje, String origen, String destino, int cantidadPasajes) {
        return viajes.stream()
                .filter(v -> v.getFecha().equals(fechaViaje) &&
                        v.getTerminalSalida().getDireccion().getComuna().equalsIgnoreCase(origen) &&
                        v.getTerminalLlegada().getDireccion().getComuna().equalsIgnoreCase(destino) &&
                        v.existeDisponibilidad(cantidadPasajes))
                .map(v -> new String[]{
                        v.getBus().getPatente(),
                        v.getHora().toString(),
                        String.valueOf(v.getPrecio()),
                        String.valueOf(v.getNroAsientosDisponibles())
                })
                .toArray(String[][]::new);
    }

    public String[][] listViajes() {
        return viajes.stream()
                .map(v -> new String[]{
                        v.getFecha().toString(),
                        v.getHora().toString(),
                        v.getFechaHoraTermino().toLocalTime().toString(),
                        String.valueOf(v.getPrecio()),
                        String.valueOf(v.getNroAsientosDisponibles()),
                        v.getBus().getPatente(),
                        v.getTerminalSalida().getDireccion().getComuna(),
                        v.getTerminalLlegada().getDireccion().getComuna()
                })
                .toArray(String[][]::new);
    }

    public String[][] listVentas() {
        return ventas.stream()
                .map(v -> new String[]{
                        v.getIdDocumento(),
                        v.getTipo().toString(),
                        v.getFecha().toString(),
                        v.getCliente().getIdPersona().toString(),
                        v.getCliente().getNombreCompleto().toString(),
                        String.valueOf(v.getPasajes().length),
                        String.valueOf(v.getMonto())
                })
                .toArray(String[][]::new);
    }

    private Optional<Cliente> findCliente(IdPersona id){
        return clientes.stream().filter(c -> c.getIdPersona().equals(id)).findFirst();
    }

    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        return ventas.stream().filter(v -> v.getIdDocumento().equals(idDocumento) && v.getTipo() == tipoDocumento).findFirst();
    }

    private Optional<Bus> findBus(String patente) {
        return ControladorEmpresas.getInstance().findBus(patente);
    }

    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        return viajes.stream()
                .filter(v -> v.getFecha().equals(fecha) && v.getHora().equals(hora) && v.getBus().getPatente().equalsIgnoreCase(patenteBus))
                .findFirst();
    }

    private Optional<Pasajero> findPasajero(IdPersona idPersona) {
        return pasajeros.stream().filter(p -> p.getIdPersona().equals(idPersona)).findFirst();
    }

    public void generatePasajesVenta(String idDocumento, TipoDocumento tipo) {
        try {
            Venta venta = findVenta(idDocumento, tipo).orElseThrow(() ->
                    new SVPException("No existe venta con el id y tipo de documento indicados")
            );

            Pasaje[] pasajes = venta.getPasajes();
            String nombreArchivo = idDocumento + tipo.name().toLowerCase() + ".txt";

            IOSVP.getInstance().savePasajesDeVenta(pasajes, nombreArchivo);
        } catch (SVPException e) {
            throw e;
        }
    }

    public void readDatosIniciales() {
        try {
            Object[] objetos = IOSVP.getInstance().readDatosIniciales();

            this.clientes.clear();
            this.pasajeros.clear();
            this.ventas.clear();
            this.viajes.clear();

            java.util.Arrays.stream(objetos)
                    .filter(obj -> obj instanceof Cliente)
                    .map(obj -> (Cliente) obj)
                    .forEach(this.clientes::add);

            java.util.Arrays.stream(objetos)
                    .filter(obj -> obj instanceof Pasajero)
                    .map(obj -> (Pasajero) obj)
                    .forEach(this.pasajeros::add);

            java.util.Arrays.stream(objetos)
                    .filter(obj -> obj instanceof Venta)
                    .map(obj -> (Venta) obj)
                    .forEach(this.ventas::add);

            java.util.Arrays.stream(objetos)
                    .filter(obj -> obj instanceof Viaje)
                    .map(obj -> (Viaje) obj)
                    .forEach(this.viajes::add);

            ControladorEmpresas.getInstance().setDatosIniciales(objetos);
        } catch (SVPException e) {
            throw e;
        }
    }

    public void saveDatosSistema() {
        try {
            Object[] controladores = new Object[]{ this, ControladorEmpresas.getInstance() };
            IOSVP.getInstance().saveControladores(controladores);
        } catch (SVPException e) {
            throw e;
        }
    }

    public void readDatosSistema() {
        try {
            Object[] controladores = IOSVP.getInstance().readControladores();
            SistemaVentaPasajes.instancia = (SistemaVentaPasajes) controladores[0];
            ControladorEmpresas.getInstance().setInstanciaPersistente((ControladorEmpresas) controladores[1]);
        } catch (SVPException e) {
            throw e;
        }
    }
}
