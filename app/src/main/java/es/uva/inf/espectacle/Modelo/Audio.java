package es.uva.inf.espectacle.modelo;

import android.content.Context;

import java.util.ArrayList;

import es.uva.inf.espectacle.utils.DeviceFiles;

/**
 * Clase de representacion de un archivo de audio
 */
public class Audio {

    private Long id;
    private String artist;
    private String tittle;
    private String path;
    private String display_name;
    private Long duration;
    private String album;

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    private String albumArt;

    /**
     * Constructor del objeto Audio, con varios datos acerca de la pista modelada
     * @param id del audio.
     * @param artist artista del contenido multimedia.
     * @param tittle título de contenido multimedia.
     * @param path  dentro del dispositivo al contenido multimedia.
     * @param display_name título del contenido legible por el usuario.
     * @param duration duración del contenido multimedia.
     * @param album album al que pertenece el contenido multimedia.
     */
    public Audio(Long id, String artist, String tittle, String path, String display_name, Long duration, String album){
        this.setId(id);
        this.setArtist(artist);
        this.setTittle(tittle);
        this.setPath(path);
        this.setDisplay_name(display_name);
        this.setDuration(duration);
        this.setAlbum(album);
    }


    public Audio(Long id, String artist, String tittle, String path, String display_name, Long duration, String album, String albumArt){
        this.setId(id);
        this.setArtist(artist);
        this.setTittle(tittle);
        this.setPath(path);
        this.setDisplay_name(display_name);
        this.setDuration(duration);
        this.setAlbum(album);
        this.setAlbumArt(albumArt);
    }

    /**
     * Retorna la lista de audios encontrados en el dispositivo
     * @param context Contexto de la aplicación
     * @return ArrayList como lista de los audios
     */
    public static ArrayList<Audio> getAllAudios(Context context){
        return DeviceFiles.getAllAudios(context);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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

    /**
     * Retorna la duracion de la pista de audio
     * @return Duracion como String
     */
    public String getStringDuration(){
        long second = (getDuration() / 1000) % 60;
        long minute = (getDuration() / (1000 * 60)) % 60;
        long hour = (getDuration() / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
