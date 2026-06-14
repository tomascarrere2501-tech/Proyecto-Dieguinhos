package excepciones;

public class SVPException extends RuntimeException {
    public SVPException(String msg) {
        super(msg);
    }
    public String getMessage() {
        return super.getMessage();
    }
}