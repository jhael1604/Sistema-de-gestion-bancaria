
package modelo.personas;
import modelo.abstracto.*;
import modelo.enums.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
public abstract class ClienteNatural extends Cliente implements Consultable, Notificable, Auditable {
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private boolean  notificacionesActivas;
    private String telefono;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    public ClienteNatural(String id, String nombre,String apellido, LocalDate fechaNacimiento, String email, TipoDocumento tipoDocumento, String numeroDocumento
    ,String telefono, String usuarioCreacion) {
        super(id, nombre,apellido, fechaNacimiento, email);
        setTipoDocumento(tipoDocumento);
        setNumeroDocumento(numeroDocumento);
        setTelefono(telefono);
        this.notificacionesActivas = true;
        this.fechaCreacion = LocalDateTime.now();
        this.usuarioModificacion = usuarioCreacion;
        
    }
    @Override
    public String obtenerTipo(){
        return "CLIENTE_NATURAL";
    }
    @Override
    public String obtenerDocumentosIdentidad(){
        return tipoDocumento.name() + ": " + numeroDocumento;
    }
    
    @Override
    public String obtenerResumen(){
        return "Nombre: " + getNombreCompleto()
             + " | " + obtenerDocumentosIdentidad()
             + " | Edad: " + calcularEdad()
             + " | Cuentas: " + getTotalCuentas()
             + " | Activo: " + isActivo();
    }
    @Override
    public void notificar(String mensaje){
        if(notificacionesActivas){
            System.out.println("[NOTIFICACION " + getNombreCompleto() + "]" + mensaje);
        }else{
            System.out.println("[NOTIFICACION DESACTIVADA para: " + getNombreCompleto());
        }
    }
    @Override
    public String obtenerContacto() {
        return telefono;
    }
    @Override
    public boolean aceptaNotificaciones() {
        return notificacionesActivas;
    }
    @Override
    public LocalDateTime obtenerFechaCreacion() {
        return fechaCreacion;
    }
    @Override
    public LocalDateTime obtenerUltimaModificacion() {
        return ultimaModificacion;
    }
    @Override
    public String obtenerUsuarioModificacion() {
        return usuarioModificacion;
    }
    @Override
    public void registrarModificacion(String usuario) {
        if (usuario == null || usuario.trim().isEmpty())
            throw new DatoInvalidoException("usuarioModificacion", usuario);
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario.trim();
    }
    public TipoDocumento getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null)
            throw new DatoInvalidoException("tipoDocumento", null);
        this.tipoDocumento = tipoDocumento;
    }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty())
            throw new DatoInvalidoException("numeroDocumento", numeroDocumento);
        this.numeroDocumento = numeroDocumento.trim();
    }
    public String getTelefono(){return telefono;}
    public void setTelefono(String telefono){
        if(telefono == null || telefono.trim().isEmpty())
            throw new DatoInvalidoException("telefono", telefono);
        this.telefono = telefono.trim();
        
    }
    public void setNotificacionesActivas(boolean notificacionesActivas){
        this.notificacionesActivas = notificacionesActivas;
    }
    @Override
    public String toString() {
        return super.toString() + " | Tel: " + telefono;
    }
    @Override
    public boolean estaActivo() {
    return isActivo(); // isActivo() viene heredado de Cliente
}
}
