package modelo.excepciones;

public class CuentaBloqueadaException extends SistemaBancarioException {
    
    public CuentaBloqueadaException(String numeroCuenta) {
        super("La cuenta: " + numeroCuenta + "Esta bloqueada y no puede operar. ", "ERR-CUENTA-001");
    }
    
}
