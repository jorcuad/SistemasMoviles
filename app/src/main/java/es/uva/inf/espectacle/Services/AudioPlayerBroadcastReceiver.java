package es.uva.inf.espectacle.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Boradcast receiver para el servicio de audio que nos permite controlar el reproductor desde el propio servicio
 */
public class AudioPlayerBroadcastReceiver extends BroadcastReceiver {

    private MusicService musicService;


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equalsIgnoreCase("es.uva.inf.espectacle.ACTION_PLAY")){
            // do your stuff to play action;
            if(musicService!=null){
                Log.d("BroadcastReceiver", "Play.");
                musicService.pauseNoForeground();
            }

        }else if(action.equalsIgnoreCase("es.uva.inf.espectacle.ACTION_NEXT")){
            musicService.next();
        }else if(action.equalsIgnoreCase("es.uva.inf.espectacle.ACTION_PREV")){
            musicService.back();
        }
    }

    public void setMusicService(MusicService musicService){
        this.musicService = musicService;
    }
}
