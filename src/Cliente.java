public class Cliente extends Persona {
    //Atributos
    private String email;

    public Cliente(IdPersona id, Nombre nom, String email) {
        super(id, nom);
        this.email = email;
    } //Constructor

    public String getEmail() {
        return this.email;
    } //Metodo getEmail 
    
    public void setEmail(String email) {
        this.email = email;
    } //Metodo setEmail
}
