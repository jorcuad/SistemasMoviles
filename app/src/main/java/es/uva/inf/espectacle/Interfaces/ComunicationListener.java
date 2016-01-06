package es.uva.inf.espectacle.interfaces;

import java.util.ArrayList;

import es.uva.inf.espectacle.modelo.Audio;

/** Interfaz que comunica la lista con su fragment
 */
public interface ComunicationListener {
    void setMedia(Object media);
    void setAudioPos(int pos);
    void setAudio(ArrayList<Audio> audio);
    Object getMedia(int posicion);
}
