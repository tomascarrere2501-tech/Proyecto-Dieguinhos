package vista;

import controlador.ControladorEmpresas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ConsultaEmpresas extends JFrame {

    private JTable tabla;
    private JButton btnCargar;
    private ControladorEmpresas controlador;

    public ConsultaEmpresas() {
        controlador = ControladorEmpresas.getInstance();

        setTitle("Consulta - Listar Empresas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSup = new JPanel();
        btnCargar = new JButton("Cargar Empresas");
        panelSup.add(btnCargar);
        add(panelSup, BorderLayout.NORTH);

        tabla = new JTable();
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnCargar.addActionListener(e -> cargarDatos());
        setLocationRelativeTo(null);
    }

    private void cargarDatos() {
        try {
            String[] columnas = {"RUT", "Nombre Empresa", "Sitio Web"};
            DefaultTableModel modelo = new DefaultTableModel(null, columnas);

            java.util.List<modelo.Empresa> listaEmpresas = controlador.getEmpresas();

            if(listaEmpresas == null || listaEmpresas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay empresas registradas. Asegurese de cargar los datos en el menu principal.");
                tabla.setModel(modelo);
                return;
            }

            for(modelo.Empresa emp : listaEmpresas) {
                modelo.addRow(new Object[]{
                        emp.getRut(),
                        emp.getNombre(),
                        emp.getUrl()
                });
            }

            tabla.setModel(modelo);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la tabla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}