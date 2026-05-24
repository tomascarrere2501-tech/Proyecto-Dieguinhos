package vista;

import controlador.SistemaVentaPasajes;
import excepciones.SistemaVentaPasajesException;
import modelo.TipoDocumento;

import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Pasaporte;
import utilidades.Rut;
import utilidades.Tratamiento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class UISVP {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    Scanner sc;
    private SistemaVentaPasajes sistema;

    private static UISVP instancia;

    public UISVP() {
        this.sc = new Scanner(System.in);
        this.sistema = SistemaVentaPasajes.getInstance();
    }

    public static UISVP getInstance() {
        if (instancia == null) {
            instancia = new UISVP();
        }
        return instancia;
    }

    public void menu() {
        int opcion = 0;
        do {
            System.out.println("       :::: Menu principal ::::     ");
            System.out.println("");
            System.out.println("1) Crear cliente");
            System.out.println("2) Crear empresa");
            System.out.println("3) Crear terminal");
            System.out.println("4) Crear bus");
            System.out.println("5) Crear viaje");
            System.out.println("6) Contratar tripulante");
            System.out.println("7) Vender pasaje");
            System.out.println("8) Lista de pasajeros");
            System.out.println("9) Lista de ventas generales");
            System.out.println("10) Lista de viajes");
            System.out.println("11) Lista de empresas");
            System.out.println("12) Lista de llegadas y salidas de terminal");
            System.out.println("13) Lista de ventas por empresa");
            System.out.println("14) Consulta Viajes disponibles por fecha");
            System.out.println("15) Salir");
            System.out.println("-----------------------------------------");
            System.out.print("Ingrese numero de opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1: createCliente(); break;
                case 2: crearEmpresa(); break;
                case 3: crearTerminal(); break;
                case 4: createBus(); break;
                case 5: createViaje(); break;
                case 6: contratarTripulante(); break;
                case 7: vendePasajes(); break;
                case 8: listPasajerosViaje(); break;
                case 9: listVentas(); break;
                case 10: listViajes(); break;
                case 11: listarEmpresas(); break;
                case 12: listarLlegadasSalidasTerminal(); break;
                case 13: listarVentasEmpresa(); break;
                case 14:
                    System.out.print("Ingrese fecha [dd/mm/yyyy]: ");
                    try {
                        LocalDate fechaBusqueda = LocalDate.parse(sc.nextLine().trim(), dateFormatter);
                        String[][] disponibles = sistema.getHorariosDisponibles(fechaBusqueda);
                        if (disponibles.length == 0) {
                            System.out.println("No hay viajes disponibles para esa fecha.");
                        } else {
                            for (String[] fila : disponibles) {
                                System.out.println(String.join(" | ", fila));
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Formato de fecha invalido o error de sistema.");
                    }
                    break;
                case 15:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 15);
    }
    private void createCliente() {
        System.out.println("  ::Creando Cliente::  ");
        System.out.print("Rut[1] o Pasaporte[2]: ");
        int tipoId = Integer.parseInt(sc.nextLine().trim());
        IdPersona idCliente = null;
        switch (tipoId) {
            case 1:
                System.out.print("R.U.T: ");
                idCliente = (IdPersona) Rut.of(sc.nextLine().trim());
                break;
            case 2:
                System.out.print("Pasaporte: ");
                String pasaporte = sc.nextLine().trim();
                System.out.print("Nacionalidad: ");
                String nacionalidad = sc.nextLine().trim();
                idCliente = (IdPersona) Pasaporte.of(pasaporte, nacionalidad);
                break;
        }
        System.out.print("Sr[1] o Sra[2]: ");
        int tratamientoOpcion = Integer.parseInt(sc.nextLine().trim());
        Tratamiento tratamiento = (tratamientoOpcion == 1) ? Tratamiento.SR : Tratamiento.SRA;

        System.out.print("Nombres: ");
        String nombres = sc.nextLine().trim();
        System.out.print("Apellido paterno: ");
        String apellidoPaterno = sc.nextLine().trim();
        System.out.print("Apellido materno: ");
        String apellidoMaterno = sc.nextLine().trim();

        Nombre nombreCompleto = new Nombre(tratamiento, nombres, apellidoPaterno, apellidoMaterno);

        System.out.print("Telefono movil: ");
        String telefonoMovil = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        try {
            sistema.createCliente(idCliente, nombreCompleto, telefonoMovil, email);
            System.out.println("");
            System.out.println("...:::: Cliente guardado exitosamente ::::....");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createBus() {
        System.out.println("...:::: Creando un nuevo Bus ::::....");
        System.out.println("");
        System.out.print("Patente : ");
        String patente = sc.nextLine().trim();
        System.out.print("Marca : ");
        String marca = sc.nextLine().trim();
        System.out.print("Modelo : ");
        String modelo = sc.nextLine().trim();
        System.out.print("Numero de asientos : ");
        int numeroAsientos = 0;

        try {
            numeroAsientos = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un numero valido.");
            return;
        }

        System.out.println("");
        System.out.println(":::: Dato de la empresa");
        System.out.print("R.U.T : ");
        String rutString = sc.nextLine().trim();

        try {
            Rut rutEmpresa = Rut.of(rutString);
            sistema.createBus(patente, marca, modelo, numeroAsientos, rutEmpresa);
            System.out.println("");
            System.out.println("...:::: Bus guardado exitosamente ::::....");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("");
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("");
            System.out.println("Error: Formato de RUT invalido.");
        }
    }

    private void createViaje() {
        System.out.println("...:::: Creando un nuevo Viaje ::::....");
        System.out.println("");
        System.out.print("Fecha[dd/mm/yyyy] : ");
        LocalDate fecha = LocalDate.parse(sc.nextLine().trim(), dateFormatter);
        System.out.print("Hora[hh:mm] : ");
        LocalTime hora = LocalTime.parse(sc.nextLine().trim(), timeFormatter);
        System.out.print("Precio : ");
        int precio = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Duracion (minutos) : ");
        int duracion = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Patente Bus : ");
        String patenteBus = sc.nextLine().trim();
        System.out.print("Nro. de conductores : ");
        int numeroConductores = Integer.parseInt(sc.nextLine().trim());

        System.out.println(":: Id Auxiliar ::");
        System.out.print("Rut[1] o Pasaporte[2] : ");
        int tipoIdAuxiliar = Integer.parseInt(sc.nextLine().trim());
        IdPersona idAuxiliar = null;
        if (tipoIdAuxiliar == 1) {
            System.out.print("R.U.T : ");
            idAuxiliar = Rut.of(sc.nextLine().trim());
        } else {
            System.out.print("Pasaporte: ");
            String pasaporte = sc.nextLine().trim();
            System.out.print("Nacionalidad: ");
            String nacionalidad = sc.nextLine().trim();
            idAuxiliar = Pasaporte.of(pasaporte, nacionalidad);
        }

        IdPersona[] idsTripulacion = new IdPersona[1 + numeroConductores];
        idsTripulacion[0] = idAuxiliar;

        for (int i = 0; i < numeroConductores; i++) {
            System.out.println(":: Id Conductor ::");
            System.out.print("Rut[1] o Pasaporte[2] : ");
            int tipoIdConductor = Integer.parseInt(sc.nextLine().trim());
            if (tipoIdConductor == 1) {
                System.out.print("R.U.T : ");
                idsTripulacion[i + 1] = Rut.of(sc.nextLine().trim());
            } else {
                System.out.print("Pasaporte: ");
                String pasaporte = sc.nextLine().trim();
                System.out.print("Nacionalidad: ");
                String nacionalidad = sc.nextLine().trim();
                idsTripulacion[i + 1] = Pasaporte.of(pasaporte, nacionalidad);
            }
        }

        System.out.print("Nombre comuna salida : ");
        String comunaSalida = sc.nextLine().trim();
        System.out.print("Nombre comuna llegada : ");
        String comunaLlegada = sc.nextLine().trim();
        String[] comunas = {comunaSalida, comunaLlegada};

        try {
            sistema.createViaje(fecha, hora, precio, duracion, patenteBus, idsTripulacion, comunas);
            System.out.println("");
            System.out.println("...:::: Viaje guardado exitosamente ::::....");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void vendePasajes() {
        System.out.println("...:::: Venta de pasajes ::::....");
        System.out.println("");
        System.out.println(":::: Datos de la Venta");
        System.out.print("ID Documento : ");
        String idDocumento = sc.nextLine().trim();
        System.out.print("Tipo documento: [1] Boleta [2] Factura : ");
        int tipoDocNum = Integer.parseInt(sc.nextLine().trim());

        TipoDocumento tipoDoc;

        if (tipoDocNum == 1) {
            tipoDoc = TipoDocumento.BOLETA;
        } else {
            tipoDoc = TipoDocumento.FACTURA;
        }

        System.out.print("Fecha de viaje[dd/mm/yyyy] : ");
        LocalDate fechaViaje = LocalDate.parse(sc.nextLine().trim(), dateFormatter);
        System.out.print("Origen (comuna) : ");
        String origen = sc.nextLine().trim();
        System.out.print("Destino (comuna) : ");
        String destino = sc.nextLine().trim();

        System.out.println("");
        System.out.println(":::: Datos del cliente");
        System.out.print("Rut[1] o Pasaporte[2] : ");
        int tipoId = Integer.parseInt(sc.nextLine().trim());
        IdPersona idCliente = null;
        if (tipoId == 1) {
            System.out.print("R.U.T : ");
            idCliente = (IdPersona) Rut.of(sc.nextLine().trim());
        } else {
            System.out.print("Pasaporte: ");
            String numeroPasaporte = sc.nextLine().trim();
            System.out.print("Nacionalidad: ");
            String nacionalidad = sc.nextLine().trim();
            idCliente = (IdPersona) Pasaporte.of(numeroPasaporte, nacionalidad);
        }

        System.out.println("");
        System.out.println(":::: Pasajes a vender");
        System.out.print("Cantidad de pasajes : ");
        int cantidadPasajes = Integer.parseInt(sc.nextLine().trim());

        try {
            sistema.iniciaVenta(idDocumento, tipoDoc, LocalDate.now(), idCliente);
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        String[][] horarios = sistema.getHorariosDisponibles(fechaViaje, origen, destino, cantidadPasajes);
        if (horarios.length == 0) {
            System.out.println("La venta no se puede concretar, No existen viajes registrados/disponibles para esa fecha y ruta.");
            return;
        }

        System.out.println("");
        System.out.println(":::: Listado de horarios disponibles");
        System.out.println("*------------*----------*----------*----------*");
        System.out.println("| BUS        | SALIDA   | VALOR    | ASIENTOS |");
        System.out.println("|------------+----------+----------+----------|");
        for (int i = 0; i < horarios.length; i++) {
            System.out.printf("| %2d | %-6s | %-8s | $%-7s | %8s |\n",
                    (i + 1), horarios[i][0], horarios[i][1], horarios[i][2], horarios[i][3]);
            System.out.println("|------------+----------+----------+----------|");
        }

        System.out.print("Seleccione viaje en [1.." + horarios.length + "] : ");
        int seleccion = Integer.parseInt(sc.nextLine().trim());

        while (seleccion < 1 || seleccion > horarios.length) {
            System.out.print("Opcion invalida. Seleccione viaje en [1.." + horarios.length + "] : ");
            seleccion = Integer.parseInt(sc.nextLine().trim());
        }

        String patenteBus = horarios[seleccion - 1][0];
        LocalTime horaViaje = LocalTime.parse(horarios[seleccion - 1][1]);
        int precioViaje = Integer.parseInt(horarios[seleccion - 1][2]);

        String[] matrizAsientos = sistema.listAsientosDeViaje(fechaViaje, horaViaje, patenteBus);
        System.out.println("");
        System.out.println(":::: Asientos disponibles para el viaje seleccionado");
        for (int i = 0; i < matrizAsientos.length; i++) {
            if (matrizAsientos[i] != null && (matrizAsientos[i].equalsIgnoreCase("Ocupado") || matrizAsientos[i].equals("*"))) {
                System.out.print("[  * ] ");
            } else {
                System.out.print("[" + String.format("%3d", (i + 1)) + "] ");
            }
            if ((i + 1) % 4 == 0) System.out.println("");
        }
        System.out.println("");

        System.out.print("Seleccione sus asientos [separe por , ] : ");
        String inputAsientos = sc.nextLine().trim();
        String[] asientosElegidosStr = inputAsientos.split(",");

        if (asientosElegidosStr.length != cantidadPasajes) {
            System.out.println("Error: Ingreso una cantidad de asientos distinta a la solicitada.");
            return;
        }

        int[] asientosElegidos = new int[cantidadPasajes];
        for (int i = 0; i < cantidadPasajes; i++) {
            asientosElegidos[i] = Integer.parseInt(asientosElegidosStr[i].trim());
        }

        ArrayList<IdPersona> idsPasajerosRegistrados = new ArrayList<>();

        for (int i = 0; i < cantidadPasajes; i++) {
            int asiento = asientosElegidos[i];
            System.out.println("");
            System.out.println(":::: Datos pasajeros " + (i + 1) + " (Asiento " + asiento + ")");

            int tipoIdPasajero = 0;
            while (true) {
                System.out.print("Rut[1] o Pasaporte[2] : ");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) continue;

                try {
                    tipoIdPasajero = Integer.parseInt(input);
                    if (tipoIdPasajero == 1 || tipoIdPasajero == 2) break;
                    else System.out.println("Por favor, ingrese 1 o 2.");
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un numero valido (1 o 2).");
                }
            }

            IdPersona idPasajero = null;
            if (tipoIdPasajero == 1) {
                System.out.print("R.U.T : ");
                idPasajero = (IdPersona) Rut.of(sc.nextLine().trim());
            } else {
                System.out.print("Pasaporte: ");
                String numeroPasaportePasajero = sc.nextLine().trim();
                System.out.print("Nacionalidad: ");
                String nacionalidadPasajero = sc.nextLine().trim();
                idPasajero = (IdPersona) Pasaporte.of(numeroPasaportePasajero, nacionalidadPasajero);
            }

            if (!sistema.getNombrePasajero(idPasajero).isPresent()) {
                System.out.println("Pasajero no registrado. Ingrese sus datos para crearlo:");
                System.out.print("Sr[1] o Sra[2]: ");
                int tratamientoOpcionPasajero = Integer.parseInt(sc.nextLine().trim());
                Tratamiento tratamientoPasajero = (tratamientoOpcionPasajero == 1) ? Tratamiento.SR : Tratamiento.SRA;
                System.out.print("Nombres: ");
                String nombresPasajero = sc.nextLine().trim();
                System.out.print("Apellido paterno: ");
                String apellidoPaternoPasajero = sc.nextLine().trim();
                System.out.print("Apellido materno: ");
                String apellidoMaternoPasajero = sc.nextLine().trim();
                System.out.print("Telefono movil: ");
                String telefonoPasajero = sc.nextLine().trim();
                System.out.print("Nombre de Contacto: ");
                String nombreContacto = sc.nextLine().trim();
                System.out.print("Telefono Contacto: ");
                String telefonoContacto = sc.nextLine().trim();

                Nombre nombrePasajeroObj = new Nombre(tratamientoPasajero, nombresPasajero, apellidoPaternoPasajero, apellidoMaternoPasajero);
                Nombre nombreContactoObj = new Nombre(Tratamiento.SR, nombreContacto, "", "");

                try {
                    sistema.createPasajero(idPasajero, nombrePasajeroObj, telefonoPasajero, nombreContactoObj, telefonoContacto);
                } catch (SistemaVentaPasajesException e) {
                    System.out.println("Error al crear pasajero: " + e.getMessage());
                }
            }

            try {
                sistema.vendePasaje(idDocumento, tipoDoc, fechaViaje, horaViaje, patenteBus, asiento, idPasajero);
                System.out.println(":::: Pasaje agregado exitosamente :::::");
                idsPasajerosRegistrados.add(idPasajero);
            } catch (SistemaVentaPasajesException e) {
                System.out.println("Error al registrar el pasaje del asiento " + asiento + ": " + e.getMessage());
            }
        }

        int montoTotal = 0;
        try {
            montoTotal = sistema.getMontoVenta(idDocumento, tipoDoc).orElse(0);
        } catch (Exception e) {
            montoTotal = cantidadPasajes * precioViaje;
        }

        System.out.println("");
        System.out.println(":::: Monto total de la venta: $" + montoTotal);
        System.out.println(":::: Pago de la venta");
        System.out.print("Efectivo[1] o Tarjeta[2] : ");
        int tipoPago = Integer.parseInt(sc.nextLine().trim());

        try {
            if (tipoPago == 1) {
                sistema.pagaVenta(idDocumento, tipoDoc);
            } else {
                System.out.print("Numero de tarjeta : ");
                long numeroTarjeta = Long.parseLong(sc.nextLine().trim());
                sistema.pagaVenta(idDocumento, tipoDoc, numeroTarjeta);
            }
            System.out.println("...:::: Venta realizada exitosamente ::::...");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error en el pago: " + e.getMessage());
        }
    }

    private void listPasajerosViaje() {
        System.out.println("...:::: Listado de pasajeros de un viaje ::::....");
        System.out.println("");
        System.out.print("Fecha del viaje[dd/mm/yyyy]: ");
        LocalDate fecha = LocalDate.parse(sc.nextLine().trim(), dateFormatter);

        System.out.print("Hora del viaje[hh:mm]: ");
        LocalTime hora = LocalTime.parse(sc.nextLine().trim(), timeFormatter);

        System.out.print("Patente bus: ");
        String patente = sc.nextLine().trim();

        try {
            String[][] pasajeros = sistema.listPasajeros(fecha, hora, patente);

            System.out.println("");
            System.out.println("*---------*----------------*--------------------------*--------------------------*-------------------*");
            System.out.println("| RUT/PASS | PASAJERO                 | CONTACTO                 | TELEFONO CONTACTO |");
            System.out.println("|----------+--------------------------+--------------------------+-------------------|");

            for (int i = 0; i < pasajeros.length; i++) {
                System.out.printf("| %-8s | %-24s | %-24s | %-17s |\n",
                        pasajeros[i][0], pasajeros[i][1], pasajeros[i][2], pasajeros[i][3]);
                System.out.println("|----------+--------------------------+--------------------------+-------------------|");
            }
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listVentas() {
        System.out.println("...:::: Listado de ventas ::::....");
        System.out.println("");
        String[][] ventas = sistema.listVentas();
        if (ventas == null || ventas.length == 0) {
            System.out.println("Error: No existen ventas registradas en el sistema");
            return;
        }

        System.out.println("*--------------*-----------*------------*----------------*--------------------------*--------------*-------------*");
        System.out.println("| ID DOCUMENTO | TIPO DOCU | FECHA      | RUT/PASAPORTE  | CLIENTE                  | CANT BOLETOS | TOTAL VENTA |");
        System.out.println("|--------------+-----------+------------+----------------+--------------------------+--------------+-------------|");

        for (int i = 0; i < ventas.length; i++) {
            System.out.printf("| %-12s | %-9s | %-10s | %-14s | %-24s | %12s | $%10s |\n",
                    ventas[i][0], ventas[i][1], ventas[i][2], ventas[i][3], ventas[i][4], ventas[i][5], ventas[i][6]);
            System.out.println("|--------------+-----------+------------+----------------+--------------------------+--------------+-------------|");
        }
    }

    private void listViajes() {
        System.out.println("...:::: Listado de viajes ::::....");
        System.out.println("");
        String[][] viajes = sistema.listViajes();
        if (viajes == null || viajes.length == 0) {
            System.out.println("Error: No existen viajes registrados en el sistema");
            return;
        }

        System.out.println("*------------*----------*----------*-------------*----------*");
        System.out.println("| FECHA      | HORA     | PRECIO   | DISPONIBLES | PATENTE  |");
        System.out.println("|------------+----------+----------+-------------+----------|");

        for (int i = 0; i < viajes.length; i++) {
            System.out.printf("| %-10s | %-8s | $%-7s | %11s | %-8s |\n",
                    viajes[i][0], viajes[i][1], viajes[i][2], viajes[i][3], viajes[i][4]);
            System.out.println("|------------+----------+----------+-------------+----------|");
        }
    }

    private void crearEmpresa() {
        System.out.println(":::: Creando una nueva Empresa ::::");
        System.out.print("R.U.T : ");
        String rut = sc.nextLine().trim();
        System.out.print("Nombre : ");
        String nombre = sc.nextLine().trim();
        System.out.print("url : ");
        String url = sc.nextLine().trim();
        try {
            sistema.createEmpresa(Rut.of(rut), nombre, url);
            System.out.println(":::: Empresa guardada exitosamente ::::");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void contratarTripulante() {
        System.out.println("...:::: Contratando un nuevo Tripulante ::::....");
        System.out.println("");
        System.out.println(":::: Dato de la Empresa");
        System.out.print("R.U.T : ");
        Rut rutEmpresa = Rut.of(sc.nextLine().trim());

        System.out.println("");
        System.out.println(":::: Datos tripulante");
        System.out.print("Auxiliar[1] o Conductor[2] : ");
        int tipoTripulante = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Rut[1] o Pasaporte[2] : ");
        int tipoId = Integer.parseInt(sc.nextLine().trim());
        IdPersona idTripulante = null;
        if (tipoId == 1) {
            System.out.print("R.U.T : ");
            idTripulante = Rut.of(sc.nextLine().trim());
        } else {
            System.out.print("Pasaporte: ");
            String pasaporte = sc.nextLine().trim();
            System.out.print("Nacionalidad: ");
            String nacionalidad = sc.nextLine().trim();
            idTripulante = Pasaporte.of(pasaporte, nacionalidad);
        }

        System.out.print("Sr.[1] o Sra.[2] : ");
        int tratamientoOpcion = Integer.parseInt(sc.nextLine().trim());
        Tratamiento tratamiento = (tratamientoOpcion == 1) ? Tratamiento.SR : Tratamiento.SRA;

        System.out.print("Nombres : ");
        String nombres = sc.nextLine().trim();
        System.out.print("Apellido Paterno : ");
        String apellidoPaterno = sc.nextLine().trim();
        System.out.print("Apellido Materno : ");
        String apellidoMaterno = sc.nextLine().trim();
        Nombre nombreTripulante = new Nombre(tratamiento, nombres, apellidoPaterno, apellidoMaterno);

        System.out.print("Calle : ");
        String calle = sc.nextLine().trim();
        System.out.print("Numero : ");
        int numero = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Comuna : ");
        String comuna = sc.nextLine().trim();
        utilidades.Direccion direccion = new utilidades.Direccion(calle, numero, comuna);

        try {
            if (tipoTripulante == 1) {
                sistema.hireAuxiliarForEmpresa(rutEmpresa, idTripulante, nombreTripulante, direccion);
                System.out.println("...:::: Auxiliar contratado exitosamente ::::....");
            } else {
                sistema.hireConductorForEmpresa(rutEmpresa, idTripulante, nombreTripulante, direccion);
                System.out.println("...:::: Conductor contratado exitosamente ::::....");
            }
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearTerminal() {
        System.out.println("..:::: Creando un nuevo Terminal ::::....");
        System.out.print("Nombre : ");
        String nombre = sc.nextLine().trim();
        System.out.print("Calle : ");
        String calle = sc.nextLine().trim();
        System.out.print("Numero : ");
        int numero = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Comuna : ");
        String comuna = sc.nextLine().trim();

        try {
            sistema.createTerminal(nombre, new utilidades.Direccion(calle, numero, comuna));
            System.out.println("...:::: Terminal guardado exitosamente ::::....");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarEmpresas() {
        System.out.println("..:::: Listado de empresas ::::..");
        String[][] empresas = sistema.listEmpresas();

        if (empresas == null || empresas.length == 0) {
            System.out.println("No hay empresas registradas.");
            return;
        }

        System.out.println("*----------------*--------------------------*--------------------------*-----------------*------------*-------------*");
        System.out.println("| RUT EMPRESA    | NOMBRE                   | URL                      | NRO. TRIPULANTES| NRO. BUSES | NRO. VENTAS |");
        System.out.println("|----------------+--------------------------+--------------------------+-----------------+------------+-------------|");
        for (String[] empresa : empresas) {
            System.out.println(String.format("| %-14s | %-24s | %-24s | %15s | %10s | %11s |", empresa[0], empresa[1], empresa[2], empresa[3], empresa[4], empresa[5]));
            System.out.println("|----------------+--------------------------+--------------------------+-----------------+------------+-------------|");
        }
    }

    private void listarLlegadasSalidasTerminal() {
        System.out.println("...:::: Listado de llegadas y salidas de un terminal ::::....");
        System.out.print("Nombre terminal : ");
        String nombreTerminal = sc.nextLine().trim();
        System.out.print("Fecha[dd/mm/yyyy] : ");

        try {
            LocalDate fecha = LocalDate.parse(sc.nextLine().trim(), dateFormatter);
            String[][] viajesTerminal = sistema.listLlegadasSalidasTerminal(nombreTerminal, fecha);

            if (viajesTerminal == null || viajesTerminal.length == 0) {
                System.out.println("No hay llegadas ni salidas programadas para esa fecha.");
            } else {
                System.out.println("*----------------*----------*-------------*--------------------------*----------------*");
                System.out.println("| LLEGADA/SALIDA | HORA     | PATENTE BUS | NOMBRE EMPRESA           | NRO. PASAJEROS |");
                System.out.println("|----------------+----------+-------------+--------------------------+----------------|");
                for (String[] viaje : viajesTerminal) {
                    System.out.println(String.format("| %-14s | %-8s | %-11s | %-24s | %14s |", viaje[0], viaje[1], viaje[2], viaje[3], viaje[4]));
                    System.out.println("|----------------+----------+-------------+--------------------------+----------------|");
                }
            }
        } catch (SistemaVentaPasajesException e) {
            System.out.println("*** Error: " + e.getMessage() + " ***");
        } catch (Exception e) {
            System.out.println("Formato de fecha invalido.");
        }
    }

    private void listarVentasEmpresa() {
        System.out.println("...:::: Listado de ventas de una empresa ::::....");
        System.out.print("R.U.T : ");
        String rutString = sc.nextLine().trim();

        try {
            Rut rutEmpresa = Rut.of(rutString);
            String[][] ventas = sistema.listVentasEmpresa(rutEmpresa);

            if (ventas == null || ventas.length == 0) {
                System.out.println("La empresa no registra ventas.");
            } else {
                System.out.println("*------------*-----------*--------------*-----------------*");
                System.out.println("| FECHA      | TIPO      | MONTO PAGADO | TIPO PAGO       |");
                System.out.println("|------------+-----------+--------------+-----------------|");
                for (String[] venta : ventas) {
                    System.out.println(String.format("| %-10s | %-9s | $%-11s | %-15s |", venta[0], venta[1], venta[2], venta[3]));
                    System.out.println("|------------+-----------+--------------+-----------------|");
                }
            }
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al procesar el formato del RUT.");
        }
    }
}