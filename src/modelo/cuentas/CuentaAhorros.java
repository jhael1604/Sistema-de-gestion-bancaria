package modelo.cuentas;
import java.time.LocalDateTime;
import modelo.abstracto.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
public class CuentaAhorros extends Cuenta implements Consultable, Transaccionable {
    private static final double comision_retiro = 0.01;
    
    private double tasaInteres;
    private int retirosMesActual;
    private int maxRetiroMes;

    public CuentaAhorros(String numeroCuenta, int saldo,
            double tasaInteres,  int maxRetiroMes, String usuarioCreacion) {
        super(numeroCuenta, saldo,usuarioCreacion);
        setTasaInteres(tasaInteres);
        setMaxRetirosMes(maxRetiroMes);
        this.maxRetiroMes = 0;
    }
    @Override
    public double calcularInteres(){
        return getSaldo()* tasaInteres/12;
    }
    @Override
    public double getLimiteRetiro(){
        return getSaldo() * 0.80;
    }
    @Override
    public String getTipocuenta() {
    return "CUENTA_AHORROS";
}
    
    @Override
    public void depositar(double monto) throws CuentaBloqueadaException{
        verificarBloqueada();
        if(monto <= 0)
            throw new DatoInvalidoException("Monto deposito", monto);
        setSaldo(getSaldo() + monto);
        
    }
    @Override
    public void retirar (double monto) throws SaldoInsuficienteException, CuentaBloqueadaException{
        verificarBloqueada();
        if(monto <= 0)
            throw new DatoInvalidoException("Monto retiro", monto);
        if(monto > getSaldo())
            throw new SaldoInsuficienteException(getSaldo(), monto);
        double comision = calcularComision(monto);
        setSaldo(getSaldo() -  monto - comision);
        retirosMesActual++;
    }
    @Override
    public double consultarSaldo(){
        return getSaldo();
    }
    @Override
    public String obtenerResumen(){
        return getTipocuenta() + "| N: " +  getNumeroCuenta() + "|Saldo: " + getSaldo()
                +"Tasa: " +  (tasaInteres*100) +  "%"
                +"Retiro mes: " +  retirosMesActual +  "/" + maxRetiroMes;
    }
    @Override
    public boolean estaActivo(){
        return !isBloqueada();
    }
    
    public void reiniciarRetirosMensuales(){
        this.retirosMesActual = 0;
    }
    public double getTasaInteres() {return tasaInteres;}
    public void setTasaInteres(double tasaInteres){
        if(tasaInteres <= 0 || tasaInteres >= 1)
            throw new DatoInvalidoException("tasainteres", tasaInteres);
        this.tasaInteres = tasaInteres;
    }
    public int getRetirosMesActual(){return retirosMesActual;}
    
    public void setMaxRetirosMes(int maxRetiroMes){
        if(maxRetiroMes <= 0)
            throw new DatoInvalidoException("Maximo retiro", maxRetiroMes);
        this.maxRetiroMes = maxRetiroMes;
    }
        @Override
    public String obtenerTipo() {
        return getTipocuenta();
    }
    @Override
public double calcularComision(double monto) {
    if (retirosMesActual >= maxRetiroMes) {
        return monto * comision_retiro; 
    }
    return 0.0;
}
}
