package modelo.excepciones;

public class EstadoTransaccionInvalidoException extends BancoRuntimeException {
    
    public EstadoTransaccionInvalidoException(String estadoOrigen, String estadoDestino) {
        super("Transaccion de estado invalido." + estadoOrigen + "a" + estadoDestino);
    }
    @Override
    public String toString() {
        return "Transición de Estado No Permitida: " + getMessage();
    }
}
