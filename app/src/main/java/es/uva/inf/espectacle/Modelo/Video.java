package es.uva.inf.espectacle.Modelo;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import es.uva.inf.espectacle.Utils.DeviceFiles;

/**
 * Representación de un archivo de video.
 */
public class Video {

    private Long id;
    private String tittle;
    private String path;
    private String resolution;
    private Long duration;

    public Video(Long id, String tittle, String path, String resolution, Long duration){
        this.setId(id);
        this.setTittle(tittle);
        this.setPath(path);
        this.setResolution(resolution);
        this.setDuration(duration);
    }

    public static ArrayList<Video> getAllVideos(Context context){
        return DeviceFiles.getAllVideos(context);
    }

    public static Bitmap getThumbnail(Context context, Long id){
        return DeviceFiles.getThumbnail(context, id);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public String getResolution() {
        return resolution;
    }
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getStringDuration(){


        long second = (getDuration() / 1000) % 60;
        long minute = (getDuration() / (1000 * 60)) % 60;
        long hour = (getDuration() / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hour, minute, second);


    }
}
