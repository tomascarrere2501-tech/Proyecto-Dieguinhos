package vista.consultas;

import controlador.ControladorEmpresas;
import excepciones.SVPException;

import javax.swing.*;
import java.awt.*;

public class ConsultaEmpresas extends JFrame {

    private final JTable tablaEmpresas = new JTable();
    private final JButton btnActualizar = new JButton("Actualizar");
    private final JButton btnCerrar = new JButton("Cerrar");

    private final ControladorEmpresas controlador = ControladorEmpresas.getInstance();

    public ConsultaEmpresas() {
        setTitle("Consulta de Empresas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel superior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        superior.add(btnActualizar);
        add(superior, BorderLayout.NORTH);

        add(new JScrollPane(tablaEmpresas), BorderLayout.CENTER);

        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        inferior.add(btnCerrar);
        add(inferior, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargarEmpresas());
        btnCerrar.addActionListener(e -> dispose());

        cargarEmpresas();

        setSize(900,500);
        setLocationRelativeTo(null);
    }

    private void cargarEmpresas() {
        try {
            String[][] datos = controlador.listEmpresas();

            String[] columnas = {
                    "RUT",
                    "Empresa",
                    "Página Web",
                    "Tripulantes",
                    "Buses",
                    "Ventas"
            };

            UtilTabla.cargarTabla(tablaEmpresas, columnas, datos);

            if (datos.length == 0) {
                JOptionPane.showMessageDialog(this,
                        "No existen empresas registradas.",
                        "Consulta",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

