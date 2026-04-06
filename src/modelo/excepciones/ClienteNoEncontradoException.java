package modelo.excepciones;

public class ClienteNoEncontradoException extends SistemaBancarioException {
    private String idCliente;

    public ClienteNoEncontradoException(String idCliente) {
        super("No se encontro ningun cliente con el ID: " + idCliente, "ERR-CLIENTE-001");
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }
    
    @Override
    public String toString(){
        return super.toString() + "[ID BUSCANDO: " + idCliente +"]"; 
    }
    
}
