package modelo.abstracto;
import java.time.LocalDate;
import modelo.excepciones.capacidadExcedidaException;
import modelo.excepciones.DatoInvalidoException;
import java.time.LocalDate;
import java.time.Period;
public abstract class Cliente extends Persona {
    private static final int Max_Cuentas = 5;
    
    private Cuenta[] cuentas;
    private int totalCuentas;
    private boolean activo;
    public Cliente(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email) {
        super(id, nombre ,apellido, fechaNacimiento, email);
        
        this.cuentas = new Cuenta[Max_Cuentas];
        this.totalCuentas = 0;
        this.activo = true;
        
        
    }
    @Override
    public abstract String obtenerDocumentosIdentidad();
    
    @Override
    public abstract String obtenerTipo();
    
    @Override
    public int calcularEdad(){
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }
    
    public void agregarCuenta(Cuenta cuenta) throws capacidadExcedidaException{
        if(totalCuentas >= Max_Cuentas){
            throw new capacidadExcedidaException(
                    "Cuenta del cliente" + getNombreCompleto(), Max_Cuentas
            );
        }
        cuentas[totalCuentas] = cuenta;
        totalCuentas++;
    }
    public Cuenta[] getCuenta(){
        Cuenta[] copia = new Cuenta[totalCuentas];
        System.arraycopy(cuentas, 0, copia, 0, totalCuentas);
        return copia;
    }

    public int getTotalCuentas() {
        return totalCuentas;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    @Override
    public String toString(){
        return super.toString() + "| Cuentas: " + totalCuentas + "|" + Max_Cuentas + "| Activo: " + activo;
    }
}
