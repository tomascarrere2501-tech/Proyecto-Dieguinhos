import java.time.LocalDate;

public class Venta {
    private  String iddocumento;
    private tipo Tipodocummento;
    private LocalDate fecha;

    public Venta ( tipo Tipodocumento, String iddocumento, LocalDate fecha){
        this. iddocumento= iddocumento;
        this.fecha=fecha;
        this.Tipodocummento= Tipodocumento;

    }

    public String getIddocumento() {
        return iddocumento;
    }

    public tipo getTipodocummento() {
        return Tipodocummento;
    }


    public LocalDate getFecha() {
        return fecha;
    }


    public getCliente(){
        return Cliente ;
    }

    public void createPasaje( int asiento, Viaje viaje,Pasajero pasajero ){

        System.out.println("Asiento " +  " " +  asiento);
        System.out.println("Viaje" +  " " +  viaje);
        System.out.println("Pasajero " +  " " +   pasajero);


    }

     public Pasaje  getPasajes(){
        return Pasajes ;
     }

     public int  getMonto(){
        return Monto;
     }


     

}
