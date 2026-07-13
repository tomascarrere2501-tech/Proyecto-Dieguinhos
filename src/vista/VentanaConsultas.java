package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaConsultas extends JFrame {

    public VentanaConsultas() {

        setTitle("Consultas e Informes");
        setSize(400,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(4,1,10,10));

        JButton btnEmpresas = new JButton("Consulta Empresas");
        JButton btnVentas = new JButton("Consulta Ventas Empresa");
        JButton btnLlegadas = new JButton("Consulta Llegadas y Salidas");
        JButton btnCerrar = new JButton("Cerrar");

        add(btnEmpresas);
        add(btnVentas);
        add(btnLlegadas);
        add(btnCerrar);

        btnEmpresas.addActionListener(e ->
                new ConsultaEmpresas().setVisible(true));

        btnVentas.addActionListener(e ->
                new ConsultaVentasEmpresa().setVisible(true));

        btnLlegadas.addActionListener(e ->
                new ConsultaLlegadasSalidas().setVisible(true));

        btnCerrar.addActionListener(e -> dispose());

    }

}
