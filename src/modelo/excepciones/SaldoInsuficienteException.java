package modelo.excepciones;

public class SaldoInsuficienteException extends SistemaBancarioException {
    double saldoActual;
    double montoSolicitado;
    
    public SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
        super("Saldo insuficiente. Saldo actual: " + saldoActual + "| Monto solicitado: " + montoSolicitado, "ERR-SALDO-001");
        this.saldoActual = saldoActual;
        this.montoSolicitado = montoSolicitado;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }
    @Override
    public String toString(){
        return super.toString() + "| Saldo: " + saldoActual +  "| Monto: " + montoSolicitado;
    }
    
}
