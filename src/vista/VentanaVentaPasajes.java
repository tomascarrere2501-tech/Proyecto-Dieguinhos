package vista;

import controlador.SistemaVentaPasajes;
import modelo.TipoDocumento;
import utilidades.Rut;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VentanaVentaPasajes extends JFrame {

    private SistemaVentaPasajes sistema;

    private JTextField txtIdVenta;
    private JTextField txtCantPasajes;
    private JTextField txtRutCliente;
    private JComboBox<TipoDocumento> cbTipoDocumento;
    private JTextField txtFchaViaje;
    private JTextField txtOrigen;
    private JTextField txtDestino;
    private JComboBox<Object> cbViajesDisponibles;
    private JButton btnBuscarViajes;
    private JButton btnIniciarVentas;

    private JTextField txtAsiento;
    private JTextField txtRutPasajero;
    private JTextField txtNombrePasajero;
    private JButton btnAgregarPasaje;

    private JButton btnPagar;
    private JButton btnGenerarArchivo;

    public VentanaVentaPasajes() {
        this.sistema = SistemaVentaPasajes.getInstance();

        setTitle("Ventana de Pasajes - Modulo de Venta");
        setSize(650, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        inicializarComponentes();
        estadosInicial();
        configurarEventos();
    }

    private void inicializarComponentes() {
        JPanel panelInicio = new JPanel(new GridLayout(9, 2, 10, 10));
        panelInicio.setBorder(BorderFactory.createTitledBorder("1. Inicia la venta"));

        panelInicio.add(new JLabel("ID Venta:"));
        txtIdVenta = new JTextField();
        panelInicio.add(txtIdVenta);

        panelInicio.add(new JLabel("RUT Cliente:"));
        txtRutCliente = new JTextField();
        panelInicio.add(txtRutCliente);

        panelInicio.add(new JLabel("Tipo Documento:"));
        cbTipoDocumento = new JComboBox<>(TipoDocumento.values());
        panelInicio.add(cbTipoDocumento);

        panelInicio.add(new JLabel("Fecha (DD/MM/YYYY):"));
        txtFchaViaje = new JTextField();
        panelInicio.add(txtFchaViaje);

        panelInicio.add(new JLabel("Comuna de Origen:"));
        txtOrigen = new JTextField();
        panelInicio.add(txtOrigen);

        panelInicio.add(new JLabel("Comuna Destino:"));
        txtDestino = new JTextField();
        panelInicio.add(txtDestino);

        panelInicio.add(new JLabel("Cantidad Pasajes:"));
        txtCantPasajes = new JTextField();
        panelInicio.add(txtCantPasajes);

        btnBuscarViajes = new JButton("Buscar Viajes");
        cbViajesDisponibles = new JComboBox<>();
        panelInicio.add(btnBuscarViajes);
        panelInicio.add(cbViajesDisponibles);

        btnIniciarVentas = new JButton("Iniciar Venta");
        panelInicio.add(new JLabel(""));
        panelInicio.add(btnIniciarVentas);

        JPanel panelPasajeros = new JPanel(new GridLayout(4, 2, 5, 5));
        panelPasajeros.setBorder(BorderFactory.createTitledBorder("2. Agregar los Pasajeros"));

        panelPasajeros.add(new JLabel("Nro Asiento:"));
        txtAsiento = new JTextField();
        panelPasajeros.add(txtAsiento);

        panelPasajeros.add(new JLabel("RUT Pasajero:"));
        txtRutPasajero = new JTextField();
        panelPasajeros.add(txtRutPasajero);

        panelPasajeros.add(new JLabel("Nombre Pasajero:"));
        txtNombrePasajero = new JTextField();
        panelPasajeros.add(txtNombrePasajero);

        btnAgregarPasaje = new JButton("Vender Pasaje a este pasajero");
        panelPasajeros.add(new JLabel(""));
        panelPasajeros.add(btnAgregarPasaje);

        JPanel panelPago = new JPanel(new FlowLayout());
        panelPago.setBorder(BorderFactory.createTitledBorder("3. Finalizar"));

        btnPagar = new JButton("Pagar Venta");
        btnGenerarArchivo = new JButton("Generar Pasajes");

        panelPago.add(btnPagar);
        panelPago.add(btnGenerarArchivo);

        add(panelInicio);
        add(panelPasajeros);
        add(panelPago);
    }

    private void estadosInicial() {
        btnIniciarVentas.setEnabled(false);
        btnAgregarPasaje.setEnabled(false);
        btnPagar.setEnabled(false);
        btnGenerarArchivo.setEnabled(false);

        txtAsiento.setEnabled(false);
        txtRutPasajero.setEnabled(false);
        txtNombrePasajero.setEnabled(false);
    }

    private void configurarEventos() {

        btnBuscarViajes.addActionListener(e -> {
            try {
                if (txtFchaViaje.getText().isEmpty() || txtOrigen.getText().isEmpty() || txtDestino.getText().isEmpty() || txtCantPasajes.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar fecha, origen, destino y cantidad.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(txtFchaViaje.getText().trim(), fmt);
                int cantidad = Integer.parseInt(txtCantPasajes.getText().trim());

                String[][] viajes = sistema.getHorariosDisponibles(fecha, txtOrigen.getText().trim(), txtDestino.getText().trim(), cantidad);

                cbViajesDisponibles.removeAllItems();
                if (viajes.length == 0) {
                    JOptionPane.showMessageDialog(this, "No hay viajes disponibles.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                for (String[] v : viajes) {
                    cbViajesDisponibles.addItem(v[0] + " | " + v[1] + " | $" + v[2]);
                }

                btnIniciarVentas.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al buscar viaje", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnIniciarVentas.addActionListener(e -> {
            try {
                if (txtRutCliente.getText().isEmpty() || txtIdVenta.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese ID de Venta y RUT del cliente.", "Validacion", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(txtFchaViaje.getText().trim(), fmt);
                TipoDocumento tipo = (TipoDocumento) cbTipoDocumento.getSelectedItem();
                int cantidad = Integer.parseInt(txtCantPasajes.getText().trim());

                sistema.iniciaVenta(
                        txtIdVenta.getText().trim(),
                        tipo,
                        fecha,
                        txtOrigen.getText().trim(),
                        txtDestino.getText().trim(),
                        Rut.of(txtRutCliente.getText().trim()),
                        cantidad
                );

                JOptionPane.showMessageDialog(this, "Venta iniciada con exito. Proceda a agregar pasajeros.");

                btnIniciarVentas.setEnabled(false);
                btnBuscarViajes.setEnabled(false);
                btnAgregarPasaje.setEnabled(true);
                btnPagar.setEnabled(true);

                txtAsiento.setEnabled(true);
                txtRutPasajero.setEnabled(true);
                txtNombrePasajero.setEnabled(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al Iniciar", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAgregarPasaje.addActionListener(e -> {
            try {
                if (txtRutPasajero.getText().isEmpty() || txtAsiento.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete el asiento y RUT del pasajero.");
                    return;
                }

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(txtFchaViaje.getText().trim(), fmt);
                TipoDocumento tipo = (TipoDocumento) cbTipoDocumento.getSelectedItem();

                String[] partesViaje = cbViajesDisponibles.getSelectedItem().toString().split(" \\| ");
                String patente = partesViaje[0];
                LocalTime hora = LocalTime.parse(partesViaje[1]);
                int asiento = Integer.parseInt(txtAsiento.getText().trim());

                sistema.vendePasaje(
                        txtIdVenta.getText().trim(),
                        tipo,
                        fecha,
                        hora,
                        patente,
                        asiento,
                        Rut.of(txtRutPasajero.getText().trim())
                );

                JOptionPane.showMessageDialog(this, "Pasaje agregado a la venta actual.");
                txtRutPasajero.setText("");
                txtNombrePasajero.setText("");
                txtAsiento.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al vender pasaje", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPagar.addActionListener(e -> {
            try {
                TipoDocumento tipo = (TipoDocumento) cbTipoDocumento.getSelectedItem();
                sistema.pagaVenta(txtIdVenta.getText().trim(), tipo);

                JOptionPane.showMessageDialog(this, "Venta pagada con exito.");

                btnAgregarPasaje.setEnabled(false);
                btnPagar.setEnabled(false);
                btnGenerarArchivo.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en el pago", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnGenerarArchivo.addActionListener(e -> {
            try {
                TipoDocumento tipo = (TipoDocumento) cbTipoDocumento.getSelectedItem();
                sistema.generatePasajesVenta(txtIdVenta.getText().trim(), tipo);

                JOptionPane.showMessageDialog(this, "Archivos de pasajes generados correctamente.");

                txtIdVenta.setText("");
                txtRutCliente.setText("");
                txtFchaViaje.setText("");
                txtOrigen.setText("");
                txtDestino.setText("");
                txtCantPasajes.setText("");
                cbViajesDisponibles.removeAllItems();
                estadosInicial();
                btnBuscarViajes.setEnabled(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al generar archivos", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
