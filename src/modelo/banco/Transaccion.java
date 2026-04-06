package modelo.banco;
import modelo.abstracto.*;
import modelo.enums.*;
import modelo.excepciones.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Transaccion {
    private String id;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private double monto;
    private EstadoTransaccion estado;
    private LocalDateTime fecha;
    private String descripcion;
    
    public Transaccion(String id, Cuenta cuentaOrigen, Cuenta cuentaDestino,
            double monto, String descripcion){
        this.id = id;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
        this.estado = EstadoTransaccion.PENDIENTE;
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;
    }
    public void cambiarEstado(EstadoTransaccion nuevo){
        if(!estado.puedeTransaccion(nuevo)){
            throw new EstadoTransaccionInvalidoException(estado.name(), nuevo.name());
        }
        this.estado = nuevo;
    }
    public String generarComprobante(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String destino = (cuentaDestino != null)
                ? cuentaDestino.getNumeroCuenta():"N/A";
                return "========== COMPROBANTE ==========\n"
             + "ID Transacción : " + id + "\n"
             + "Fecha          : " + fecha.format(formatter) + "\n"
             + "Cuenta Origen  : " + cuentaOrigen.getNumeroCuenta() + "\n"
             + "Cuenta Destino : " + destino + "\n"
             + "Monto          : $" + String.format("%.2f", monto) + "\n"
             + "Estado         : " + estado.name() + "\n"
             + "Descripción    : " + descripcion + "\n"
             + "=================================";
    }

    public String getId() {
        return id;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public double getMonto() {
        return monto;
    }

    public EstadoTransaccion getEstado() {
        return estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
        @Override
    public String toString() {
        return "Transaccion[" + id + "] $" + monto
             + " | Estado: " + estado.name()
             + " | " + descripcion;
    }
    
}

