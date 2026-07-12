package vista;

import controlador.ControladorEmpresas;
import controlador.SistemaVentaPasajes;
import excepciones.SVPException;
import utilidades.IdPersona;
import utilidades.Rut;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class VentanaCrearViaje extends JFrame {

    private SistemaVentaPasajes controlador;

    private JTextField txtFecha;
    private JTextField txtHora;
    private JTextField txtTarifa;
    private JTextField txtDuracion;
    private JTextField txtPatenteBus;
    private JTextField txtRutConductor;
    private JTextField txtRutAuxiliar;
    private JTextField txtOrigen;
    private JTextField txtDestino;

    public VentanaCrearViaje() {
        controlador = SistemaVentaPasajes.getInstance();

        setTitle("Crear Nuevo Viaje - Gestion de Pasajes");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Registro de Nuevo Viaje", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(9, 2, 10, 15));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        panelFormulario.add(new JLabel("Fecha (dd-MM-yyyy):"));
        txtFecha = new JTextField();
        panelFormulario.add(txtFecha);

        panelFormulario.add(new JLabel("Hora (HH:MM):"));
        txtHora = new JTextField();
        panelFormulario.add(txtHora);

        panelFormulario.add(new JLabel("Tarifa ($):"));
        txtTarifa = new JTextField();
        panelFormulario.add(txtTarifa);

        panelFormulario.add(new JLabel("Duracion (minutos):"));
        txtDuracion = new JTextField();
        panelFormulario.add(txtDuracion);

        panelFormulario.add(new JLabel("Patente del Bus:"));
        txtPatenteBus = new JTextField();
        panelFormulario.add(txtPatenteBus);

        panelFormulario.add(new JLabel("RUT Conductor:"));
        txtRutConductor = new JTextField();
        panelFormulario.add(txtRutConductor);

        panelFormulario.add(new JLabel("RUT Auxiliar:"));
        txtRutAuxiliar = new JTextField();
        panelFormulario.add(txtRutAuxiliar);

        panelFormulario.add(new JLabel("Terminal Origen:"));
        txtOrigen = new JTextField();
        panelFormulario.add(txtOrigen);

        panelFormulario.add(new JLabel("Terminal Destino:"));
        txtDestino = new JTextField();
        panelFormulario.add(txtDestino);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnGuardar = new JButton("Guardar Viaje");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarViaje());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void guardarViaje() {
        try {
            if (txtFecha.getText().trim().isEmpty() || txtHora.getText().trim().isEmpty() ||
                    txtPatenteBus.getText().trim().isEmpty() || txtOrigen.getText().trim().isEmpty() ||
                    txtDestino.getText().trim().isEmpty() || txtTarifa.getText().trim().isEmpty() ||
                    txtDuracion.getText().trim().isEmpty() || 
                    txtRutConductor.getText().trim().isEmpty() ||
                    txtRutAuxiliar.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos Vacios", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DateTimeFormatter formatFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), formatFecha);
            LocalTime hora = LocalTime.parse(txtHora.getText().trim(), formatHora);

            int precio = Integer.parseInt(txtTarifa.getText().trim());
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            String patBus = txtPatenteBus.getText().trim();

            IdPersona idAux = Rut.of(txtRutAuxiliar.getText().trim());
            IdPersona idCond = Rut.of(txtRutConductor.getText().trim());
            IdPersona[] conductores = new IdPersona[]{idCond};

            String comSalida = txtOrigen.getText().trim();
            String comLlegada = txtDestino.getText().trim();
            controlador.createViaje(fecha, hora, precio, duracion, patBus, idAux, conductores, comSalida, comLlegada);

            JOptionPane.showMessageDialog(this, "Viaje creado exitosamente en el sistema!", "Exito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha u hora incorrecto.\nUse dd-MM-yyyy para fecha y HH:mm para hora.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La tarifa y la duracion deben ser numeros enteros validos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Formato de RUT invalido.\nEjemplo: 12.345.678-9", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, "Error de Logica: " + ex.getMessage(), "Error en el Sistema", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear el viaje: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
