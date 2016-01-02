package es.uva.inf.espectacle.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.uva.inf.espectacle.Modelo.Audio;
import es.uva.inf.espectacle.Modelo.Imagen;
import es.uva.inf.espectacle.Modelo.Video;

/**
 * Utils class for retrieve media files from device.
 */
public class DeviceFiles {

    public static ArrayList<Audio> getAllAudios(Context context) {
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

        ArrayList<Audio> audios = null;
        try{
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    null,
                    MediaStore.Audio.Media.DISPLAY_NAME + " ASC");

            audios = new ArrayList<Audio>();

            if(cursor == null) { return audios; }

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
        }catch(Exception e){
            Log.e("Audio", "No hay audios en el dispositivo");
        }
        return audios;
    }

    public static ArrayList<Video> getAllVideos(Context context){

        //TODO eliminar videos de whatsapp y otras app
        String[] projection = {
                MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.DURATION
        };
        ArrayList<Video> videos = null;

        try {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    MediaStore.Audio.Media.DISPLAY_NAME + " ASC");
            videos = new ArrayList<Video>();
            if (cursor == null) {
                return videos;
            }

            while (cursor.moveToNext()) {
                videos.add(new Video(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getLong(4)));
            }

            cursor.close();
        }catch(Exception e){
            Log.e("Video", "No hay videos en el dispositivo");
        }
        return videos;
    }

    public static ArrayList<Imagen> getAllImagenes(Context context){

        //TODO eliminar imagenes de whatsapp?
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.SIZE
        };

        ArrayList<Imagen> imagenes = null;

        try{
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    MediaStore.Images.Media.DISPLAY_NAME + " ASC");

            imagenes = new ArrayList<Imagen>();

            if(cursor == null) { return imagenes; }

            while (cursor.moveToNext()) {
                imagenes.add(new Imagen(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getLong(4),
                        cursor.getLong(5)));
            }

            cursor.close();
        }catch(Exception e){
            Log.e("Video", "No hay videos en el dispositivo");
        }
        return imagenes;
    }

    public static Bitmap getThumbnail(Context context, Long videoId){
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), videoId,
                                                        MediaStore.Video.Thumbnails.MICRO_KIND,
                                                        null );
    }
}
