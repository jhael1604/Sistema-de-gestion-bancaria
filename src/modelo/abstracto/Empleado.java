package modelo.abstracto;
import java.time.LocalDate;
import java.time.Period;
import java.time.LocalDate;
import modelo.excepciones.*;

public abstract class Empleado extends Persona{
    private String legajo;
    private LocalDate fechaContratacion;
    private int salarioBase;
    private boolean activo;
    public Empleado(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email, String legajo,
            LocalDate fechaContratacion, int salarioBase) {
        super(id, nombre,apellido, fechaNacimiento, email);
        setLegajo(legajo);
        setFechaContratacion(fechaContratacion);
        setSalarioBase(salarioBase);
        this.activo = true;
        
    }
    public abstract double calcularSalarioBase();
    public abstract double calcularBono();
    
    public int calcularAntiguedad(){
        if (fechaContratacion == null) return 0;
        return Period.between(fechaContratacion, LocalDate.now()).getYears();
    }
    @Override
    public int calcularEdad(){
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    public void setLegajo(String legajo) {
        if(legajo == null || legajo.trim().isEmpty()){
            throw new DatoInvalidoException("Legajo", legajo);
        }
        this.legajo = legajo;
    }
    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        if(fechaContratacion == null){
            throw new DatoInvalidoException("Fecha contratacion", null);
            
        }
        if(fechaContratacion.isAfter(LocalDate.now())){
            throw new DatoInvalidoException("Fecha contratacion", fechaContratacion);
        }
        this.fechaContratacion = fechaContratacion;
    }

    public int getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(int salarioBase) {
        if(salarioBase <= 0){
            throw new DatoInvalidoException("Salario base", salarioBase);
        }
        this.salarioBase = salarioBase;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getLegajo() {
        return legajo;
    }

    public boolean isActivo() {
        return activo;
    }
    @Override
    public String toString(){
        return super.toString() + " Legajo: " + legajo + "Antiguedad: "+ calcularAntiguedad()
                + "Salario: " + calcularSalarioBase();
    }
    
}
