import excepciones.SistemaVentaPasajesException;

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
            System.out.println("2) Crear bus");
            System.out.println("3) Crear viaje");
            System.out.println("4) Vender pasaje");
            System.out.println("5) Lista de pasajeros");
            System.out.println("6) Lista de ventas");
            System.out.println("7) Lista de viajes");
            System.out.println("8) Consulta Viajes disponibles por fecha");
            System.out.println("9) Salir");
            System.out.println("-----------------------------------------");
            System.out.print("Ingrese numero de opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1: createCliente(); break;
                case 2: createBus(); break;
                case 3: createViaje(); break;
                case 4: vendePasajes(); break;
                case 5: listPasajerosViaje(); break;
                case 6: listVentas(); break;
                case 7: listViajes(); break;
                case 8:
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
                        System.out.println("Formato de fecha invalido.");
                    }
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 9);
    }

    private void createCliente() {
        System.out.println("  ::Creando Cliente::  ");
        System.out.print("Rut[1] o Pasaporte[2]: ");
        int RoP = Integer.parseInt(sc.nextLine().trim());
        IdPersona idCliente = null;
        switch (RoP) {
            case 1:
                System.out.print("R.U.T: ");
                idCliente = (IdPersona) Rut.of(sc.nextLine().trim());
                break;
            case 2:
                System.out.print("Pasaporte: ");
                String pas= sc.nextLine().trim();
                System.out.print("Nacionalidad: ");
                String nac = sc.nextLine().trim();
                idCliente = (IdPersona) Pasaporte.of(pas, nac);
                break;
        }
        System.out.print("Sr[1] o Sra[2]: ");
        int SoSra = Integer.parseInt(sc.nextLine().trim());
        Tratamiento trat = (SoSra == 1) ? Tratamiento.SR : Tratamiento.SRA;

        System.out.print("Nombres: ");
        String nombre= sc.nextLine().trim();
        System.out.print("Apellido paterno: ");
        String aP= sc.nextLine().trim();
        System.out.print("Apellido materno: ");
        String aM= sc.nextLine().trim();

        Nombre nomCompleto = new Nombre(trat, nombre, aP, aM);

        System.out.print("Telefono movil: ");
        String telefono= sc.nextLine().trim();
        System.out.print("Email: ");
        String email= sc.nextLine().trim();

        try {
            sistema.createCliente(idCliente, nomCompleto, telefono, email);
            System.out.println("\n...:::: Cliente guardado exitosamente ::::....");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createBus() {
        System.out.println("...:::: Creacion de un nuevo BUS ::::....\n");
        System.out.print("Patente : ");
        String patente= sc.nextLine().trim();
        System.out.print("Marca : ");
        String marca= sc.nextLine().trim();
        System.out.print("Modelo : ");
        String modelo= sc.nextLine().trim();
        System.out.print("Numero de asientos : ");
        int numAsientos= Integer.parseInt(sc.nextLine().trim());
        try {
            sistema.createBus(patente, marca, modelo, numAsientos);
            System.out.println("\n...:::: Bus guardado exitosamente ::::....");
        } catch (SistemaVentaPasajesException e) {
            System.out.print("Error: " + e.getMessage());
        }
    }

    private void createViaje() {
        System.out.println("...:::: Creacion de un nuevo Viaje ::::....\n");
        System.out.print("Fecha[dd/mm/yyyy] : ");
        LocalDate fecha= LocalDate.parse(sc.nextLine().trim(), dateFormatter);
        System.out.print("Hora[hh:mm] : ");
        LocalTime hora= LocalTime.parse(sc.nextLine().trim(), timeFormatter);
        System.out.print("Precio : ");
        int precio= Integer.parseInt(sc.nextLine().trim());
        System.out.print("Patente Bus : ");
        String patente= sc.nextLine().trim();
        try {
            sistema.createViaje(fecha, hora, precio, patente);
            System.out.println("\n...:::: Viaje guardado exitosamente ::::....");
        }catch (SistemaVentaPasajesException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void vendePasajes() {
        System.out.println("...:::: Venta de pasajes ::::....\n");
        System.out.println(":::: Datos de la Venta");
        System.out.print("ID Documento : ");
        String idDocumento = sc.nextLine().trim();
        System.out.print("Tipo documento: [1] Boleta [2] Factura : ");
        int tipoDocNum = Integer.parseInt(sc.nextLine().trim());

        TipoDocumento tipoDoc = (tipoDocNum == 1) ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;

        System.out.print("Fecha de venta[dd/mm/yyyy] : ");
        LocalDate fechaVenta = LocalDate.parse(sc.nextLine().trim(), dateFormatter);

        System.out.println("\n:::: Datos del cliente");
        System.out.print("Rut[1] o Pasaporte[2] : ");
        int tipoId = Integer.parseInt(sc.nextLine().trim());
        IdPersona idCliente = null;
        if (tipoId == 1) {
            System.out.print("R.U.T : ");
            idCliente = (IdPersona) Rut.of(sc.nextLine().trim());
        } else {
            System.out.print("Pasaporte: ");
            String num = sc.nextLine().trim();
            System.out.print("Nacionalidad: ");
            String nac = sc.nextLine().trim();
            idCliente = (IdPersona) Pasaporte.of(num, nac);
        }
        try {
            sistema.iniciaVenta(idDocumento, tipoDoc, fechaVenta, idCliente);
        }catch (SistemaVentaPasajesException e){
            System.out.println("Error: " + e.getMessage());
            return;
        }

        String nombreClienteStr = "Desconocido";
        String[][] listadoVentas = sistema.listVentas();
        for (String[] filaVenta : listadoVentas) {
            if (filaVenta[0].equals(idDocumento)) {
                nombreClienteStr = filaVenta[4];
                break;
            }
        }
        System.out.println("Nombre Cliente : " + nombreClienteStr);

        System.out.println("\n:::: Pasajes a vender");
        System.out.print("Cantidad de pasajes : ");
        int cantidadPasajes = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Fecha de viaje[dd/mm/yyyy] : ");
        LocalDate fechaViaje = LocalDate.parse(sc.nextLine().trim(), dateFormatter);

        String[][] horarios = sistema.getHorariosDisponibles(fechaViaje);
        if (horarios.length == 0) {
            System.out.println("La venta no se puede concretar, No existen viajes registrados/disponibles para esa fecha.");
            return;
        }

        System.out.println("\n:::: Listado de horarios disponibles");
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
        System.out.println("\n:::: Asientos disponibles para el viaje seleccionado");
        for (int i = 0; i < matrizAsientos.length; i++) {
            if (matrizAsientos[i] != null && matrizAsientos[i].equalsIgnoreCase("Ocupado")) {
                System.out.print("[  * ] ");
            } else {
                System.out.print("[" + String.format("%3d", (i + 1)) + "] ");
            }
            if ((i + 1) % 4 == 0) System.out.println();
        }
        System.out.println();

        System.out.print("\nSeleccione sus asientos [separe por , ] : ");
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
            System.out.println("\n:::: Datos pasajeros " + (i + 1) + " (Asiento " + asiento + ")");

            int tipoIdPas = 0;
            while (true) {
                System.out.print("Rut[1] o Pasaporte[2] : ");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) continue;

                try {
                    tipoIdPas = Integer.parseInt(input);
                    if (tipoIdPas == 1 || tipoIdPas == 2) break;
                    else System.out.println("Por favor, ingrese 1 o 2.");
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un numero valido (1 o 2).");
                }
            }

            IdPersona idPasajero = null;
            if (tipoIdPas == 1) {
                System.out.print("R.U.T : ");
                idPasajero = (IdPersona) Rut.of(sc.nextLine().trim());
            } else {
                System.out.print("Pasaporte: ");
                String numP = sc.nextLine().trim();
                System.out.print("Nacionalidad: ");
                String nacP = sc.nextLine().trim();
                idPasajero = (IdPersona) Pasaporte.of(numP, nacP);
            }

            if (sistema.getNombrePasajero(idPasajero) == null) {
                System.out.println("Pasajero no registrado. Ingrese sus datos para crearlo:");
                System.out.print("Sr[1] o Sra[2]: ");
                int SoSraPax = Integer.parseInt(sc.nextLine().trim());
                Tratamiento tratPax = (SoSraPax == 1) ? Tratamiento.SR : Tratamiento.SRA;
                System.out.print("Nombres: ");
                String nomPax = sc.nextLine().trim();
                System.out.print("Apellido paterno: ");
                String apPax = sc.nextLine().trim();
                System.out.print("Apellido materno: ");
                String amPax = sc.nextLine().trim();
                System.out.print("Telefono movil: ");
                String fonoPax = sc.nextLine().trim();
                System.out.print("Nombre de Contacto: ");
                String nomContacto = sc.nextLine().trim();
                System.out.print("Telefono Contacto: ");
                String fonoContacto = sc.nextLine().trim();

                Nombre nombrePasajeroObj = new Nombre(tratPax, nomPax, apPax, amPax);
                Nombre nombreContactoObj = new Nombre(Tratamiento.SR, nomContacto, "", "");

                try {
                    sistema.createPasajero(idPasajero, nombrePasajeroObj, fonoPax, nombreContactoObj, fonoContacto);
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

        int montoTotal = sistema.getMontoVenta(idDocumento, tipoDoc);
        System.out.println("\n:::: Monto total de la venta: $" + montoTotal);
        System.out.println("...:::: Venta generada exitosamente ::::....");

        System.out.println("\n:::: Imprimiendo los pasajes\n");
        for (int i = 0; i < idsPasajerosRegistrados.size(); i++) {
            IdPersona paxId = idsPasajerosRegistrados.get(i);
            int numAsiento = asientosElegidos[i];
            String paxName = sistema.getNombrePasajero(paxId);

            System.out.println("------------------ PASAJE ------------------");
            System.out.println("NUMERO DE PASAJE : " + (System.currentTimeMillis() + i));
            System.out.println("FECHA DE VIAJE   : " + fechaViaje.format(dateFormatter));
            System.out.println("HORA DE VIAJE    : " + horaViaje.format(timeFormatter));
            System.out.println("PATENTE BUS      : " + patenteBus);
            System.out.println("ASIENTO          : " + numAsiento);
            System.out.println("RUT/PASAPORTE    : " + paxId.toString());
            System.out.println("NOMBRE PASAJERO  : " + paxName);
            System.out.println("--------------------------------------------\n");
        }
    }

    private void listPasajerosViaje() {
        System.out.println("...:::: Listado de pasajeros de un viaje ::::....\n");
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
        System.out.println("...:::: Listado de ventas ::::....\n");
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
        System.out.println("...:::: Listado de viajes ::::....\n");
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
}

