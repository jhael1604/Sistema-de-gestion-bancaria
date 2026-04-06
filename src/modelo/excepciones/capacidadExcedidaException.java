package modelo.excepciones;

public class capacidadExcedidaException extends SistemaBancarioException {
    private int capacidadMaxima;
    public capacidadExcedidaException(String entidad, int capacidadMaxima) {
        super("Capacidad maxima alcanza para: " + entidad + ". Limite: " + capacidadMaxima, "ERR-CAP-001");
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
    @Override
    public String toString(){
        return super.toString() + "Capacidad maxima: " + capacidadMaxima;
    }
    
}
