package modelo.banco;
import modelo.abstracto.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
import java.time.*;
public abstract class Banco implements Auditable {
    private static final int max_empleados = 50;
    private static final int max_clientes = 200;
    private static final int max_cuentas = 500;
    
    private String nombre;
    private Empleado[] empleados;
    private Cliente[] clientes;
    private Cuenta[] cuentas;
    private int totalEmpleados;
    private int totalClientes;
    private int totalCuentas;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    public Banco(String nombre, String usuarioCreacion){
        if(nombre == null || nombre.trim().isEmpty())
            throw new DatoInvalidoException("Nombre", nombre);
        this.nombre = nombre;
        this.empleados = new Empleado[max_empleados];
        this.clientes = new Cliente[max_clientes];
        this.cuentas = new Cuenta[max_cuentas];
        this.totalEmpleados = 0;
        this.totalClientes = 0;
        this.totalCuentas = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuarioCreacion;
    }
    public void registrarCliente(Cliente cliente)throws capacidadExcedidaException{
            if(totalClientes >= max_clientes)
                throw new capacidadExcedidaException("Cliente del banco", max_clientes);
            clientes[totalClientes] = cliente;
            totalClientes++;
        }
    public void registrarEmpleado(Empleado empleado)throws capacidadExcedidaException{
        if(totalEmpleados >= max_empleados)
            throw new capacidadExcedidaException("Empleado del banco", max_empleados);
        empleados[totalEmpleados] = empleado;
        totalEmpleados++;
    }
    public void abrirCuenta(String idCliente, Cuenta cuenta)throws ClienteNoEncontradoException, capacidadExcedidaException{
        Cliente cliente = buscarCliente(idCliente);
        if(totalCuentas >= max_cuentas)
            throw new capacidadExcedidaException("Cuenta del banco", max_cuentas);
        cliente.agregarCuenta(cuenta);
        cuentas[totalCuentas] = cuenta;
        totalCuentas++;
    }
    public Cliente buscarCliente(String id) throws ClienteNoEncontradoException{
        if(id == null || id.trim().isEmpty())
            throw new DatoInvalidoException("id", id);
        for(int i = 0; i < totalClientes; i++){
            if (clientes[i].getId().equals(id.trim())) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException(id);
    }
    public double calcularNominaTotal() {
        double total = 0.0;
        for (int i = 0; i < totalEmpleados; i++) {
            total += empleados[i].calcularSalarioBase();
        }
        return total;
    }
    public void calcularInteresesMensuales(){
        for(int i = 0; i < totalCuentas; i++){
            double interes = cuentas[i].calcularInteres();
            if(interes > 0){
                System.out.println("INTERES: cuenta:  " + cuentas[i].getNumeroCuenta() + "(" + cuentas[i].getTipocuenta()+ ")" + "$" + String.format("%.2f", interes));
            }
        }
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
    public String getNombre() { return nombre; }
    public int getTotalClientes() { return totalClientes; }
    public int getTotalEmpleados() { return totalEmpleados; }
    public int getTotalCuentas() { return totalCuentas; }
    
    public Empleado[] getEmpleados() {
        Empleado[] copia = new Empleado[totalEmpleados];
        System.arraycopy(empleados, 0, copia, 0, totalEmpleados);
        return copia;
    }
    public Cuenta[] getCuentas() {
        Cuenta[] copia = new Cuenta[totalCuentas];
        System.arraycopy(cuentas, 0, copia, 0, totalCuentas);
        return copia;
    }
    @Override
    public String toString() {
        return "Banco: " + nombre
             + " | Clientes: " + totalClientes
             + " | Empleados: " + totalEmpleados
             + " | Cuentas: " + totalCuentas;
    }
}
