public class Cliente {
    //Atributos
    private String email;

    //Metodo 1
    public Cliente(IdPersona id, Nombre nom, String email) {
        this.IdPersona = IdPersona;
        this.Nombre = Nombre;
        this.email = email;
    }

    //Metodo 2
    public String getEmail() {
        return email;
    }

    //Metodo 3
    public void setEmail(String email) {
        this.email = email;
    }
}