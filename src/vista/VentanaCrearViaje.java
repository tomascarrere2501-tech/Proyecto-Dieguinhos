package vista;

import controlador.SistemaVentaPasajes;
import controlador.SistemaVentaPasajes;
import excepciones.SVPException;
import utilidades.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class VentanaCrearViaje extends JFrame {

    private SistemaVentaPasajes sistema;

    private JTextField txtFecha;
    private JTextField txtHora;
    private JTextField txtPrecio;
    private JTextField txtDuracion;
    private JTextField txtPatenteBus;
    private JTextField txtIdAuxiliar;
    private JTextField txtIdConductor;
    private JTextField txtTerminalSalida;
    private JTextField txtTerminalLlegada;

    public VentanaCrearViaje() {
        sistema = SistemaVentaPasajes.getInstance();

        setTitle("Crear Nuevo Viaje - Gestión de Pasajes");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Registro de Nuevo Viaje", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(9, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        panelFormulario.add(new JLabel("Fecha (AAAA-MM-DD):"));
        txtFecha = new JTextField();
        panelFormulario.add(txtFecha);

        panelFormulario.add(new JLabel("Hora (HH:MM):"));
        txtHora = new JTextField();
        panelFormulario.add(txtHora);

        panelFormulario.add(new JLabel("Precio ($):"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Duración (minutos):"));
        txtDuracion = new JTextField();
        panelFormulario.add(txtDuracion);

        panelFormulario.add(new JLabel("Patente del Bus:"));
        txtPatenteBus = new JTextField();
        panelFormulario.add(txtPatenteBus);

        panelFormulario.add(new JLabel("ID Auxiliar (RUT):"));
        txtIdAuxiliar = new JTextField();
        panelFormulario.add(txtIdAuxiliar);

        panelFormulario.add(new JLabel("ID Conductor (RUTs sep. por coma):"));
        txtIdConductor = new JTextField();
        panelFormulario.add(txtIdConductor);

        panelFormulario.add(new JLabel("Comuna Terminal Salida:"));
        txtTerminalSalida = new JTextField();
        panelFormulario.add(txtTerminalSalida);

        panelFormulario.add(new JLabel("Comuna Terminal Llegada:"));
        txtTerminalLlegada = new JTextField();
        panelFormulario.add(txtTerminalLlegada);

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
                    txtPrecio.getText().trim().isEmpty() || txtDuracion.getText().trim().isEmpty() ||
                    txtPatenteBus.getText().trim().isEmpty() || txtIdAuxiliar.getText().trim().isEmpty() ||
                    txtIdConductor.getText().trim().isEmpty() || txtTerminalSalida.getText().trim().isEmpty() ||
                    txtTerminalLlegada.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());
            int precio = Integer.parseInt(txtPrecio.getText().trim());
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            String patente = txtPatenteBus.getText().trim();
            String salida = txtTerminalSalida.getText().trim();
            String llegada = txtTerminalLlegada.getText().trim();

            IdPersona idAux = Rut.of(txtIdAuxiliar.getText().trim());
            if (idAux == null) {
                JOptionPane.showMessageDialog(this, "El RUT del auxiliar no tiene un formato válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] rutsConductores = txtIdConductor.getText().trim().split(",");
            IdPersona[] idsConds = new IdPersona[rutsConductores.length];
            for (int i = 0; i < rutsConductores.length; i++) {
                idsConds[i] = Rut.of(rutsConductores[i].trim());
                if (idsConds[i] == null) {
                    JOptionPane.showMessageDialog(this, "El RUT de uno de los conductores no es válido: " + rutsConductores[i], "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            sistema.createViaje(fecha, hora, precio, duracion, patente, idAux, idsConds, salida, llegada);

            JOptionPane.showMessageDialog(this, "¡Viaje creado exitosamente en el sistema!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha u hora incorrecto.\nUse AAAA-MM-DD para fecha y HH:MM para hora.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio y la duración deben ser números enteros válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en el Sistema", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}