package vista;

import controlador.SistemaVentaPasajes;
import excepciones.SVPException;
import modelo.TipoDocumento;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.IntStream;

public class UISVP {

    private final SistemaVentaPasajes sistema;
    private final Scanner teclado;
    private final DateTimeFormatter formateadorFecha;
    private final DateTimeFormatter formateadorHora;

    public UISVP(SistemaVentaPasajes sistema) {
        this.sistema = sistema;
        this.teclado = new Scanner(System.in);
        this.formateadorFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.formateadorHora = DateTimeFormatter.ofPattern("HH:mm");
    }

    public void menu() {
        int opcion = 0;
        do {
            System.out.println("\n...::: Menu principal :::...");
            System.out.println("1) Crear cliente");
            System.out.println("2) Crear pasajero");
            System.out.println("3) Crear viaje");
            System.out.println("4) Iniciar venta pasajes");
            System.out.println("5) Vender pasaje individual");
            System.out.println("6) Pagar venta (Efectivo/Debito)");
            System.out.println("7) Pagar venta (Tarjeta de Credito)");
            System.out.println("8) Buscar horarios disponibles");
            System.out.println("9) Listar todas las ventas");
            System.out.println("10) Listar todos los viajes");
            System.out.println("11) Listar pasajeros de un viaje");
            System.out.println("12) Listar asientos de un viaje");
            System.out.println("13) Generar pasajes electronicos (Texto)");
            System.out.println("14) Leer datos iniciales");
            System.out.println("15) Guardar datos del sistema (Binario)");
            System.out.println("16) Leer datos del sistema (Binario)");
            System.out.println("17) Salir");
            System.out.print("..:: Ingrese numero de opcion: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
                ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un numero valido dentro del rango.");
            }
        } while (opcion != 17);
    }

    private void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1: crearCliente(); break;
                case 2: crearPasajero(); break;
                case 3: crearViaje(); break;
                case 4: iniciarVenta(); break;
                case 5: venderPasajeIndividual(); break;
                case 6: pagarVentaEfectivo(); break;
                case 7: pagarVentaTarjeta(); break;
                case 8: buscarHorarios(); break;
                case 9: mostrarMatrizGenerica(sistema.listVentas()); break;
                case 10: mostrarMatrizGenerica(sistema.listViajes()); break;
                case 11: listarPasajerosViaje(); break;
                case 12: listarAsientosViaje(); break;
                case 13: generarPasajesVenta(); break;
                case 14: cargarDatosIniciales(); break;
                case 15: guardarDatosSistema(); break;
                case 16: recuperarDatosSistema(); break;
                case 17: System.out.println("Saliendo de la aplicacion... ¡Adios!"); break;
                default: System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } catch (SVPException e) {
            System.out.println("Error controlado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos: " + e.getMessage());
        }
    }

    private void crearCliente() {
        System.out.print("Ingrese RUN/ID Cliente: ");
        String run = teclado.nextLine();
        System.out.print("Ingrese Nombre completo: ");
        String nom = teclado.nextLine();
        System.out.print("Ingrese Telefono: ");
        String fono = teclado.nextLine();
        System.out.print("Ingrese Email: ");
        String email = teclado.nextLine();


        System.out.println("Operacion pausada temporalmente por abstraccion de IdPersona.");
    }

    private void crearPasajero() {
        System.out.print("Ingrese RUN/ID Pasajero: ");
        String run = teclado.nextLine();
        System.out.print("Ingrese Nombre Pasajero: ");
        String nom = teclado.nextLine();
        System.out.print("Ingrese Telefono: ");
        String fono = teclado.nextLine();
        System.out.print("Ingrese Nombre Contacto Emergencia: ");
        String nomC = teclado.nextLine();
        System.out.print("Ingrese Telefono Contacto: ");
        String fonoC = teclado.nextLine();

        System.out.println("Operacion pausada temporalmente por abstraccion de IdPersona.");
    }

    private void crearViaje() {
        System.out.print("Ingrese fecha (dd-MM-aaaa): ");
        LocalDate fecha = LocalDate.parse(teclado.nextLine(), formateadorFecha);
        System.out.print("Ingrese hora (HH:mm): ");
        LocalTime hora = LocalTime.parse(teclado.nextLine(), formateadorHora);
        System.out.print("Ingrese precio pasaje: ");
        int precio = Integer.parseInt(teclado.nextLine());
        System.out.print("Ingrese duracion (minutos): ");
        int duracion = Integer.parseInt(teclado.nextLine());
        System.out.print("Ingrese patente del bus: ");
        String patente = teclado.nextLine();
        System.out.print("Ingrese comuna de salida: ");
        String salida = teclado.nextLine();
        System.out.print("Ingrese comuna de llegada: ");
        String llegada = teclado.nextLine();

        System.out.println("Operacion pausada temporalmente por abstraccion de IdPersona.");
    }

    private void iniciarVenta() {
        System.out.print("Ingrese ID del Documento: ");
        String idDoc = teclado.nextLine();
        System.out.print("Ingrese Tipo Documento (BOLETA/FACTURA): ");
        TipoDocumento tipo = TipoDocumento.valueOf(teclado.nextLine().toUpperCase());
        System.out.print("Ingrese fecha del viaje (dd-MM-aaaa): ");
        LocalDate fecha = LocalDate.parse(teclado.nextLine(), formateadorFecha);
        System.out.print("Ingrese comuna de origen: ");
        String origen = teclado.nextLine();
        System.out.print("Ingrese comuna de destino: ");
        String destino = teclado.nextLine();
        System.out.print("Ingrese cantidad de pasajes a reservar: ");
        int cant = Integer.parseInt(teclado.nextLine());

        System.out.println("Operacion pausada temporalmente por abstraccion de IdPersona.");
    }

    private void venderPasajeIndividual() {
        System.out.print("ID Documento Venta: ");
        String idDoc = teclado.nextLine();
        System.out.print("Tipo Documento (BOLETA/FACTURA): ");
        TipoDocumento tipo = TipoDocumento.valueOf(teclado.nextLine().toUpperCase());
        System.out.print("Fecha Viaje (dd-MM-aaaa): ");
        LocalDate fecha = LocalDate.parse(teclado.nextLine(), formateadorFecha);
        System.out.print("Hora Viaje (HH:mm): ");
        LocalTime hora = LocalTime.parse(teclado.nextLine(), formateadorHora);
        System.out.print("Patente del Bus: ");
        String patente = teclado.nextLine();
        System.out.print("Numero de Asiento: ");
        int asiento = Integer.parseInt(teclado.nextLine());

        System.out.println("Operacion pausada temporalmente por abstraccion de IdPersona.");
    }

    private void pagarVentaEfectivo() {
        System.out.print("ID Documento Venta: ");
        String idDoc = teclado.nextLine();
        System.out.print("Tipo Documento (BOLETA/FACTURA): ");
        TipoDocumento tipo = TipoDocumento.valueOf(teclado.nextLine().toUpperCase());
        sistema.pagaVenta(idDoc, tipo);
        System.out.println("Venta pagada exitosamente mediante Efectivo/Debito.");
    }

    private void pagarVentaTarjeta() {
        System.out.print("ID Documento Venta: ");
        String idDoc = teclado.nextLine();
        System.out.print("Tipo Documento (BOLETA/FACTURA): ");
        TipoDocumento tipo = TipoDocumento.valueOf(teclado.nextLine().toUpperCase());
        System.out.print("Ingrese Numero de Tarjeta de Credito: ");
        long tarjeta = Long.parseLong(teclado.nextLine());
        sistema.pagaVenta(idDoc, tipo, tarjeta);
        System.out.println("Transaccion de tarjeta de credito aprobada.");
    }

    private void buscarHorarios() {
        System.out.print("Fecha del Viaje (dd-MM-aaaa): ");
        LocalDate fecha = LocalDate.parse(teclado.nextLine(), formateadorFecha);
        System.out.print("Comuna Origen: ");
        String origen = teclado.nextLine();
        System.out.print("Comuna Destino: ");
        String destino = teclado.nextLine();
        System.out.print("Cantidad de Pasajes: ");
        int cant = Integer.parseInt(teclado.nextLine());

        String[][] horarios = sistema.getHorariosDisponibles(fecha, origen, destino, cant);
        mostrarHorariosDisponibles(horarios);
    }

    private void listarPasajerosViaje() {
        System.out.print("Fecha Viaje (dd-MM-aaaa): ");
        LocalDate fecha = LocalDate.parse(teclado.nextLine(), formateadorFecha);
        System.out.print("Hora Viaje (HH:mm): ");
        LocalTime hora = LocalTime.parse(teclado.nextLine(), formateadorHora);
        System.out.print("Patente del Bus: ");
        String patente = teclado.nextLine();

        mostrarMatrizGenerica(sistema.listPasajerosViaje(fecha, hora, patente));
    }

    private void listarAsientosViaje() {
        System.out.print("Fecha Viaje (dd-MM-aaaa): ");
        LocalDate fecha = LocalDate.parse(teclado.nextLine(), formateadorFecha);
        System.out.print("Hora Viaje (HH:mm): ");
        LocalTime hora = LocalTime.parse(teclado.nextLine(), formateadorHora);
        System.out.print("Patente del Bus: ");
        String patente = teclado.nextLine();

        String[] asientos = sistema.listAsientosDeViaje(fecha, hora, patente);
        System.out.println("--- Mapa de Ocupacion de Asientos ---");
        Arrays.stream(asientos).forEach(asiento -> System.out.print("[" + asiento + "] "));
        System.out.println();
    }

    private void generarPasajesVenta() {
        System.out.print("ID del documento de venta: ");
        String idDoc = teclado.nextLine();
        System.out.print("Tipo de documento (BOLETA/FACTURA): ");
        TipoDocumento tipo = TipoDocumento.valueOf(teclado.nextLine().toUpperCase());

        sistema.generatePasajesVenta(idDoc, tipo);
        System.out.println("Archivo de texto plano generado.");
    }

    private void cargarDatosIniciales() {
        sistema.readDatosIniciales();
        System.out.println("Datos iniciales de control cargados.");
    }

    private void guardarDatosSistema() {
        sistema.saveDatosSistema();
        System.out.println("Estado binario guardado con exito.");
    }

    private void recuperarDatosSistema() {
        sistema.readDatosSistema();
        System.out.println("Controladores restaurados con exito.");
    }


    public void mostrarHorariosDisponibles(String[][] horarios) {
        if (horarios == null || horarios.length == 0) {
            System.out.println("No existen itinerarios disponibles.");
            return;
        }

        System.out.println("| OP | PATENTE BUS |  HORA  |  PRECIO  | DISPONIBLES |");
        System.out.println("|----+-------------+--------+----------+-------------|");

        IntStream.range(0, horarios.length).forEach(i -> {
            System.out.printf("| %2d | %-11s | %-6s | $%-7s | %11s |\n",
                    (i + 1), horarios[i][0], horarios[i][1], horarios[i][2], horarios[i][3]);
            System.out.println("|----+-------------+--------+----------+-------------|");
        });
    }

    private void mostrarMatrizGenerica(String[][] matriz) {
        if (matriz == null || matriz.length == 0) {
            System.out.println("No hay registros disponibles.");
            return;
        }

        Arrays.stream(matriz).forEach(fila -> {
            Arrays.stream(fila).forEach(celda -> System.out.print("[" + celda + "] "));
            System.out.println();
        });
    }
}
