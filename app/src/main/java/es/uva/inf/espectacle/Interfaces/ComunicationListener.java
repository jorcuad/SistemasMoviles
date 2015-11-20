package es.uva.inf.espectacle.Interfaces;
/** Interfaz que comunica la lista con su fragment
 */
public interface ComunicationListener {
    void setMedia(Object media);
    Object getMedia(int posicion);
}
