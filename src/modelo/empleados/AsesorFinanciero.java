package modelo.empleados;
import modelo.abstracto.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



public abstract class AsesorFinanciero extends Empleado implements Consultable, Auditable {
    private static final int max_clientes = 20;
    
    private double comisionBase;
    private double metasMensuales;
    private double ventasMes;
    private Cliente[] clientesAsignados;
    private int totalCLientes;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    public AsesorFinanciero(String id, String nombre,String apellido, LocalDate fechaNacimiento, String email, String legajo, LocalDate fechaContratacion, int salarioBase,
            double comisionBono, double metasMensuales, String usuarioCreacion) {
        super(id, nombre,apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        setComisionBase(comisionBase);
        setMetasMensuales(metasMensuales);
        
        this.ventasMes = 0.0;
        this.clientesAsignados = new Cliente[max_clientes];
        this.totalCLientes = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuarioCreacion;
        
        
    }
    @Override
    public double calcularBono(){
        if(ventasMes>= metasMensuales){
            return comisionBase *(ventasMes/ metasMensuales);
        }
        return 0.0;
    }
    @Override
    public double calcularSalarioBase(){
        return getSalarioBase() + calcularBono();
    }
    @Override
    public String obtenerTipo(){
        return "ASESOR FINANCIERO";
    }
    @Override
    public String obtenerDocumentosIdentidad(){
        return "Legajo: " + getLegajo();
    }
    @Override
    public String obtenerResumen(){
        return "Asesor: " + getNombreCompleto() + "|Cliente: " + totalCLientes + "| venta mes: " +ventasMes +"| meta: " + metasMensuales + "|Salario: " + calcularSalarioBase();
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
            throw new DatoInvalidoException("usuario modificacdo", usuario);
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario.trim();
    }
    public void asignarCliente(Cliente cliente) throws capacidadExcedidaException{
        if(totalCLientes >= max_clientes)
            throw new capacidadExcedidaException("Clientes del asesor"+ getNombreCompleto(), max_clientes);
        clientesAsignados[totalCLientes] = cliente;
        totalCLientes++;
    }
    public Cliente[] clientesAsignados(){
        Cliente[] copia = new Cliente[totalCLientes];
        System.arraycopy(clientesAsignados, 0, copia, 0, totalCLientes);
        return copia;
    }
    public void registrarVenta(double monto){
        if(monto <= 0)
            throw new DatoInvalidoException("Monto: ", monto);
        this.ventasMes += monto;
    }

    public double getComisionBase() {
        return comisionBase;
    }

    public void setComisionBase(double comisionBase) {
        if(comisionBase < 0)
            throw new DatoInvalidoException("Comision base", comisionBase);
        this.comisionBase = comisionBase;
    }

    public double getMetasMensuales() {
        return metasMensuales;
    }

    public void setMetasMensuales(double metasMensuales) {
        if(metasMensuales <= 0)
            throw new DatoInvalidoException("Metas mensuales", metasMensuales);
        this.metasMensuales = metasMensuales;
    }

    public double getVentasMes() {
        return ventasMes;
    }

    public int getTotalCLientes() {
        return totalCLientes;
    }

    @Override
    public String toString() {
        return super.toString()
             + " | Clientes: " + totalCLientes
             + " | Ventas: $" + ventasMes;
    }
    
    
    
}
