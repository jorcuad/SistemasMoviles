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

/**
 * Clase que modela e implementa el servicio de reproducción de música
 */
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

    /**
     * Creación del servicio reproductor de musica
     */
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

    /**
     * Establece la lista de reproduccion de audios
     * @param audios ArrayList de audios a reproducir
     */
    public void setList(ArrayList<Audio> audios){
        this.audios = audios;
    }

    /**
     * Inicia la ejecucion en segundo plano
     */
    public void startForefround(){
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.support.design.R.drawable.notification_template_icon_bg)
                        .setContentTitle("Espectacle")
                        .setContentText("Playing...");
        playing = mBuilder.build();
        startForeground(1, playing);
    }

    /**
     * Detiene la reproduccion en segundo plano
     */
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

    /**
     * Reproduce pista de audio
     */
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

    /**
     * Pausa la reproduccion de una pista de audio
     */
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

    /**
     * Pasa a siguiente pista de audio
     */
    public void next() {
        setNextSongPos();
        playSong();
    }

    /**
     * Vuelve a la anterior pista de audio
     */
    public  void back(){
        setPrevSongPos();
        playSong();
    }

    /**
     * Reproduce pista aleatoria
     */
    public void shuffle(){
        setRandomSongPos();
        playSong();
    }

    /**
     * Establece la posicion de la siguiente pista de audio
     * @return Posicion
     */
    private int setNextSongPos(){
        songPos++;
        if(songPos>=audios.size()) songPos = 0;
        return songPos;
    }
    /**
     * Establece la posicion de la anterior pista de audio
     * @return Posicion
     */
    private int setPrevSongPos(){
        songPos--;
        if(songPos<0) songPos = audios.size()-1;
        return songPos;
    }
    /**
     * Establece la posicion de la pista de audio aleatoria
     * @return Posicion
     */
    private int setRandomSongPos(){
        songPos = r.nextInt(audios.size());
        return songPos;
    }


    /**
     * Establece la cancion a reproducir
     * @param songIndex Indice de la cancion
     */
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

    /**
     * Devuelve el audio en reproduccion
     * @return Audio
     */
    public Audio getPlayingAudio(){
        return audios.get(songPos);
    }
}
