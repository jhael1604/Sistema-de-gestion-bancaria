package sistema.de.gestión.bancaria;
import modelo.abstracto.*;
import modelo.banco.*;
import modelo.cuentas.*;
import modelo.empleados.*;
import modelo.enums.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
import modelo.personas.*;
import java.time.LocalDate;
public class SistemaBancarioDemo {


    public static void main(String[] args) {
        Banco banco = new Banco ("Banco Comfencal SGB", "ADMIN"){};
        System.out.println("====SISTEMA DE GESTION BANCARIO====");
        
        //Escenario 1. Registrar clientes
        System.out.println("Registrar cliente");
        try{
            ClienteNatural c1 = new ClienteNatural(
                "C001", "Jhael", "Marin", LocalDate.of(1990, 5, 15),
                "jhael@mail.com", TipoDocumento.CEDULA, "1142917184",
                "3001234567", "CAJERO01"
            ){};
            ClienteNatural c2 = new ClienteNatural("C002", "Juan", "Martinez", LocalDate.of(2000, 1, 1),
            "juan@mail.com", TipoDocumento.PASAPORTE, "10451515","300456789", "Cajero01"){};
            
            ClienteEmpresarial c3 = new ClienteEmpresarial("C003", "Julian", "Meza", LocalDate.of(1999, 3, 5), 
            "contacto@comfenalco.com", "900123456-7", "Comfenalco S.A.S", "Jual Meza", "contacto@comfenalco.com", "CAJERO01"){};
            
            
            banco.registrarCliente(c1);
            banco.registrarCliente(c2);
            banco.registrarCliente(c3);
            System.out.println("Registrado: " + c1.obtenerResumen());
            System.out.println("Registrado: " + c2.obtenerResumen());
            System.out.println("Registrado: " + c3.obtenerResumen());
        
        }catch(capacidadExcedidaException e){
            System.out.println("ERROR: " + e );
        }
        
        //Escenario 2 - Abrir cuenta
        
        System.out.println("\n ==== Abrir cuenta ===");
        CuentaAhorros cAhorros = new CuentaAhorros("AH-011", 500_000,0.05,3,"CAJERO01");
        CuentaCorriente cCorriente = new CuentaCorriente("CC-001", 1_000_000, 200_000, 15_000, "CAJERO01");
        CuentaCredito cCredito= new CuentaCredito("CR-012", 5_000_000, 0.02, "ASESOR01") {};
        
        
        try{
            banco.abrirCuenta("C001", cAhorros);
            banco.abrirCuenta("C002", cCorriente);
            banco.abrirCuenta("C003", cCredito);
            
            System.out.println("Cuenta abierta: " + cAhorros.obtenerResumen());
            System.out.println("Cuenta abierta: " + cCorriente.obtenerResumen());
            System.out.println("Cuenta abierta: " + cCredito.obtenerResumen());
        
        }catch(ClienteNoEncontradoException | capacidadExcedidaException e){
            System.out.println("ERROR: " + e);
        }
        //Escenario 3- Desposito exitoso | cuenta bloqueada
        System.out.println("\n ==== Deposito y cuenta");
        try{
            cAhorros.depositar(300_000);
            System.out.println("Deposito exitoso. Saldo:  " + cAhorros.consultarSaldo());
        }catch(CuentaBloqueadaException e){
            System.out.println("Error: " + e);
        }
        cAhorros.setBloqueada(true);
        
        try{
            cAhorros.depositar(500_000);
        }catch(CuentaBloqueadaException e){
            System.out.println("Excepcion capturada: " + e.getMessage());
        }finally{
            cAhorros.setBloqueada(false);
        }
        //Escenario 4 - retiro exitoso
        System.out.println("\n=== Retiro exitoso");
        try{
            cAhorros.retirar(100_000);
            System.out.println("Retiro exitoso: Saldo: " + cAhorros.consultarSaldo());
        }catch(SaldoInsuficienteException | CuentaBloqueadaException e){
            System.out.println("Error: " +  e);
        }
        try{
            cAhorros.retirar(999_999_999);
        }catch(SaldoInsuficienteException e){
            System.out.println("Excepcion");
            System.out.println("Saldo actual: " + e.getSaldoActual());
            System.out.println("Monto solicitado: " + e.getMontoSolicitado());
            
        }catch(CuentaBloqueadaException e){
            System.out.println("ERROR: " + e);
        }
        
        //Escenario 5 - Transferencia entre cuentas
        
        System.out.println("\n === TRANSFERENCIA ENTRE CUENTAS");
        try{
            double montoTransferencia = 150_000;
            cCorriente.retirar(montoTransferencia);
            cCorriente.depositar(montoTransferencia);
            Transaccion t1 = new Transaccion("TRX-001", cCorriente,cAhorros, montoTransferencia, "Transferencia entre cuentas");
            
            t1.cambiarEstado(EstadoTransaccion.PROCESANDO);
            t1.cambiarEstado(EstadoTransaccion.COMPLETADA);
            System.out.println(t1.generarComprobante());
            
        }catch(SaldoInsuficienteException | CuentaBloqueadaException e){
            System.out.println("ERROR: " + e);
        }
        
        // Escenario 6 
        System.out.println("\n Escenario 6");
        try{
            Cajero cajero = new Cajero("E001", "Sofia", "Rios", LocalDate.of(1995, 4, 3),
            "sofia@mai.com", "LEG-02", LocalDate.of(2020, 1, 15),
            2_000_000, Turno.MANANA, "Cedesarrollo", "ADMIN") {};
            
            cajero.registrarTransaccion();
            cajero.registrarTransaccion();
            
            AsesorFinanciero asesor = new AsesorFinanciero("E002", "Dayrelis", "Perez", LocalDate.of(2006, 1, 1),
            "day@mail.com", "LEG-02", LocalDate.of(2025, 8, 5), 3_500_000, 500_000, 5_000_000, "ADMIN"){};
            
            asesor.registrarVenta(6_000_000);
            
            GerenteSucursal gerente = new GerenteSucursal("E003", "Valentina", "Pg", LocalDate.of(1999, 5, 5),
            "valen@mail.com", "LEG-02", LocalDate.of(2020, 5, 7), 6_000_000, "Cedesarrollo", 500_000_000, "ADMIN"){};
            
            banco.registrarEmpleado(cajero);
            banco.registrarEmpleado(asesor);
            banco.registrarEmpleado(gerente);
            
            Empleado[] empleados = banco.getEmpleados();
            for(int i = 0; i <empleados.length; i++){
                System.out.println(empleados[i].obtenerTipo()+"Salario: "+ String.format("%.2f",empleados[i].calcularSalarioBase()));
            }
        }catch(capacidadExcedidaException e){
            System.out.println("Error: " +  e);
        }
        //Escenario 7
        System.out.println("==== Escenario 7 ====");
        Cuenta[] cuentas = banco.getCuentas();
        for(int i = 0; i < cuentas.length; i++){
            System.out.println(cuentas[i].getTipocuenta() + "[" + cuentas[i].getNumeroCuenta() + "]" + "interes mensual: " +
                    String.format("%.2f", cuentas[i].calcularInteres()));
        }
        // Escenario 8
        System.out.println("Escenario 8");
        Transaccion t2 = new Transaccion("TRX-002", cAhorros, null, 50_000, "Prueba");
        t2.cambiarEstado(EstadoTransaccion.PROCESANDO);
        t2.cambiarEstado(EstadoTransaccion.COMPLETADA);
        try{
            t2.cambiarEstado(EstadoTransaccion.PROCESANDO);
        }catch(EstadoTransaccionInvalidoException e){
            System.out.println("ERROR: " + e.getMessage());
        }
        //Escenario 9
        System.out.println("Escenario 9");
        GerenteSucursal gerente2 = new GerenteSucursal("E004","Blanca", "Lopez", LocalDate.of(1995, 3, 8),"blanca@mai.com",
        "LEG-04",LocalDate.of(2015, 7, 9),7_000_000, "Barrio espana", 300_000_000,"ADMIN"){};
        
        Cajero cajeroSinPermiso =  new Cajero("E005", "Maria", "Sofia", LocalDate.of(1998, 5, 3),
        "maria@mai.com","LEG-05",LocalDate.of(2016, 5, 21),1_800_000, Turno.TARDE, "Barrio espana", "ADMIN");
        try{
            gerente2.aprobarCredito(cajeroSinPermiso, "CR-001", 10_000_000);
        }catch(PermisoInsuficienteException e){
            System.out.println("ERROR: " + e.getMessage());
        }
        gerente2.aprobarCredito(gerente2, "CR-001", 10_000_000);
        
        //ESCENARIO 10
        System.out.println("ESCENARIO 10 ");
        try{
            ClienteNatural clienteConNotif = (ClienteNatural) banco.buscarCliente("C001");
            ClienteNatural clienteSinNotif = (ClienteNatural) banco.buscarCliente("C002");
            clienteSinNotif.setNotificacionesActivas(false);
            
            clienteConNotif.notificar("Su deposito de 200.000 fue procesado");
            clienteSinNotif.notificar("Tiene un nuevo movimiento en su cuenta");
            
        }catch(ClienteNoEncontradoException e){
            System.out.println("ERROR: " + e);
        }
        //Escenario 11
        System.out.println("Escenario 11");
        cCorriente.registrarModificacion("ASESOR02");
        System.out.println("Ultima modificacion: " + cCorriente.obtenerUltimaModificacion());
        
        System.out.println("Usuario modificado: " + cCorriente.obtenerUsuarioModificacion());
        
        //Escenario 12 - Nomina total del banco
        System.out.println("Escenario 12 nomina total");
        double nomina = banco.calcularNominaTotal();
        System.out.println("Nomina total: " + String.format("%.2f", nomina));
        
        System.out.println("\n Fin");
        
    }
    
    
    
    
    
}
