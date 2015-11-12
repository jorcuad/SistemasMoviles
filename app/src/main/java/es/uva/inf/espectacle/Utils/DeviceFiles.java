package es.uva.inf.espectacle.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import es.uva.inf.espectacle.Modelo.Audio;
import es.uva.inf.espectacle.Modelo.Imagen;
import es.uva.inf.espectacle.Modelo.Video;

/**
 * Utils class for retrieve media files from device.
 */
public class DeviceFiles {

    public static List<Audio> getAllAudios(Context context) {
        //Some audio may be explicitly marked as not being music
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        //TODO eliminar audios de whatsapp y otras app que no sean musica
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

        List<Audio> audios = new ArrayList<Audio>();

        if(cursor == null) { return audios; }

        while (cursor.moveToNext()) {
            audios.add(new Audio(cursor.getString(0),
                                 cursor.getString(1),
                                 cursor.getString(2),
                                 cursor.getString(3),
                                 cursor.getString(4),
                                 cursor.getString(5),
                                 cursor.getString(6)));
        }

        cursor.close();

        return audios;
    }

    public static List<Video> getAllVideos(Context context){
        //Some audio may be explicitly marked as not being music
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        //TODO eliminar audios de whatsapp y otras app que no sean musica
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION
        };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Audio.Media.DISPLAY_NAME + " ASC");

        List<Video> videos = new ArrayList<Video>();

        if(cursor == null) { return videos; }

        while (cursor.moveToNext()) {
            videos.add(new Video(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)));
        }

        cursor.close();

        return videos;
    }

    public static List<Imagen> getAllImagenes(Context context){
        //Some audio may be explicitly marked as not being music
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        //TODO eliminar audios de whatsapp y otras app que no sean musica
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED
        };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Audio.Media.DISPLAY_NAME + " ASC");

        List<Imagen> imagenes = new ArrayList<Imagen>();

        if(cursor == null) { return imagenes; }

        while (cursor.moveToNext()) {
            imagenes.add(new Imagen(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)));
        }

        cursor.close();

        return imagenes;
    }
}
