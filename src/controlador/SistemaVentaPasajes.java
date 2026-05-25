package controlador;

import modelo.*;
import utilidades.*;
import excepciones.SistemaVentaPasajesException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaVentaPasajes {

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
            throw new SistemaVentaPasajesException("Ya existe cliente con el id indicado");
        }
        Cliente nuevoCliente = new Cliente(id, nom, email);
        nuevoCliente.setTelefono(fono);
        this.clientes.add(nuevoCliente);
    }

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

    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion, String patBus, IdPersona idAuxiliar, IdPersona[] idConductores, String comunaSalida, String comunaLlegada) {
        if (findViaje(fecha, hora, patBus).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe viaje con fecha, hora y patente de bus indicados");
        }

        Optional<Bus> busOpt = ControladorEmpresas.getInstance().findBus(patBus);
        if (busOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe bus con la patente indicada");
        }

        Bus bus = busOpt.get();
        Empresa empresa = bus.getEmpresa();

        boolean existeAuxiliar = false;
        for (Tripulante t : empresa.getTripulantes()) {
            if (t instanceof Auxiliar && t.getIdPersona().equals(idAuxiliar)) {
                existeAuxiliar = true;
                break;
            }
        }
        if (!existeAuxiliar) {
            throw new SistemaVentaPasajesException("No existe auxiliar con el id indicado en la empresa con el rut indicado");
        }

        for (IdPersona idConductor : idConductores) {
            boolean existeConductor = false;
            for (Tripulante t : empresa.getTripulantes()) {
                if (t instanceof Conductor && t.getIdPersona().equals(idConductor)) {
                    existeConductor = true;
                    break;
                }
            }
            if (!existeConductor) {
                throw new SistemaVentaPasajesException("No existe conductor con el id indicado en la empresa con el rut indicado");
            }
        }

        Optional<Terminal> termSalidaOpt = ControladorEmpresas.getInstance().findTerminalPorComuna(comunaSalida);
        if (termSalidaOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe terminal de salida en la comuna indicada");
        }

        Optional<Terminal> termLlegadaOpt = ControladorEmpresas.getInstance().findTerminalPorComuna(comunaLlegada);
        if (termLlegadaOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe terminal de llegada en la comuna indicada");
        }

        IdPersona[] tripulantes = new IdPersona[1 + idConductores.length];
        tripulantes[0] = idAuxiliar;
        for (int i = 0; i < idConductores.length; i++) {
            tripulantes[i + 1] = idConductores[i];
        }

        String[] comunas = {comunaSalida, comunaLlegada};

        Viaje nuevoViaje = new Viaje(fecha, hora, precio, duracion, bus, tripulantes, comunas);
        this.viajes.add(nuevoViaje);
    }

    public void iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente) {
        if (findVenta(idDoc, tipo).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe venta con el id y tipo de documento indicados");
        }

        Optional<Cliente> clienteOpt = findCliente(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe cliente con id indicado");
        }

        Venta nuevaVenta = new Venta(idDoc, tipo, fechaVenta, clienteOpt.get());
        this.ventas.add(nuevaVenta);
    }

    public void vendePasaje(String idDoc, TipoDocumento tipo, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero) {
        Optional<Venta> ventaOpt = findVenta(idDoc, tipo);
        if (ventaOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe venta con el id y tipo de documento indicados");
        }

        Optional<Pasajero> pasajeroOpt = findPasajero(idPasajero);
        if (pasajeroOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe pasajero con el id indicado");
        }

        Optional<Viaje> viajeOpt = findViaje(fecha, hora, patBus);
        if (viajeOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente de bus indicados");
        }

        ventaOpt.get().createPasaje(asiento, viajeOpt.get(), pasajeroOpt.get());
    }

    public void pagaVenta(String idDocumento, TipoDocumento tipo) {
        Optional<Venta> ventaOpt = findVenta(idDocumento, tipo);
        if (ventaOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe venta con el id y tipo de documento indicados");
        }
        if (!ventaOpt.get().pagaMonto()) {
            throw new SistemaVentaPasajesException("La venta ya fue pagada");
        }
    }

    public void pagaVenta(String idDocumento, TipoDocumento tipo, long nroTarjeta) {
        Optional<Venta> ventaOpt = findVenta(idDocumento, tipo);
        if (ventaOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe venta con el id y tipo de documento indicados");
        }
        if (!ventaOpt.get().pagaMonto(nroTarjeta)) {
            throw new SistemaVentaPasajesException("La venta ya fue pagada");
        }
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje, String comunaSalida, String comunaLlegada, int nroPasajes) {
        List<Viaje> filtrados = new ArrayList<>();
        for (Viaje v : viajes) {
            if (v.getFecha().equals(fechaViaje) &&
                    v.getTerminalSalida().getComuna().equals(comunaSalida) &&
                    v.getTerminalLlegada().getComuna().equals(comunaLlegada) &&
                    v.existeDisponibilidad(nroPasajes)) {
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

    public String[] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus) {
        Optional<Viaje> viajeOpt = findViaje(fecha, hora, patBus);
        if (viajeOpt.isEmpty()) {
            return new String[0];
        }
        return viajeOpt.get().getAsientos();
    }

    public String[][] listViajes() {
        if (viajes.isEmpty()) {
            return new String[0][0];
        }
        String[][] matrizViajes = new String[viajes.size()][8];
        for (int i = 0; i < viajes.size(); i++) {
            Viaje v = viajes.get(i);
            matrizViajes[i][0] = v.getFecha().toString();
            matrizViajes[i][1] = v.getHora().toString();
            matrizViajes[i][2] = v.getFechaHoraTermino().toLocalTime().toString();
            matrizViajes[i][3] = String.valueOf(v.getPrecio());
            matrizViajes[i][4] = String.valueOf(v.getNroAsientosDisponibles());
            matrizViajes[i][5] = v.getBus().getPatente();
            matrizViajes[i][6] = v.getTerminalSalida().getComuna();
            matrizViajes[i][7] = v.getTerminalLlegada().getComuna();
        }
        return matrizViajes;
    }

    public String[][] listPasajerosViaje(LocalDate fecha, LocalTime hora, String patBus) {
        Optional<Viaje> viajeOpt = findViaje(fecha, hora, patBus);
        if (viajeOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente de bus indicados");
        }
        return viajeOpt.get().getListaPasajeros();
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

    public Optional<String> getNombrePasajero(IdPersona idPersona) {
        return findPasajero(idPersona).map(p -> p.getNombreCompleto().toString());
    }

    public Optional<Integer> getMontoVenta(String idDocumento, TipoDocumento tipo) {
        return findVenta(idDocumento, tipo).map(Venta::getMonto);
    }
    // metodos de buqueda
    private Optional<Cliente> findCliente(IdPersona id) {
        for (Cliente c : clientes) {
            if (c.getIdPersona().equals(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        for (Venta v : ventas) {
            if (v.getIdDocumento().equals(idDocumento) && v.getTipo() == tipoDocumento) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        for (Viaje v : viajes) {
            if (v.getFecha().equals(fecha) &&
                    v.getHora().equals(hora) &&
                    v.getBus().getPatente().equals(patenteBus)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    private Optional<Pasajero> findPasajero(IdPersona idPersona) {
        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(idPersona)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}