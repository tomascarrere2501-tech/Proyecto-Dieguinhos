package vista.consultas;

import controlador.ControladorEmpresas;
import excepciones.SVPException;
import utilidades.Rut;

import javax.swing.*;
import java.awt.*;

public class ConsultaVentasEmpresa extends JFrame {

    private final JTextField txtRut;
    private final JButton btnBuscar;
    private final JButton btnCerrar;
    private final JTable tablaVentas;

    private final ControladorEmpresas controlador;

    public ConsultaVentasEmpresa() {

        controlador = ControladorEmpresas.getInstance();

        setTitle("Consulta de Ventas por Empresa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));



        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelSuperior.add(new JLabel("RUT Empresa:"));

        txtRut = new JTextField(15);
        panelSuperior.add(txtRut);

        btnBuscar = new JButton("Buscar");
        panelSuperior.add(btnBuscar);

        add(panelSuperior, BorderLayout.NORTH);



        tablaVentas = new JTable();

        JScrollPane scroll = new JScrollPane(tablaVentas);

        add(scroll, BorderLayout.CENTER);



        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnCerrar = new JButton("Cerrar");

        panelInferior.add(btnCerrar);

        add(panelInferior, BorderLayout.SOUTH);


        btnBuscar.addActionListener(e -> buscarVentas());

        btnCerrar.addActionListener(e -> dispose());

        setSize(800,500);
        setLocationRelativeTo(null);
    }


    private void buscarVentas() {

        try {

            String rutTexto = txtRut.getText().trim();

            if(rutTexto.isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar un RUT.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;

            }

            Rut rut = Rut.of(rutTexto);

            String[][] datos = controlador.listVentasEmpresa(rut);

            String[] columnas = {
                    "Fecha",
                    "Tipo Documento",
                    "Monto",
                    "Estado Pago"
            };

            UtilTabla.cargarTabla(tablaVentas,columnas,datos);

            if(datos.length==0){

                JOptionPane.showMessageDialog(
                        this,
                        "La empresa no registra ventas.",
                        "Consulta",
                        JOptionPane.INFORMATION_MESSAGE
                );

            }

        }catch(IllegalArgumentException ex){

            JOptionPane.showMessageDialog(
                    this,
                    "Formato de RUT inválido.\nEjemplo: 12.345.678-9",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

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