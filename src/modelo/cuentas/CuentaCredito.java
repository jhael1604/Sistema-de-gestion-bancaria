package modelo.cuentas;
import java.time.LocalDateTime;
import modelo.abstracto.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
public class CuentaCredito extends Cuenta implements Consultable, Transaccionable {
    private static final double comision_avance = 0.05;
    double limiteCredito;
    double tasaInteres;
    double deudaActual;

    public CuentaCredito(String numeroCuenta,double limiteCredito, double tasaInteres, String usuarioCreacion) {
        super(numeroCuenta, 0.0, usuarioCreacion);
        setLimiteCredito(limiteCredito);
        setTasaInteres(tasaInteres);
        this.deudaActual = 0.0;
    }
    @Override
    public double calcularInteres(){
        return deudaActual * tasaInteres / 12;
    }
    @Override
    public double getLimiteRetiro(){
        return limiteCredito - deudaActual;
    }
    @Override
    public String getTipocuenta() {
        return "CUENTA_CREDITO";
    }
    @Override
    public void depositar(double monto) throws CuentaBloqueadaException{
        verificarBloqueada();
        if(monto <= 0)
            throw new DatoInvalidoException("Monto depositar", monto);
        deudaActual = Math.max(0, deudaActual- monto);
    }
    @Override
    public void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException{
        verificarBloqueada();
        if(monto <= 0)
            throw new DatoInvalidoException("Monto retiro", monto);
        double disponible = getLimiteRetiro();
        if(monto > disponible)
            throw new SaldoInsuficienteException(disponible, monto);
        deudaActual += monto;
    }
    @Override
    public double calcularComision(double monto){
        return monto * comision_avance;
    }
    @Override
    public double consultarSaldo(){
        return getSaldo();
    }
    @Override
    public String obtenerResumen(){
        return getTipocuenta() + "| N: " + getNumeroCuenta() 
                +"| Limite: " + limiteCredito
                +"| Deuda: " + deudaActual
                +"| Disponible: " + getLimiteRetiro()
                +"| Tasa: " + (tasaInteres*100)+"%";
    }
    @Override
    public boolean estaActivo(){
        return !isBloqueada();
    }
    @Override
    public String obtenerTipo(){
        return getTipocuenta();
    }
    public double getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(double limiteCredito) {
        if (limiteCredito <= 0)
            throw new DatoInvalidoException("limiteCredito", limiteCredito);
        this.limiteCredito = limiteCredito;
    }
    public double getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(double tasaInteres) {
        if (tasaInteres <= 0 || tasaInteres >= 1)
            throw new DatoInvalidoException("tasaInteres", tasaInteres);
        this.tasaInteres = tasaInteres;
    }
    public double getDeudaActual() { return deudaActual; }
}
