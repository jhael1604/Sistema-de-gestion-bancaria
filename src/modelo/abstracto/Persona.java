
package modelo.abstracto;
import modelo.excepciones.DatoInvalidoException;
import java.time.LocalDate;
import java.time.Period;
public abstract class Persona {
    private String id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String email;
    
    public Persona(String id, String nombre,String apellido, LocalDate fechaNacimiento, String email){
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.apellido = apellido;
        
    }
    
    public abstract int calcularEdad();
    public abstract String obtenerTipo();
    public abstract String obtenerDocumentosIdentidad();
    
    public String getNombreCompleto(){
        return nombre +""+ apellido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id == null || id.trim().isEmpty()){
            throw new DatoInvalidoException("Id", id);
        }
        this.id = id.trim();
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if(fechaNacimiento == null){
            throw new DatoInvalidoException("fechaNacimiento", null);
        }
        if(fechaNacimiento.isAfter(LocalDate.now())){
            throw new DatoInvalidoException("FechaNacimiento", fechaNacimiento);
        }
        
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || !email.contains("@")){
            throw new IllegalArgumentException("El email debe contener @");
        }
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre == null || nombre.trim().isEmpty()){
            throw new DatoInvalidoException("Nombre", nombre);
        }
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if(apellido == null || apellido.trim().isEmpty()){
            throw new DatoInvalidoException("Apellido", apellido);
        }
        this.apellido = apellido.trim();
    }
    @Override
    public String toString(){
        return obtenerTipo() +  "| " +  getNombreCompleto() + "| Documento: " + obtenerDocumentosIdentidad() + "| Email: " + getEmail();
    }
    
    

    
    
    
    
    
    
    
}
