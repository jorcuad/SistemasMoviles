package es.uva.inf.espectacle.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import es.uva.inf.espectacle.services.MusicService;

/**
 * Created by alvaro on 09/01/2016.
 */
public class AudioPlayerBroadcastReceiver extends BroadcastReceiver {

    private MusicService musicService;
    public static final String ACTION_PLAY = "es.uva.inf.espectacle.ACTION_PLAY";
    public static final String ACTION_NEXT = "es.uva.inf.espectacle.ACTION_NEXT";
    public static final String ACTION_PREV = "es.uva.inf.espectacle.ACTION_PREV";


    @Override
    public void onReceive(Context context, Intent intent) {
        musicService = MusicService.instance;
        if(musicService==null)return;
        String action = intent.getAction();
        if(action.equalsIgnoreCase(AudioPlayerBroadcastReceiver.ACTION_PLAY)){
            if(musicService!=null){
                Log.d("BroadcastReceiver", "Play.");
                musicService.pauseNoForeground();
            }

        }else if(action.equalsIgnoreCase(AudioPlayerBroadcastReceiver.ACTION_NEXT)){
            musicService.next();
        }else if(action.equalsIgnoreCase(AudioPlayerBroadcastReceiver.ACTION_PREV)){
            musicService.back();
        }
    }

    public void setMusicService(MusicService musicService){
        this.musicService = musicService;
    }
}
