package es.uva.inf.espectacle.Services;

import android.app.Notification;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import es.uva.inf.espectacle.Modelo.Audio;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener{
    MediaPlayer player;
    ArrayList<Audio> audios;
    int songPos;
    Notification playing;
    Random r = new Random();
    private final IBinder musicBind = new MusicBinder();
    public MusicService() {
    }

    public void onCreate(){
        super.onCreate();
        songPos = 0;
        if(player==null) {
            player = new MediaPlayer();
            Log.d("MediaPlayer", "New player ");

        }
        initMediaPlayer();

        //create the service
    }
    public void setList(ArrayList<Audio> audios){
        this.audios = audios;
    }

    public void startForefround(){
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.support.design.R.drawable.notification_template_icon_bg)
                        .setContentTitle("Espectacle")
                        .setContentText("Playing...");
        playing = mBuilder.build();
        startForeground(1, playing);
    }

    public void stopForeground(){
        stopForeground(true);
    }



    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void initMediaPlayer(){
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }




    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    private void playSong(){

        //startForefround();
        player.reset();

        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                audios.get(songPos).getId());
        Log.d("Music Service","Song played: "+songPos);
        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();
    }

    public void pause(){
        if(player.isPlaying()){
            player.pause();
            stopForeground();
        }else{
            player.start();
            if(!player.isPlaying()) playSong();
            startForefround();
        }
    }

    public void next() {
        setNextSongPos();
        playSong();
    }

    public  void back(){
        setPrevSongPos();
        playSong();
    }

    public void shuffle(){
        setRandomSongPos();
        playSong();
    }

    private int setNextSongPos(){
        songPos++;
        if(songPos>=audios.size()) songPos = 0;
        return songPos;
    }

    private int setPrevSongPos(){
        songPos--;
        if(songPos<0) songPos = audios.size()-1;
        return songPos;
    }

    private int setRandomSongPos(){
        songPos = r.nextInt(audios.size());
        return songPos;
    }



    public void setSong(int songIndex){
        Log.d("Music Service", "Songindex: "+ songIndex);
        songPos = songIndex;
        Log.d("Music Service", "Songpos: "+ songPos);
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    public Audio getPlayingAudio(){
        return audios.get(songPos);
    }
}
