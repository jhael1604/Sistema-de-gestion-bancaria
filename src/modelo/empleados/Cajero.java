package modelo.empleados;
import modelo.abstracto.*;
import modelo.enums.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class Cajero extends Empleado implements Consultable, Auditable{
    private static final double bono_por_transaccion = 500.0;
    private static final int meta_transaccion = 50;
    
    private Turno turno;
    private String sucursalAsignada;
    private int transaccioneDia;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    public Cajero(String id, String nombre,String apellido, LocalDate fechaNacimiento, String email, String legajo, LocalDate fechaContratacion, int salarioBase,
            Turno turno, String sucursalAsignada, String usuarioCreacion) {
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        
        setTurno(turno);
        setSucursalAsignada(sucursalAsignada);
        this.transaccioneDia = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuarioCreacion;
    }
    @Override
    public double calcularBono(){
        if(transaccioneDia >= meta_transaccion){
            return transaccioneDia * bono_por_transaccion;
        }
        return 0.0;
    }
    @Override
    public double calcularSalarioBase(){
        return getSalarioBase() + calcularBono();
    }
    @Override
    public String obtenerTipo(){
        return "CAJERO";
    }
    @Override
    public String obtenerDocumentosIdentidad(){
        return "Legajo: " + getLegajo();
    }
    @Override
    public String obtenerResumen(){
        return "Cajero: " + getNombreCompleto()
             + " | Sucursal: " + sucursalAsignada
             + " | Turno: " + turno.name()
             + " | Transacciones hoy: " + transaccioneDia
             + " | Salario: $" + calcularSalarioBase();
    }
    @Override
    public boolean estaActivo(){
        return isActivo();
    }
    @Override
    public LocalDateTime obtenerFechaCreacion() { return fechaCreacion; }
 
    @Override
    public LocalDateTime obtenerUltimaModificacion() { return ultimaModificacion; }
 
    @Override
    public String obtenerUsuarioModificacion() { return usuarioModificacion; }
    
    @Override
    public void registrarModificacion(String usuario) {
        if (usuario == null || usuario.trim().isEmpty())
            throw new DatoInvalidoException("usuarioModificacion", usuario);
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario.trim();
    }
    public void registrarTransaccion() {
        this.transaccioneDia++;
    }
    public void reiniciarContadorDiario() {
        this.transaccioneDia = 0;
    }
    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) {
        if (turno == null)
            throw new DatoInvalidoException("turno", null);
        this.turno = turno;
    }
    public String getSucursalAsignada() { return sucursalAsignada; }
    public void setSucursalAsignada(String sucursalAsignada) {
        if (sucursalAsignada == null || sucursalAsignada.trim().isEmpty())
            throw new DatoInvalidoException("sucursalAsignada", sucursalAsignada);
        this.sucursalAsignada = sucursalAsignada.trim();
    }
    public int getTransaccionesDia() { return transaccioneDia; }
    
    @Override
    public String toString() {
        return super.toString()
             + " | Turno: " + turno.name()
             + " | Sucursal: " + sucursalAsignada;
    }

}

