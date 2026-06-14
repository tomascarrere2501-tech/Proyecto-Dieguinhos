package modelo;

import java.io.Serializable;

public class PagoEfectivo extends Pago implements Serializable {
    private static final long serialVersionUID = 1L;

    public PagoEfectivo(int monto) {
        super(monto);
    }
}