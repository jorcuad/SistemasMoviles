package es.uva.inf.espectacle.Modelo;

import android.content.Context;

import java.util.List;

import es.uva.inf.espectacle.Utils.DeviceFiles;

/**
 * Representación de un archivo de video.
 */
public class Video {

    private String id;
    private String tittle;
    private String path;
    private String display_name;
    private String duration;

    public Video(String id, String tittle, String path, String display_name, String duration){
        this.setId(id);
        this.setTittle(tittle);
        this.setPath(path);
        this.setDisplay_name(display_name);
        this.setDuration(duration);
    }

    public static List<Audio> getAllVideos(Context context){
        return DeviceFiles.getAllAudios(context);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
