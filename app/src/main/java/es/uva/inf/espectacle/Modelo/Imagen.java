package es.uva.inf.espectacle.Modelo;

import android.content.Context;

import java.util.List;

import es.uva.inf.espectacle.Utils.DeviceFiles;

/**
 * Representación de un archivo de imagen.
 */
public class Imagen {

    private String id;
    private String tittle;
    private String path;
    private String display_name;
    private String dateAdded;

    public Imagen(String id, String tittle, String path, String display_name, String dateAdded){
        this.setId(id);
        this.setTittle(tittle);
        this.setPath(path);
        this.setDisplay_name(display_name);
        this.setDateAdded(dateAdded);
    }

    public static List<Imagen> getAllImagenes(Context context){
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
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
