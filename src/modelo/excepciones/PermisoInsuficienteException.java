package modelo.excepciones;

public class PermisoInsuficienteException extends BancoRuntimeException {
    
    public PermisoInsuficienteException(String accion, String rolActual) {
        super("Permiso insuficiente. La accion: " + accion + "no esta permitida para el rol: " + rolActual);
    }
    @Override
    public String toString() {
        return "Acceso Denegado: " + getMessage();
    }
}
