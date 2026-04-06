package modelo.interfaces;
public interface Consultable {
    //retorna un texto con los datos principales de la entidad
         String obtenerResumen();
         // Indica si la entidad está operativa
         boolean estaActivo();
         //Retorna una cadena que identifica el tipo concreto
         String obtenerTipo();
}
