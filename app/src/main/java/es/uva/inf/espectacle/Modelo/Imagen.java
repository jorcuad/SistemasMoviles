package es.uva.inf.espectacle.Modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.uva.inf.espectacle.Utils.DeviceFiles;

/**
 * Representación de un archivo de imagen.
 */
public class Imagen implements Comparable {

    private String id;
    private String tittle;
    private String path;
    private String display_name;
    private Long dateAdded;
    private Long size;
    private Double title;

    public Imagen(String id, String tittle, String path, String display_name, Long dateAdded, Long size){
        this.setId(id);
        this.setTittle(tittle);
        this.setPath(path);
        this.setDisplay_name(display_name);
        this.setDateAdded(dateAdded);
        this.setSize(size);
    }

    public static ArrayList<Imagen> getAllImagenes(Context context){
        return DeviceFiles.getAllImagenes(context);
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
    public String getDateAdded() {
        return (new Date(dateAdded*1000)).toString();
    }
    public void setDateAdded(Long dateAdded) { this.dateAdded = dateAdded; }
    public String getSize(Context context) {
        return android.text.format.Formatter.formatShortFileSize(context, size);
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public Bitmap getThumbnail(){
        final int THUMBSIZE = 120;
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(getPath()), THUMBSIZE, THUMBSIZE);
    }

    @Override
    public int compareTo(Object another) {
        if(((Imagen)another).getTitle().compareTo(tittle)==1){
            return 1;
        }if(((Imagen)another).getTitle().equals(tittle)){
            return 0;
        }else{
            return -1;
        }
    }

    public String getTitle() {
        return this.tittle;
    }
}
