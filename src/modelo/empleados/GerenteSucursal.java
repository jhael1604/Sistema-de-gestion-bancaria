package modelo.empleados;
import modelo.abstracto.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
public abstract class GerenteSucursal extends Empleado implements Consultable, Auditable {
    private static final int max_empleados =30;
    private static final double bono_fijo_gerencia = 2_000_000.0;
    private static final double bono_por_ano_antiguedad = 150_000.0;
    
    private String sucursal;
    private double presupuestoAnual;
    private Empleado[] empleadosAcargo;
    private int totalEmpleados;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    public GerenteSucursal(String id, String nombre,String apellido, LocalDate fechaNacimiento, String email, String legajo, LocalDate fechaContratacion, int salarioBase,
            String sucursal, double presupuestoAnual, String usuarioCreacion) {
        super(id, nombre,apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        setSucursal(sucursal);
        setPresupuestoAnual(presupuestoAnual);
        this.empleadosAcargo = new Empleado[max_empleados];
        this.totalEmpleados =0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuarioCreacion;
    }
    @Override
    public double calcularBono(){
        return bono_fijo_gerencia + (calcularAntiguedad() *  bono_por_ano_antiguedad);
    }
    @Override
    public double calcularSalarioBase(){
        return getSalarioBase() + calcularBono();
    }
    @Override
    public String obtenerTipo(){
        return "Gerente de sucursal";
    }
    @Override
    public String obtenerDocumentosIdentidad(){
        return "Legajo: " + getLegajo();
    }
    @Override
    public String obtenerResumen(){
        return "Gerente: "+ getNombreCompleto()
                + "Sucursal: " +sucursal
                + "Empleados a cargo: " + empleadosAcargo
                +"Antiguedad: "+ calcularAntiguedad()+ "Año"
                + "Salario: " + calcularSalarioBase();
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
    public void registrarModificacion(String usuario){
        if(usuario == null || usuario.trim().isEmpty())
            throw new DatoInvalidoException("Usuario modificado", usuario);
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario.trim();
    }
    public void aprobarCredito(Empleado solicitante, String numeroCuenta, double monto){
        if(!(solicitante instanceof GerenteSucursal)){
            throw new PermisoInsuficienteException("Aprobar credito", solicitante.obtenerTipo());
        }
        System.out.println("[Credito aprobado] Cuenta: " + numeroCuenta + "|Monto: " +  monto +  "Aprobado por: "+ getNombreCompleto());
    }
    public void agregarEmpleado(Empleado empleado) throws capacidadExcedidaException{
        if(totalEmpleados >= max_empleados)
            throw new capacidadExcedidaException("Empleado a cargo"+getNombreCompleto(), max_empleados);
        empleadosAcargo[totalEmpleados] = empleado;
        totalEmpleados++;
    }
    public Empleado[] getEmpleadoACargo(){
        Empleado[] copia = new Empleado[totalEmpleados];
        System.arraycopy(empleadosAcargo, 0, copia, 0, totalEmpleados);
        return copia;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        if(sucursal == null || sucursal.trim().isEmpty())
            throw new DatoInvalidoException("Sucursal", sucursal);
        this.sucursal = sucursal.trim();
    }

    public double getPresupuestoAnual() {
        return presupuestoAnual;
    }

    public void setPresupuestoAnual(double presupuestoAnual) {
        if(presupuestoAnual <= 0)
            throw new DatoInvalidoException("Presupuesto anual", presupuestoAnual);
        this.presupuestoAnual = presupuestoAnual;
    }

    public int getTotalEmpleados() {
        return totalEmpleados;
    }
    @Override
    public String toString(){
        return super.toString() + "|Sucursal: " + sucursal + "|Empleados: " + totalEmpleados;
    }
    
}
