package es.uva.inf.espectacle.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import java.util.ArrayList;

import es.uva.inf.espectacle.modelo.Audio;
import es.uva.inf.espectacle.modelo.Imagen;
import es.uva.inf.espectacle.modelo.Video;

/**
 * Clase de utils para recuperar los archivos multimedia del dispositivo
 */
public class DeviceFiles {

    /**
     * getter para obtener las pistas de audio del dispositivo
     * @param context context de la app
     * @return lista de todos los audios en la memoria
     */
    public static ArrayList<Audio> getAllAudios(Context context) {
        //Some audio may be explicitly marked as not being music
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM
        };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Audio.Media.DISPLAY_NAME + " ASC");

        ArrayList<Audio> audios = new ArrayList<>();

        if(cursor == null) { return null; }

        while (cursor.moveToNext()) {
            audios.add(new Audio(cursor.getLong(0),
                                 cursor.getString(1),
                                 cursor.getString(2),
                                 cursor.getString(3),
                                 cursor.getString(4),
                                 cursor.getLong(5),
                                 cursor.getString(6)));
        }

        cursor.close();

        return audios;
    }
    /**
     * getter para obtener las pistas de video del dispositivo
     * @param context context de la app
     * @return lista de todos los videos en la memoria
     */
    public static ArrayList<Video> getAllVideos(Context context){

        String[] projection = {
                MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.DURATION
        };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Audio.Media.DISPLAY_NAME + " ASC");

        ArrayList<Video> videos = new ArrayList<>();

        if(cursor == null) { return null; }

        while (cursor.moveToNext()) {
            videos.add(new Video(cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getLong(4)));
        }

        cursor.close();

        return videos;
    }

    /**
     * getter para obtener las imagenes del dispositivo
     * @param context context de la app
     * @return lista de todos los archivos imagen en la memoria
     */
    public static ArrayList<Imagen> getAllImagenes(Context context){

        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.SIZE
        };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + " ASC");

        ArrayList<Imagen> imagenes = new ArrayList<>();

        if(cursor == null) { return null; }

        while (cursor.moveToNext()) {
            imagenes.add(new Imagen(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getLong(4),
                    cursor.getLong(5)));
        }

        cursor.close();

        return imagenes;
    }

    /**
     * getter para obtener el thumbnail de un video
     * @param context this.context
     * @param videoId id del video
     * @return thumbnail perteneciente al video
     */
    public static Bitmap getThumbnail(Context context, Long videoId){
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), videoId,
                                                        MediaStore.Video.Thumbnails.MICRO_KIND,
                                                        null );
    }
}
