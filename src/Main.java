package vista;

import controlador.SistemaVentaPasajes;

public class Main {
    public static void main(String[] args) {
        SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();
        UISVP interfaz = new UISVP(sistema);
        interfaz.menu();
    }
}