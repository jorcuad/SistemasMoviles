package es.uva.inf.espectacle.Modelo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import es.uva.inf.espectacle.Utils.DeviceFiles;

/**
 * Representación de un archivo de audio.
 */
public class Audio {

    private String id;
    private String artist;
    private String tittle;
    private String path;
    private String display_name;
    private Long duration;
    private String album;

    public Audio(String id, String artist, String tittle, String path, String display_name, Long duration, String album){
        this.setId(id);
        this.setArtist(artist);
        this.setTittle(tittle);
        this.setPath(path);
        this.setDisplay_name(display_name);
        this.setDuration(duration);
        this.setAlbum(album);
    }

    public static ArrayList<Audio> getAllAudios(Context context){
        return DeviceFiles.getAllAudios(context);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getTittle() {
        return tittle;
    }
    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getDisplay_name() {
        return display_name;
    }
    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString(){
       return display_name;
    }

    public String getStringDuration(){
        long second = (getDuration() / 1000) % 60;
        long minute = (getDuration() / (1000 * 60)) % 60;
        long hour = (getDuration() / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
