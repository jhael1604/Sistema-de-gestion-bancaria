package modelo.interfaces;
import java.time.LocalDateTime;
public interface Auditable {
    //Retorna la fecha en que se creó la entidad
    LocalDateTime obtenerFechaCreacion();
    //Retorna la fecha del último cambio registrado
    LocalDateTime obtenerUltimaModificacion();
    //Retorna el legajo del usuario que hizo el último cambio
    String obtenerUsuarioModificacion();
    //Actualiza la fecha y el usuario de modificación
    void registrarModificacion(String usuario);
}
