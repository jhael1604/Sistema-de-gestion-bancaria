package modelo.cuentas;
import java.time.LocalDateTime;
import modelo.abstracto.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
public class CuentaCorriente extends Cuenta implements Consultable, Transaccionable {
    private static final double comision_transferencia = 0.003;
    
   private double montoSobregiro;
   private double comisionMantenimiento;

    public CuentaCorriente(String numeroCuenta, double saldoInicial, double montoSobregiro, double comisionMantenimiento, String usuarioCreacion) {
        super(numeroCuenta, saldoInicial, usuarioCreacion);
    setMontoSobregiro(montoSobregiro);
    setComisionMantenimiento(comisionMantenimiento);
        
    }
    @Override
    public double calcularInteres(){
        return 0.0;
    }
    @Override
    public double getLimiteRetiro(){
        return getSaldo() * montoSobregiro;
    }
    @Override
    public String getTipocuenta(){
        return "Cuenta Corriente";
    }
    @Override
    public void depositar(double monto) throws CuentaBloqueadaException{
        verificarBloqueada();
        if(monto <= 0)
            throw new DatoInvalidoException("Monto deposito", monto);
        setSaldo(getSaldo() + monto);
    }
    @Override
    public void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException{
        verificarBloqueada();
        if(monto <= 0)
            throw new DatoInvalidoException("Monto retiro", monto);
        double disponible = getSaldo() + montoSobregiro;
        if(monto > getSaldo())
            throw new SaldoInsuficienteException(disponible, monto);
        setSaldo(getSaldo() - monto);
    }
    @Override
    public double calcularComision(double monto){
        return monto *  comision_transferencia;
    }
    @Override
    public double consultarSaldo(){
        return getSaldo();
    }
    @Override
    public String obtenerResumen(){
        return getTipocuenta() + "| N: " + getNumeroCuenta()
                + "|Saldo: " + getSaldo()
                +"|Sobre grio disponible: " + montoSobregiro
                +"| Comision mantenimieto: "+ comisionMantenimiento;
    }
    @Override
    public boolean estaActivo(){
        return !isBloqueada();
    }
    @Override
    public String obtenerTipo(){
        return getTipocuenta();
    }
    public void cobrarMantenimiento() throws CuentaBloqueadaException, SaldoInsuficienteException{
        retirar(comisionMantenimiento);
    }
    public double getMontoSobregiro(){return montoSobregiro;}
    public void setMontoSobregiro(double montoSobregiro){
        if(montoSobregiro < 0)
            throw new DatoInvalidoException("Monto sobre Grio", montoSobregiro);
        this.montoSobregiro = montoSobregiro;
    }
        public double getComisionMantenimiento() { return comisionMantenimiento; }
    public void setComisionMantenimiento(double comisionMantenimiento) {
        if (comisionMantenimiento < 0)
            throw new DatoInvalidoException("comisionMantenimiento", comisionMantenimiento);
        this.comisionMantenimiento = comisionMantenimiento;
    }
}
