package vista;

import controlador.SistemaVentaPasajes;
import modelo.TipoDocumento;

import javax.swing.*;
import java.awt.*;

public class VentanaVentaPasajes extends JFrame {

    private SistemaVentaPasajes sistema;

    private JTextField txtRutCliente;
    private JComboBox<TipoDocumento> cbTipoDocumento;
    private JTextField txtFchaViaje;
    private JTextField txtOrigen;
    private JTextField txtDestino;
    private JComboBox<Object> cbViajesDisponibles;
    private JButton btnBuscarViajes;
    private JButton btnIniciarVentas;

    private JTextField txtRutPasajero;
    private JTextField txtNombrePasajero;
    private JButton btnAgregarPasaje;

    private JButton btnPagar;
    private JButton btnGenerarArchivo;

    public VentanaVentaPasajes() {
        this.sistema = sistema;

        setTitle("Ventana de Pasajes  modulo de la venta ");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        inicializarComponentes();
        estadosInicial();
        configurarEventos();
    }

    private void inicializarComponentes() {
        JPanel panelInicio = new JPanel(new GridLayout(7, 2, 10, 10));
        panelInicio.setBorder(BorderFactory.createTitledBorder("1. Inicia la venta "));

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

        btnBuscarViajes = new JButton("Buscar Viajes");
        cbViajesDisponibles = new JComboBox<>();
        panelInicio.add(btnBuscarViajes);
        panelInicio.add(cbViajesDisponibles);

        btnIniciarVentas = new JButton("Iniciar Venta");
        panelInicio.add(new JLabel(""));
        panelInicio.add(btnIniciarVentas);

        JPanel panelPasajeros = new JPanel(new GridLayout(3, 2, 5, 5));
        panelPasajeros.setBorder(BorderFactory.createTitledBorder("2. Agregar  los Pasajeros"));

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
        panelPago.setBorder(BorderFactory.createTitledBorder("3. Finalizar "));

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

        txtRutPasajero.setEnabled(false);
        txtNombrePasajero.setEnabled(false);
    }

    private void configurarEventos() {

        btnBuscarViajes.addActionListener(e -> {
            try {
                if (txtFchaViaje.getText().isEmpty() || txtOrigen.getText().isEmpty() || txtDestino.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar fecha, origen y destino.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                btnIniciarVentas.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al buscar viaje", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnIniciarVentas.addActionListener(e -> {
            try {
                if (txtRutCliente.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese RUT del cliente.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                TipoDocumento tipo = (TipoDocumento) cbTipoDocumento.getSelectedItem();



                JOptionPane.showMessageDialog(this, "Venta iniciada con éxito. Proceda a agregar pasajeros.");

                btnIniciarVentas.setEnabled(false);
                btnBuscarViajes.setEnabled(false);
                btnAgregarPasaje.setEnabled(true);
                btnPagar.setEnabled(true);

                txtRutPasajero.setEnabled(true);
                txtNombrePasajero.setEnabled(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al Iniciar", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAgregarPasaje.addActionListener(e -> {
            try {
                if (txtRutPasajero.getText().isEmpty() || txtNombrePasajero.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete los datos del pasajero.");
                    return;
                }



                JOptionPane.showMessageDialog(this, "Pasaje agregado a la venta actual.");
                txtRutPasajero.setText("");
                txtNombrePasajero.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al vender pasaje", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPagar.addActionListener(e -> {
            try {


                JOptionPane.showMessageDialog(this, "Venta pagada con éxito.");

                btnAgregarPasaje.setEnabled(false);
                btnPagar.setEnabled(false);
                btnGenerarArchivo.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en el pago", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnGenerarArchivo.addActionListener(e -> {
            try {


                JOptionPane.showMessageDialog(this, "Archivos de pasajes generados correctamente.");

                txtRutCliente.setText("");
                txtFchaViaje.setText("");
                txtOrigen.setText("");
                txtDestino.setText("");
                cbViajesDisponibles.removeAllItems();
                estadosInicial();
                btnBuscarViajes.setEnabled(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al generar archivos", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}