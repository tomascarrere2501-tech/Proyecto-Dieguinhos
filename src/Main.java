import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    Scanner sc;
    SistemaVentasPasajes sistema;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public Main() {
        this.sc = new Scanner(System.in);
        this.sistema = new SistemaVentasPasajes();
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
        switch (RoP) {
            case 1:
                System.out.println("R.U.T: ");
                String rut= sc.next();
                break;
            case 2:
                System.out.println("Pasaporte: ");
                String pas= sc.next();
        }
        System.out.println("Sr[1] o Sra[2]: ");
        int SoSra = sc.nextInt();
        System.out.println("Nombre: ");
        String nombre= sc.nextLine();
        sc.nextLine();
        System.out.println("Apellido paterno: ");
        String aP= sc.next();
        System.out.println("Apellido materno: ");
        String aM= sc.next();
        System.out.println("Telefono: ");
        String telefono= sc.next();
        System.out.println("email: ");
        String email= sc.next();
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
        System.out.println("::::Viaje guardado exitosamente::::::");
    }//createViaje

    private void ventaPasaje() {
        System.out.println("  ::Creando Viaje ::  ");
        System.out.println(" ");
        System.out.println(":::::Dato de la venta");
        System.out.println("ID Documento: ");
        String idDocumento= sc.next();
        System.out.println("Tipo de documento: [1]Boleta [2]Factura: ");//aqui
        int tipoDocNum = sc.nextInt();
        switch (tipoDocNum) {
            case 1: TipoDocumento.BOLETA;
            break;
            case 2: TipoDocumento.FACTURA;
            break;
        }

        System.out.println("Fecha de la venta [dd/mm/yyyy]: ");
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
        }
        boolean ventaIniciada = sistema.iniciaVenta(idDoc, tipoDoc, fechaVenta, idCliente);

        if (!ventaIniciada) {
            System.out.println("La venta no se puede concretar, Verifique ID de venta o cliente");
        }
        System.out.println("Nombre Cliente: " + sistema.getNombrePasajero(idCliente));
    }//ventaViaje


}