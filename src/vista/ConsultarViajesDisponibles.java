package vista;

import controlador.SistemaVentaPasajes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsultarViajesDisponibles extends JFrame {

    private JTable tabla;
    private JButton btnCargar;
    private SistemaVentaPasajes sistema;

    public ConsultarViajesDisponibles() {
        this.sistema = SistemaVentaPasajes.getInstance();

        setTitle("Consulta - Listar Viajes Disponibles");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSup = new JPanel();
        btnCargar = new JButton("Cargar Todos los Viajes");
        panelSup.add(btnCargar);
        add(panelSup, BorderLayout.NORTH);

        tabla = new JTable();
        tabla.setEnabled(false);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnCargar.addActionListener(e -> cargarDatos());
        setLocationRelativeTo(null);
    }

    private void cargarDatos() {
        try {
            String[] columnas = {"Fecha", "Hora Salida", "Hora Termino", "Precio", "Asientos Disp.", "Patente", "Origen", "Destino"};
            DefaultTableModel modelo = new DefaultTableModel(null, columnas);

            String[][] viajes = sistema.listViajes();

            if (viajes == null || viajes.length == 0) {
                JOptionPane.showMessageDialog(this, "No hay viajes registrados. Recuerde cargar los datos en el menu principal.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                tabla.setModel(modelo);
                return;
            }

            for (String[] v : viajes) {
                try {
                    LocalDate fechaBD = LocalDate.parse(v[0]);
                    v[0] = fechaBD.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (Exception exDate) {
                    v[0] = v[0].replace("-", "/");
                }

                modelo.addRow(v);
            }

            tabla.setModel(modelo);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la tabla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}