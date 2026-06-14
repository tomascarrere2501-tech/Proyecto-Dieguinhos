package modelo;
import java.io.Serializable;
import java.util.ArrayList;

public class Bus implements Serializable {
    private static final long serialVersionUID = 1L;

    //Atributos
    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private Empresa empresa;
    private ArrayList<Viaje> listaViajes;

    public Bus(String patente, String marca, String modelo, int nroAsientos, Empresa empresa) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.nroAsientos = nroAsientos;
        this.empresa = empresa;
        this.listaViajes = new ArrayList<>();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getNroAsientos() {
        return nroAsientos;
    }

    public void addViaje(Viaje viaje) {
        this.listaViajes.add(viaje);
    }

    public Viaje[] getViajes() {
        return this.listaViajes.toArray(new Viaje[0]);
    }
}