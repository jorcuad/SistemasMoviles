package es.uva.inf.espectacle.fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import es.uva.inf.espectacle.interfaces.ComunicationListener;
import es.uva.inf.espectacle.modelo.Audio;
import es.uva.inf.espectacle.R;
import es.uva.inf.espectacle.services.MusicService;
/**
 * Clase que modela el fragment del reproductor de audio
 */
public class AudioPlayerFragment extends Fragment implements View.OnClickListener {

    private MusicService musicSrv;
    private ArrayList<Audio> audioList;

    private ComunicationListener mListener;
    private TextView titleText;
    private boolean musicBound;
    private boolean isEmpty;

    public AudioPlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioList = Audio.getAllAudios(getContext());
        if (audioList != null) {
            if(audioList.size() > 0) {
                Intent playIntent = new Intent(getActivity(), MusicService.class);
                getActivity().bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
                getActivity().startService(playIntent);
                Log.d("OnCreateFragment:", "Arguments==null");
                isEmpty = false;
            } else {
                isEmpty = true;
            }
        } else {
            isEmpty = true;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_audio_player, container, false);
        if(!isEmpty) {
            ImageButton buttonPlay = (ImageButton) view.findViewById(R.id.buttonPlay);
            buttonPlay.setOnClickListener(this);
            buttonPlay.setImageResource(R.drawable.play_button_selector);
            ImageButton buttonNext = (ImageButton) view.findViewById(R.id.buttonNext);
            buttonNext.setOnClickListener(this);
            ImageButton buttonBack = (ImageButton) view.findViewById(R.id.buttonBack);
            buttonBack.setOnClickListener(this);
            ImageButton buttonShuffle = (ImageButton) view.findViewById(R.id.buttonShuffle);
            buttonShuffle.setOnClickListener(this);
            titleText = (TextView) view.findViewById(R.id.textTitle);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mListener = (ComunicationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //getActivity().stopService(playIntent);
        if(!isEmpty) getActivity().unbindService(musicConnection);
        musicSrv=null;
        mListener = null;
        Log.d("onDetach", "onDetach");
    }

    /**
     * Handler para el botón de reproducción de audio
     * @param view La vista del componente
     */
    public void onPlayButton(View view) {
        Log.d("Set song to", "9");
        //musicSrv.setSong(9);
        musicSrv.pause();
        updateInfo();

    }

    public void setAudioPos(int pos){
        musicSrv.playSongPos(pos);
        updateInfo();
    }



    /**
     * Handler para el click en el componente
     * @param v La vista del componente
     */
    public void onClick(View v){
        if(v.getId()==R.id.buttonPlay){
            onPlayButton(v);
        }else if(v.getId()==R.id.buttonNext){
            onNextButton(v);
        }else if(v.getId()==R.id.buttonBack){
            onBackButton(v);
        }else if(v.getId()==R.id.buttonShuffle){
            onShuffleButton(v);
        }
        updateInfo(); //TODO corregir para que la info se actualize automaticamente cuando cambia la cancion
    }

    /**
     * Handler para el boton de aleatorio
     * @param v La vista del componente
     */
    private void onShuffleButton(View v) {
        musicSrv.shuffle();
    }

    /**
     * Actualiza el titulo del audio
     */
    public void updateInfo(){
        titleText.setText(musicSrv.getPlayingAudio().getTittle());
        /*if(musicSrv.isPlaying()){
            buttonPlay.setImageResource(R.drawable.play_button_selector);
        }else{
            buttonPlay.setImageResource(R.drawable.pause_button_selector);
        }*/
        //musicSrv.getPlayingAudio();
    }
    /**
     * Handler para el boton de atras de audio
     * @param v La vista del componente
     */
    private void onBackButton(View v) {
        musicSrv.back();
    }
    /**
     * Handler para el boton de adelante de audio
     * @param v La vista del componente
     */
    private void onNextButton(View v) {
        musicSrv.next();
    }
    /**
     * Establece una lista de reproducción de audio
     * @param list ArrayList con la lista de audio
     */
    public void setPlayList(ArrayList<Audio> list){
        audioList = list;
        if(musicSrv!=null) musicSrv.setList(audioList);
    }
    /**
     * Conecta con el servicio de reproduccion de audio
     */
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            musicSrv = binder.getService();
            musicSrv.setList(audioList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

}
