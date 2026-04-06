package modelo.interfaces;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.SaldoInsuficienteException;
public interface Transaccionable {
    //Aumenta el saldo de la cuenta
    void depositar(double monto) throws CuentaBloqueadaException;
    //Disminuye el saldo validando disponibilidad
    void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException;
    //Retorna el valor de la comisión según el tipo de cuenta
    double calcularComision(double monto);
    //Retorna el saldo disponible actual
    double consultarSaldo();
}
