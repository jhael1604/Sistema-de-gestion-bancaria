package modelo.enums;
public enum EstadoTransaccion {
    PENDIENTE{
    @Override
    public boolean puedeTransaccion(EstadoTransaccion destino){
        return destino == PROCESANDO || destino == RECHAZADA;
    }
    },
    PROCESANDO{
        @Override
        public boolean puedeTransaccion(EstadoTransaccion destino){
            return destino == COMPLETADA || destino == RECHAZADA;
        }
    },
    COMPLETADA{
        public boolean puedeTransaccion(EstadoTransaccion destino){
            return destino == REVERTIDA;
        }
    },
    RECHAZADA{
        public boolean puedeTransaccion(EstadoTransaccion destino){
            return false;
        }
    },
    REVERTIDA{
        public boolean puedeTransaccion(EstadoTransaccion destino){
            return false;
        }
    };
    
    public abstract boolean puedeTransaccion(EstadoTransaccion destino);
}
