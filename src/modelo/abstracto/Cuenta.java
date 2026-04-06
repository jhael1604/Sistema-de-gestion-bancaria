package modelo.abstracto;
import java.time.LocalDateTime;
import modelo.excepciones.*;
import modelo.interfaces.*;
import modelo.banco.*;
public abstract class Cuenta implements Auditable  {
    private static int max_historial = 20;
    
    private String numeroCuenta;
    private double saldo;
    private boolean bloqueada;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    private Transaccion[] historial;
    private int contador = 0;
    
    public Cuenta(String numeroCuenta, double saldoInicial,
            String usuarioCreacion){
        setNumeroCuenta(numeroCuenta);
        setSaldo(saldoInicial);
        this.bloqueada = false;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuarioCreacion;
        this.historial = new Transaccion[max_historial];
        this.contador = 0;
    }
    public abstract double calcularInteres();
    public abstract double getLimiteRetiro();
    public abstract String getTipocuenta();
    
    
    public void verificarBloqueada() throws CuentaBloqueadaException{
        if(bloqueada){
            throw new CuentaBloqueadaException(numeroCuenta);
        }
    }
    public void agregarHistorial(Transaccion t) throws capacidadExcedidaException{
        if(contador >= max_historial){
            throw new capacidadExcedidaException("historial cuenta"+ numeroCuenta, max_historial);
        }
        historial[contador] = t;
        contador++;
    }
    public Transaccion[] getHistorial(){
        Transaccion[] copia = new Transaccion[contador];
        System.arraycopy(historial, 0, copia, 0, contador);
        return copia;
    }
    @Override
    public LocalDateTime obtenerFechaCreacion(){
        return fechaCreacion;
    }
    @Override
    public LocalDateTime obtenerUltimaModificacion(){
        return ultimaModificacion;
    }
    @Override
    public String obtenerUsuarioModificacion(){
        return usuarioModificacion;
    }
    @Override
    public void registrarModificacion(String usuario){
        if(usuario == null || usuario.trim().isEmpty()){
            throw new DatoInvalidoException("Usuario modificado", usuario);
        }
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario.trim();
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        if(numeroCuenta == null || numeroCuenta.trim().isEmpty()){
            throw new DatoInvalidoException("numeroCuenta", numeroCuenta);
        }
        this.numeroCuenta = numeroCuenta.trim();
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if(saldo < 0){
            throw new DatoInvalidoException("Saldo", saldo);
        }
        this.saldo = saldo;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }

    public int getContador() {
        return contador;
    }
    @Override
    public String toString() {
        return getTipocuenta()
             + " | N°: " + numeroCuenta
             + " | Saldo: $" + saldo
             + " | Bloqueada: " + bloqueada
             + " | Transacciones: " + contador + "/" + max_historial;
    }
    
    
    
    
    
}
