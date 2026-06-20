import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
    private String email;
    private List<Venta> ventasInternas;

    public Cliente(IdPersona id, Nombre nom, String email) {
        super(id, nom);
        this.email = email;
        this.ventasInternas = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addVenta(Venta venta) {
        this.ventasInternas.add(venta);
    }

    public Venta[] getVentas() {
        return this.ventasInternas.stream().toArray(Venta[]::new);
    }
}
