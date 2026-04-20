public class Cliente extends Persona {
        //Atributos
        private String email;

        //Metodo 1
        public Cliente(IdPersona id, Nombre nom, String email) {
            super(id, nom);
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
//corregido por tomasCarrere