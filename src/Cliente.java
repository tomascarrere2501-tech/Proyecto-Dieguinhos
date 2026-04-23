public class Cliente {
    //Atributos
    private String email;

    //Constructor
    public Cliente(IdPersona id, Nombre nom, String email) {
        this.IdPersona = IdPersona;
        this.Nombre = Nombre;
        this.email = email;
    }

    //Metodo getEmail
    public String getEmail() {
        return email;
    }

    //Metodo setEmail
    public void setEmail(String email) {
        this.email = email;
    }
}
