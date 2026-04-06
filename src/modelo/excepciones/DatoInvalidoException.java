package modelo.excepciones;

public class DatoInvalidoException extends BancoRuntimeException {
    private String campo;
    private Object valorRecibido;

    public DatoInvalidoException(String campo, Object valorRecibido) {
        super("Dato invalido en " + campo +  ": " + valorRecibido);
        this.campo = campo;
        this.valorRecibido = valorRecibido;
    }

    public String getCampo() {
        return campo;
    }

    public Object getValorRecibido() {
        return valorRecibido;
    }
    @Override
    public String toString(){
        return "Datoinvalid (Campo: " + campo + ". valor Recibido: " + valorRecibido + ". Mensaje: "  +getMessage();
    }
}
