package vista.consultas;

import controlador.ControladorEmpresas;
import excepciones.SVPException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ConsultaLlegadasSalidas extends JFrame {

    private JTextField txtTerminal;
    private JTextField txtFecha;

    private JButton btnBuscar;
    private JButton btnCerrar;

    private JTable tabla;

    private final ControladorEmpresas controlador;

    public ConsultaLlegadasSalidas() {

        controlador = ControladorEmpresas.getInstance();

        inicializarComponentes();
        registrarEventos();

    }

    private void inicializarComponentes() {

        setTitle("Consulta Llegadas y Salidas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));


        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelSuperior.add(new JLabel("Terminal:"));

        txtTerminal = new JTextField(15);
        panelSuperior.add(txtTerminal);

        panelSuperior.add(new JLabel("Fecha (AAAA-MM-DD):"));

        txtFecha = new JTextField(10);
        panelSuperior.add(txtFecha);

        btnBuscar = new JButton("Buscar");

        panelSuperior.add(btnBuscar);

        add(panelSuperior, BorderLayout.NORTH);


        tabla = new JTable();

        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);


        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnCerrar = new JButton("Cerrar");

        panelInferior.add(btnCerrar);

        add(panelInferior, BorderLayout.SOUTH);

        setSize(900,500);
        setLocationRelativeTo(null);

    }

    private void registrarEventos() {

        btnBuscar.addActionListener(e -> consultar());

        btnCerrar.addActionListener(e -> dispose());

    }

    private void consultar() {

        try {

            String nombreTerminal = txtTerminal.getText().trim();

            if(nombreTerminal.isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar un terminal.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;

            }

            LocalDate fecha;

            try{

                fecha = LocalDate.parse(txtFecha.getText().trim());

            }catch(DateTimeParseException ex){

                JOptionPane.showMessageDialog(
                        this,
                        "La fecha debe tener formato AAAA-MM-DD.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;

            }

            String[][] datos =
                    controlador.listLlegadasSalidasTerminal(nombreTerminal, fecha);

            String[] columnas = {
                    "Tipo",
                    "Hora",
                    "Patente",
                    "Empresa",
                    "Pasajeros"
            };

            UtilTabla.cargarTabla(tabla,columnas,datos);

            if(datos.length==0){

                JOptionPane.showMessageDialog(
                        this,
                        "No existen llegadas ni salidas para esa fecha.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );

            }

        }catch(SVPException ex){

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

}