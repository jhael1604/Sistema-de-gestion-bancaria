package modelo.personas;
import modelo.abstracto.*;
import modelo.excepciones.*;
import modelo.interfaces.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
public abstract class ClienteEmpresarial extends Cliente implements Consultable, Notificable, Auditable{
    private String nit;
    private String razonSocial;
    private String representanteLegal;
    private boolean notificacionesActivas;
    private String emailContactos;
    
    private LocalDateTime fechaCracion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    public ClienteEmpresarial(String id, String nombre,String apellido, LocalDate fechaNacimiento, String email, String nit
    , String razonSocial, String representanteLegal, String emailContactos, String usuarioCreacion) {
        super(id, nombre,apellido, fechaNacimiento, email);
        
        setNit(nit);
        setRazonSocial(razonSocial);
        setRepresentanteLegal(representanteLegal);
        setEmailContactos(emailContactos);
        this.fechaCracion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuarioCreacion;
    }
    @Override
    public String obtenerTipo(){
        return "CLIENTE_EMPRESARIAL";
    }
    @Override
    public String obtenerDocumentosIdentidad(){
        return "NIT: " + nit;
    }
    @Override
    public String obtenerResumen(){
        return "Empresa: " + razonSocial
             + " | " + obtenerDocumentosIdentidad()
             + " | Rep. Legal: " + representanteLegal
             + " | Cuentas: " + getTotalCuentas()
             + " | Activo: " + isActivo();
    }
    @Override
    public boolean estaActivo(){
        return isActivo();
    }
    @Override
    public void notificar(String mensaje){
        if(notificacionesActivas){
            System.out.println("[NOTIFICACION: " + razonSocial + "]" + mensaje);
        }else{
            System.out.println("[NOTIFICACION DESACTIVDA para: " + razonSocial + "]");
        }
    }
    @Override
    public String obtenerContacto(){
        return emailContactos;
    }
    @Override
    public boolean aceptaNotificaciones(){
        return notificacionesActivas;
    }
    @Override
    public LocalDateTime obtenerFechaCreacion(){
        return fechaCracion;
    }
    @Override
    public LocalDateTime obtenerUltimaModificacion(){
        return ultimaModificacion;
    }
    @Override
    public String obtenerUsuarioModificacion() {
        return usuarioModificacion;
    }
    @Override
    public void registrarModificacion(String usuario){
        if(usuario == null || usuario.trim().isEmpty())
            throw new DatoInvalidoException("Usuario modificado", usuario);
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario.trim();
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        if(nit == null || nit.trim().isEmpty())
            throw new DatoInvalidoException("nit", nit);
        this.nit = nit.trim();
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        if(razonSocial == null || razonSocial.trim().isEmpty())
            throw new DatoInvalidoException("Razon Social", razonSocial);
        this.razonSocial = razonSocial.trim();
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        if(representanteLegal == null || representanteLegal.trim().isEmpty())
            throw new DatoInvalidoException("Representante Legal", representanteLegal);
        
        this.representanteLegal = representanteLegal.trim();
    }

    public String getEmailContactos() {
        return emailContactos;
    }

    public void setEmailContactos(String emailContactos) {
        if(emailContactos == null || representanteLegal.trim().isEmpty())
            throw new DatoInvalidoException("Email contactos", emailContactos);
        this.emailContactos = emailContactos.trim();
    }

    public void setNotificacionesActivas(boolean notificacionesActivas) {
        this.notificacionesActivas = notificacionesActivas;
    }
    @Override
    public String toString() {
        return super.toString() + " | Razón Social: " + razonSocial;
    }
    
    
    
}
