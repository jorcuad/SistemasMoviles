package es.uva.inf.espectacle.interfaces;

import java.util.ArrayList;

import es.uva.inf.espectacle.modelo.Audio;
import es.uva.inf.espectacle.modelo.Video;

/**
 * Interfaz que comunica la lista con su fragment
 */
public interface ComunicationListener {
    void setMedia(Object media);
    void setAudioPos(int pos);
    void setAudioSel(int pos);
    void setAudio(ArrayList<Audio> audio);
    void setVideoPos(int position);
    void setVideo(ArrayList<Video> datos);
}
