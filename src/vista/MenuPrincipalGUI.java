package vista;

import controlador.ControladorEmpresas;
import controlador.SistemaVentaPasajes;
import excepciones.SVPException;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalGUI extends JFrame {

    private SistemaVentaPasajes sistema;
    private ControladorEmpresas ctrlEmpresas;

    public MenuPrincipalGUI() {
        this.sistema = SistemaVentaPasajes.getInstance();
        this.ctrlEmpresas = ControladorEmpresas.getInstance();

        setTitle("Menu Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("SISTEMA DE VENTA DE PASAJES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JButton btnCargarDatos = new JButton("Cargar Datos Iniciales");
        JButton btnGuardarDatos = new JButton("Guardar Datos del Sistema");
        JButton btnConsultas = new JButton("Consultas e Informes");
        JButton btnCrearViaje = new JButton("Crear Viaje");
        JButton btnVenderPasajes = new JButton("Vender Pasajes");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnCargarDatos);
        panelBotones.add(btnGuardarDatos);
        panelBotones.add(btnConsultas);
        panelBotones.add(btnCrearViaje);
        panelBotones.add(btnVenderPasajes);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        btnSalir.addActionListener(e -> System.exit(0));
        btnGuardarDatos.addActionListener(e -> guardarDatosSistema());
        btnCargarDatos.addActionListener(e -> cargarDatosIniciales());

        btnCrearViaje.addActionListener(e -> {
            VentanaCrearViaje ventana = new VentanaCrearViaje();
            ventana.setVisible(true);
        });

        btnVenderPasajes.addActionListener(e -> {
            VentanaVentaPasajes ventana = new VentanaVentaPasajes();
            ventana.setVisible(true);
        });

        btnConsultas.addActionListener(e -> {
            VentanaConsultas ventana = new VentanaConsultas();
            ventana.setVisible(true);
        });


    }

    private void guardarDatosSistema() {
        try {
            sistema.saveDatosSistema();
            JOptionPane.showMessageDialog(this,
                    "Datos guardados exitosamente en SVPObjetos.obj",
                    "Exito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar: " + ex.getMessage(),
                    "Error de Persistencia",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosIniciales() {
        try {
            sistema.readDatosIniciales();
            JOptionPane.showMessageDialog(this,
                    "Datos iniciales cargados exitosamente.",
                    "Exito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar: " + ex.getMessage(),
                    "Error de Persistencia",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}