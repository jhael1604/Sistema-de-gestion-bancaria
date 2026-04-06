package modelo.interfaces;

public interface Notificable {
    //Envía (imprime) un mensaje al cliente
    void notificar(String mensaje);
    //Retorna el email o teléfono de contacto
    String obtenerContacto();
    //Indica si el cliente tiene activadas las notificaciones
    boolean aceptaNotificaciones();
    
}
