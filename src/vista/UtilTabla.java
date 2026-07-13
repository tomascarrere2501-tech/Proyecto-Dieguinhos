package vista;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class UtilTabla {

    public static void cargarTabla(JTable tabla, String[] columnas, String[][] datos) {

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        if (datos != null) {
            for (String[] fila : datos) {
                modelo.addRow(fila);
            }
        }

        tabla.setModel(modelo);

        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    
    public static void limpiarTabla(JTable tabla) {

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

    }

}
