package es.uva.inf.espectacle.modelo;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import es.uva.inf.espectacle.utils.DeviceFiles;

/**
 * Clase que modela un archivo de video
 */
public class Video {

    private Long id;
    private String tittle;
    private String path;
    private String resolution;
    private Long duration;

    /**
     * Constructor del objeto Video, con varios datos sobre el archivo que modela
     * @param id del contenido multimedia.
     * @param tittle título de contenido multimedia.
     * @param path  dentro del dispositivo al contenido multimedia.
     * @param resolution resolución del contenido multimedia.
     * @param duration duración del contenido multimedia.
     */
    public Video(Long id, String tittle, String path, String resolution, Long duration){
        this.setId(id);
        this.setTittle(tittle);
        this.setPath(path);
        this.setResolution(resolution);
        this.setDuration(duration);
    }

    /**
     * Retorna la lista de videos encontrados en el dispositivo
     * @param context Contexto de la aplicacion
     * @return ArrayList como lista de videos
     */
    public static ArrayList<Video> getAllVideos(Context context){
        return DeviceFiles.getAllVideos(context);
    }

    /**
     * Retorna el thumbnail del video para previsualizacion
     * @param context Contexto de la aplicacion
     * @param id Id del video
     * @return Thumbnail
     */
    public static Bitmap getThumbnail(Context context, Long id){
        return DeviceFiles.getThumbnail(context, id);
    }

    public Long getId() {
        return id;
    }
    private void setId(Long id) {
        this.id = id;
    }
    public String getTittle() {
        return tittle;
    }
    private void setTittle(String tittle) {
        this.tittle = tittle;
    }
    public String getPath() {
        return path;
    }
    private void setPath(String path) {
        this.path = path;
    }
    public String getResolution() {
        return resolution;
    }
    private void setResolution(String resolution) {
        this.resolution = resolution;
    }
    public Long getDuration() {
        return duration;
    }
    private void setDuration(Long duration) {
        this.duration = duration;
    }

    /**
     * Devuelve como string la duracion del video
     * @return Duracion
     */
    public String getStringDuration(){
        long second = (getDuration() / 1000) % 60;
        long minute = (getDuration() / (1000 * 60)) % 60;
        long hour = (getDuration() / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
