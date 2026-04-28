import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    Scanner sc;
    SistemaVentaPasajes sistema;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public Main() {
        this.sc = new Scanner(System.in);
        this.sistema = new SistemaVentaPasajes();
        //rellena automatico de la op 1 asta la 3
        //esto solo rellena los casos con rut, para pasaporte debe hacer opciones dadas por la terminal e ingresar otro cliente  desde 0
        cargarDatosPrueba();
    }
    public static void main(String[] args) {
        Main app = new Main();
        app.menu();
    }
    private void menu() {
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

        boolean exito = sistema.createCliente(idCliente, nomCompleto, telefono, email);
        if (exito) {
            System.out.println("\n...:::: Cliente guardado exitosamente ::::....");
        } else {
            System.out.println("Error: El cliente ya existe.");
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

        boolean exito = sistema.createBus(patente, marca, modelo, numAsientos);
        if (exito) {
            System.out.println("\n...:::: Bus guardado exitosamente ::::....");
        } else {
            System.out.println("Error: Ya existe un bus con esa patente.");
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

        boolean exito = sistema.createViaje(fecha, hora, precio, patente);
        if (exito) {
            System.out.println("\n...:::: Viaje guardado exitosamente ::::....");
        } else {
            System.out.println("Error: No se pudo crear el viaje. Verifique si el bus existe o si el viaje se superpone.");
        }
    }

    private void vendePasajes() {
        System.out.println("...:::: Venta de pasajes ::::....\n");
        System.out.println(":::: Datos de la Venta");
        System.out.print("ID Documento : ");
        String idDocumento= sc.nextLine().trim();
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
        }else{
            System.out.print("Pasaporte: ");
            String num = sc.nextLine().trim();
            System.out.print("Nacionalidad: ");
            String nac = sc.nextLine().trim();
            idCliente = (IdPersona) Pasaporte.of(num, nac);
        }

        boolean ventaIniciada = sistema.iniciaVenta(idDocumento, tipoDoc, fechaVenta, idCliente);
        if (!ventaIniciada) {
            System.out.println("La venta no se puede concretar. Verifique ID de venta o cliente.");
            return;
        }

        System.out.println("Nombre Cliente : " + sistema.getNombreCliente(idCliente));

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

        String[][] matrizAsientos = sistema.listAsientosDeViaje(fechaViaje, horaViaje, patenteBus);
        System.out.println("\n:::: Asientos disponibles para el viaje seleccionado");
        for (int i = 0; i < matrizAsientos.length; i++) {
            if (matrizAsientos[i][1].equals("Ocupado")) {
                System.out.print("[  * ] ");
            } else {
                System.out.print("[" + String.format("%3s", matrizAsientos[i][0]) + "] ");
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

                if (input.isEmpty()) {
                    continue;
                }

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

            if (sistema.findPasajero(idPasajero) == null) {
                System.out.println("Error: El pasajero con ID " + idPasajero + " no esta registrado.");
                System.out.println("Debe registrarlo previamente como cliente (Opcion 1) para continuar.");
                i--;
                continue;
            }

            boolean pasajeVendido = sistema.vendePasaje(idDocumento, fechaViaje, horaViaje, patenteBus, asiento, idPasajero);

            if (pasajeVendido) {
                System.out.println("\n:::: Pasaje agregado exitosamente");
                idsPasajerosRegistrados.add(idPasajero);
            } else {
                System.out.println("\nError al registrar el pasaje del asiento " + asiento);
            }
        }

        int montoTotal = cantidadPasajes * precioViaje;
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

        String[][] pasajeros = sistema.listPasajeros(fecha, hora, patente);

        if (pasajeros == null || pasajeros.length == 0) {
            System.out.println("Error: no existe el bus o un viaje con los datos indicados.");
            return;
        }

        System.out.println("");
        System.out.println("*---------*----------------*--------------------------*--------------------------*-------------------*");
        System.out.println("| RUT/PASS | PASAJERO                 | CONTACTO                 | TELEFONO CONTACTO |");
        System.out.println("|----------+--------------------------+--------------------------+-------------------|");

        for (int i = 0; i < pasajeros.length; i++) {
            System.out.printf("| %-8s | %-24s | %-24s | %-17s |\n",
                    pasajeros[i][0], pasajeros[i][1], pasajeros[i][2], pasajeros[i][3]);
            System.out.println("|----------+--------------------------+--------------------------+-------------------|");
        }
    }

    private void listVentas() {
        System.out.println("...:::: Listado de ventas ::::....\n");
        String[][] ventas = sistema.listVentas();
        if (ventas == null || ventas.length == 0) {
            System.out.println("Error: No existen ventas registradas en el sistema.");
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
            System.out.println("Error: No existen viajes registrados en el sistema.");
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

    //este lo iso gpt para rellenar auto 2 de cada, ahora tiempo y errores al escribir en la terminal

    private void cargarDatosPrueba() {
        System.out.println("[Sistema] Cargando datos de prueba automaticamente...");

        // 1. Crear Clientes de prueba
        IdPersona idCliente1 = Rut.of("11.111.111-1");
        Nombre nomCliente1 = new Nombre(Tratamiento.SR, "Juan Jose", "Perez", "Rios");
        sistema.createCliente(idCliente1, nomCliente1, "948753235", "jjperez@gmail.com");

        // IMPORTANTE: Registrarlo tambien como PASAJERO para que la Opcion 4 lo encuentre
        sistema.createPasajero(idCliente1, nomCliente1, "948753235", new Nombre(Tratamiento.SR, "Contacto Emergencia", "", ""), "123456");

        IdPersona idCliente2 = Rut.of("22.222.222-2");
        Nombre nomCliente2 = new Nombre(Tratamiento.SRA, "Maria Paz", "Daza", "Barrera");
        sistema.createCliente(idCliente2, nomCliente2, "912345678", "mpdaza@correo.cl");

        // Registrar segundo pasajero
        sistema.createPasajero(idCliente2, nomCliente2, "912345678", new Nombre(Tratamiento.SRA, "Contacto Emergencia", "", ""), "654321");

        // 2. Crear Buses de prueba
        sistema.createBus("AB.CD-12", "Mercedes Benz", "Centauro", 45);
        sistema.createBus("EF.GH-34", "Volvo", "B430R", 52);

        // 3. Crear Viajes de prueba
        LocalDate fechaViaje1 = LocalDate.of(2026, 8, 31);
        LocalTime horaViaje1 = LocalTime.of(10, 0);
        sistema.createViaje(fechaViaje1, horaViaje1, 3000, "AB.CD-12");

        LocalDate fechaViaje2 = LocalDate.of(2026, 8, 31);
        LocalTime horaViaje2 = LocalTime.of(14, 30);
        sistema.createViaje(fechaViaje2, horaViaje2, 3500, "EF.GH-34");

        System.out.println("[Sistema] ¡Datos cargados con exito!\n");
    }
}