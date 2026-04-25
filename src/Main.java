import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    Scanner sc;
    SistemaVentaPasajes sistema;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public Main() {
        this.sc = new Scanner(System.in);
        this.sistema = new SistemaVentaPasajes();
    }
    public static void main(String[] args) {
        Main app = new Main();
        app.menu();

    }
    private void menu() {
        int opcion = 0;
        do {
            System.out.println("       :::: Menú principal ::::     ");
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
            System.out.print("Ingrese número de opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    createCliente();
                    break;
                case 2:
                    createBus();
                    break;
                case 3:
                    createViaje();
                    break;
                case 4:
                    vendePasajes();
                    break;
                case 5:
                    listPasajerosViaje();
                    break;
                case 6:
                    listVentas();
                    break;
                case 7:
                    listViajes();
                    break;
                case 8:
                    System.out.print("Ingrese fecha [dd/mm/yyyy]: ");
                    LocalDate fechaBusqueda = LocalDate.parse(sc.nextLine(), dateFormatter);
                    String[][] disponibles = sistema.getHorariosDisponibles(fechaBusqueda);
                    for (String[] fila : disponibles) {
                        System.out.println(String.join(" | ", fila));
                    }
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 9);
    }
    private void createCliente() {
        System.out.println("  ::Creando Cliente::  ");
        System.out.println("Rut[1] o Pasaporte[2]: ");
        int RoP = sc.nextInt();
        IdPersona idCliente = null;
        switch (RoP) {
            case 1:
                System.out.println("R.U.T: ");
                idCliente = (IdPersona) Rut.of(sc.next());
                break;
            case 2:
                System.out.println("Pasaporte: ");
                String pas= sc.next();
                System.out.println("Nacionalidad: ");
                String nac = sc.next();
                idCliente = (IdPersona) Pasaporte.of(pas, nac);
                break;
        }
        System.out.println("Sr[1] o Sra[2]: ");
        int SoSra = sc.nextInt();
        Tratamiento trat;
        if (SoSra == 1) {
            trat = Tratamiento.SR;
        } else {
            trat = Tratamiento.SRA;
        }

        System.out.println("Nombre: ");
        sc.nextLine();
        String nombre= sc.nextLine();
        System.out.println("Apellido paterno: ");
        String aP= sc.next();
        System.out.println("Apellido materno: ");
        String aM= sc.next();

        Nombre nomCompleto = new Nombre(trat, nombre, aP, aM);
        nomCompleto.setTratamiento(trat);
        nomCompleto.setNombres(nombre);
        nomCompleto.setApellidoPaterno(aP);
        nomCompleto.setApellidoMaterno(aM);


        System.out.println("Telefono: ");
        String telefono= sc.next();
        System.out.println("email: ");
        String email= sc.next();

        sistema.createCliente(idCliente, nomCompleto, telefono, email);

        System.out.println("   :::::::Cliente guardado exitosamente::::::   ");
    }//createCliente

    private void createBus() {
        System.out.println("  ::Creando Bus ::  ");
        System.out.println("Patente: ");
        String patente= sc.next();
        System.out.println("Marca: ");
        String marca= sc.next();
        System.out.println("Modelo: ");
        String modelo= sc.next();
        System.out.println("Numero de asietnos: ");
        int numAsientos= sc.nextInt();
        sistema.createBus(patente, marca, modelo, numAsientos);
        System.out.println("::::::Bus guardado exitosamente::::::");
    }//createbus

    private void createViaje() {
        System.out.println("  ::Creando Viaje ::  ");
        System.out.println("Fecha [dd/mm/yyyy]: ");
        LocalDate fecha= LocalDate.parse(sc.nextLine(), dateFormatter);
        System.out.println("Hora[hh:mm");
        LocalTime hora= LocalTime.parse(sc.nextLine(), timeFormatter);
        System.out.println("Precio: ");
        int precio= sc.nextInt();
        System.out.println("Patente bus: ");
        String patente= sc.next();
        sistema.createViaje(fecha, hora, precio, patente);
        System.out.println("::::Viaje guardado exitosamente::::::");
    }//createViaje

    private void vendePasajes() {
        System.out.println("  ::Creando Viaje ::  ");
        System.out.println(" ");
        System.out.println(":::::Dato de la venta");
        System.out.println("ID Documento: ");
        String idDocumento= sc.next();
        System.out.println("Tipo de documento: [1]Boleta [2]Factura: ");//aqui
        int tipoDocNum = sc.nextInt();

        TipoDocumento tipoDoc = null;
        switch (tipoDocNum) {
            case 1:
                tipoDoc = TipoDocumento.BOLETA;
                break;
            case 2:
                tipoDoc = TipoDocumento.FACTURA;
                break;
        }

        System.out.println("Fecha de la venta [dd/mm/yyyy]: ");
        sc.nextLine();
        LocalDate fechaVenta = LocalDate.parse(sc.next(), dateFormatter);

        System.out.println("");
        System.out.println("::: Datos del cliente");
        System.out.println("Rut[1] o Pasaporte[2]: ");
        int tipoId = sc.nextInt();
        IdPersona idCliente = null;
        if (tipoId == 1) {
            System.out.print("R.U.T: ");
            idCliente = (IdPersona) Rut.of(sc.next());
        }else{
                System.out.print("Pasaporte: ");
                String num = sc.next();
                System.out.print("Nacionalidad: ");
                String nac = sc.next();
                idCliente = (IdPersona) Pasaporte.of(num, nac);

        }
        // Validaciones requeridas por la Figura 7 para concretar o terminar la venta
        if (sistema.listViajes().length == 0) {
            System.out.println("La venta no se puede concretar, No existen viajes registrados");
            return;
        }
        boolean ventaIniciada = sistema.iniciaVenta(idDocumento, tipoDoc, fechaVenta, idCliente);

        if (!ventaIniciada) {
            System.out.println("La venta no se puede concretar, Verifique ID de venta o cliente");
            return;
        }
        System.out.println("Nombre Cliente: " + sistema.getNombrePasajero(idCliente));
    }//ventaViaje

    private void listPasajerosViaje() {
        System.out.println("::: Listado de pasajeros de un viaje :::");
        System.out.println(" ");
        System.out.println("Fecha del viaje[dd/mm/yyyy]: ");
        LocalDate fecha = LocalDate.parse(sc.next(), dateFormatter);

        System.out.println("Hora del viaje[hh:mm]: ");
        LocalTime hora = LocalTime.parse(sc.next(), timeFormatter);

        System.out.println("Patente bus: ");
        String patente = sc.next();

        // pide la matris a sistema
        String[][] pasajeros = sistema.listPasajeros(fecha, hora, patente);

        // si no existe viaje/bus da un mensaje
        if (pasajeros == null) {
            System.out.println("Error: No existe el bus o un viaje con los datos indicados.");
            return;
        }

        System.out.println("");
        System.out.println("| ASIENTO | RUT/PASS | PASAJERO | CONTACTO | TELEFONO CONTACTO |");
        System.out.println("----------------------------------------------------------------");

        for (int i = 0; i < pasajeros.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < pasajeros[i].length; j++) {
                System.out.print(pasajeros[i][j]);//imprime dato
                if (j < pasajeros[i].length - 1) {
                    System.out.print(" | ");//imprime sparador si no es la ultima columna
                }
            }
            System.out.println(" |");
            System.out.println("----------------------------------------------------------------");
        }
    }//listPasajerosViaje

    private void listVentas() {
        System.out.println(" ::: Listado de ventas :::");
        //se pide ingresar una fecha para mostrar las ventas respecitvas
        System.out.println("Fecha de las ventas [dd/mm/yyyy]: ");
        LocalDate fechaBusqueda = LocalDate.parse(sc.next(), dateFormatter);
        String fechaFiltro = fechaBusqueda.format(dateFormatter);
        // solicita ventas a sistema
        String[][] ventas = sistema.listVentas();
        // ve si el sistema esta vacio
        if (ventas == null) {
            System.out.println("Error: No existen ventas registradas en el sistema.");
            return;
        }

        System.out.println("");
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("| ID DOCUMENTO | TIPO DOCU | FECHA | RUT/PASAPORTE | CLIENTE | CANT BOLETOS | TOTAL VENTA |");
        System.out.println("----------------------------------------------------------------------------------------------");

        boolean ventasEnFecha = false;

        for (int i = 0; i < ventas.length; i++) {
            // mira si la fecha ingresada coincide con la columna
            if (ventas[i][2].equals(fechaFiltro)) {
                ventasEnFecha = true;
                System.out.print("| ");
                for (int j = 0; j < ventas[i].length; j++) {
                    System.out.print(ventas[i][j]);//imprime dato
                    if (j < ventas[i].length - 1) {
                        System.out.print(" | ");//imprime sparador si no es la ultima columna
                    }
                }
                System.out.println(" |");
                System.out.println("-----------------------------------------------------------------------------------------------");
            }
        }
        // si no hay coincidencia en la fecha
        if (!ventasEnFecha) {
            System.out.println("No se encontraron ventas para la fecha indicada.");
        }
    }//listVentas

    private void listViajes() {
        System.out.println(" ::: Listado de viajes :::");
        // solicita viajes a sistema
        String[][] viajes = sistema.listViajes();
        // ve si el sistema esta vacio
        if (viajes == null) {
            System.out.println("Error: No existen viajes registrados en el sistema.");
            return;
        }
        System.out.println("");
        System.out.println("---------------------------------------------------------");
        System.out.println("| FECHA | HORA | PRECIO | DISPONIBLES | PATENTE |");
        System.out.println("---------------------------------------------------------");

        for (int i = 0; i < viajes.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < viajes[i].length; j++) {
                System.out.print(viajes[i][j]);//imprime dato
                if (j < viajes[i].length - 1) {
                    System.out.print(" | ");//imprime separador si no es la ultima columna
                }
            }
            System.out.println(" |");
            System.out.println("---------------------------------------------------------");
        }
    }//listViajes

}
