package es.uva.inf.espectacle.modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import es.uva.inf.espectacle.utils.DeviceFiles;

/**
 * Clase de representacion de un archivo de imagen
 */
public class Imagen implements Comparable {

    private String tittle;
    private String path;
    private String display_name;
    private Long dateAdded;
    private Long size;

    /**
     * Constructor del objeto Imagen, con varios datos acerca de la imagen modelada.
     * @param tittle título de contenido multimedia.
     * @param path  dentro del dispositivo al contenido multimedia.
     * @param display_name título del contenido legible por el usuario.
     * @param dateAdded fecha en la que se añadió al dispositivo el contenido multimedia.
     * @param size tamaño del contenido multimedia.
     */
    public Imagen(String tittle, String path, String display_name, Long dateAdded, Long size){
        this.setTittle(tittle);
        this.setPath(path);
        this.setDisplay_name(display_name);
        this.setDateAdded(dateAdded);
        this.setSize(size);
    }

    /**
     * Retorna la lista de imagenes encontradas en el dispositivo
     * @param context Contexto de la aplicacion
     * @return ArrayList como lista de imagenes
     */
    public static ArrayList<Imagen> getAllImagenes(Context context){
        return DeviceFiles.getAllImagenes(context);
    }
    private void setTittle(String tittle) {
        this.tittle = tittle;
    }
    public String getTitle() {
        return this.tittle;
    }
    public String getPath() {
        return path;
    }
    private void setPath(String path) {
        this.path = path;
    }
    public String getDisplay_name() {
        return display_name;
    }
    private void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
    public String getDateAdded() {
        return (new Date(dateAdded*1000)).toString();
    }
    private void setDateAdded(Long dateAdded) { this.dateAdded = dateAdded; }
    public String getSize(Context context) {
        return android.text.format.Formatter.formatShortFileSize(context, size);
    }
    private void setSize(Long size) {
        this.size = size;
    }

    /**
     * Devuelve el thumbnail de previsualizacion de la imagen
     * @return Thumbnail
     */
    public Bitmap getThumbnail(){
        final int THUMBSIZE = 120;
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(getPath()), THUMBSIZE, THUMBSIZE);
    }

    @Override
    public int compareTo(@NonNull Object another) {
        if(((Imagen)another).getTitle().compareTo(tittle)==1){
            return 1;
        }if(((Imagen)another).getTitle().equals(tittle)){
            return 0;
        }else{
            return -1;
        }
    }

    /**
     * Devuelve el bitmap de la imagen
     * @param path dirección del contenido multimedia en el dispositivo.
     * @return Bitmap de la imagen.
     */
    public static Bitmap getBitmap(String path){
        File imgFile = new  File(path);
        return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
    }

    public Long getSize() {
        return size;
    }

    public Long getDateLong(){
        return dateAdded;
    }
}
