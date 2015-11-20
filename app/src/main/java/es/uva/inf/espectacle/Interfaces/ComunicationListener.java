package es.uva.inf.espectacle.Interfaces;
/** Interfaz que comunica la lista con su fragment
 */
public interface ComunicationListener {
    void setMedia(Object media);
    void setAudioPos(int pos);
    Object getMedia(int posicion);
}
