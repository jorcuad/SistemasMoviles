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
 * Fragmento del reproductor de audio. Se trata de un fragment que va asignado  a un music service
 * para poder reproducir audio. La lista de audios la obtiene en su creacion y mediante un CommunicationListener le
 * pasamos el audio que selecciona el usuario para su reproduccion.
 */
public class AudioPlayerFragment extends Fragment implements View.OnClickListener {

    private MusicService musicSrv;
    private ArrayList<Audio> audioList;

    private ComunicationListener mListener;
    private TextView titleText;
    private boolean musicBound;
    private ImageButton buttonPlay;
    private final AudioPlayerFragment thisFragment = this;
    private boolean isEmpty;

    public AudioPlayerFragment() {
    }

    /**
     * Al crear el reproductor establecemos su lista de reproduccion y creamos el
     * servicio de musica que bindeamos con un intent
     * @param savedInstanceState datos guardados previamente
     */
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

    /**
     * Al crear la vista inflamos el fragment y establecemos los botones del reproductor
     * @param inflater inflater del layout
     * @param container container del fragment
     * @param savedInstanceState datos guardados
     * @return vista del fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {






        View view = inflater.inflate(R.layout.fragment_audio_player, container, false);
        if(!isEmpty) {
            buttonPlay = (ImageButton) view.findViewById(R.id.buttonPlay);
            buttonPlay.setOnClickListener(this);
            buttonPlay.setImageResource(R.drawable.play_button_selector);
            ImageButton buttonNext = (ImageButton) view.findViewById(R.id.buttonNext);
            buttonNext.setOnClickListener(this);
            ImageButton buttonBack = (ImageButton) view.findViewById(R.id.buttonBack);
            buttonBack.setOnClickListener(this);
            ImageButton buttonShuffle = (ImageButton) view.findViewById(R.id.buttonShuffle);
            buttonShuffle.setOnClickListener(this);
            titleText = (TextView) view.findViewById(R.id.textTitle);
            updateInfo();
        }
        return view;
    }

    /**
     * Establecemos el listener para el evento de seleccionar item de la lista de reproduccion
     * @param context context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mListener = (ComunicationListener) context;
        }
    }

    /**
     * Liberamos el listener al perder el foco, ademas desconectamos el servicio
     */
    @Override
    public void onPause(){
        super.onPause();
        if(musicSrv.isPlaying()) musicSrv.startForefround();
        //unBindFragmentService();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //getActivity().stopService(playIntent);
        unBindFragmentService();
        Log.d("onDetach", "onDetach");
    }

    private void unBindFragmentService(){
        getActivity().unbindService(musicConnection);
        if(musicSrv!=null) musicSrv.unbindFragment();
        musicSrv=null;
        mListener = null;
    }

    /**
     * Handler para el botón de reproducción de audio
     * @param view La vista del componente
     */
    private void onPlayButton(View view) {
        Log.d("Set song to", "9");
        //musicSrv.setSong(9);
        musicSrv.pause();
        updateInfo();

    }

    /**
     * Setter para cambiar el path del audio que se debe ejecutar
     * @param pos posicion del audio en la lista
     */
    public void setAudioPos(int pos){
        if(musicSrv!=null) musicSrv.playSongPos(pos);
        updateInfo();
    }



    /**
     * Handler para el click en el fragment
     * @param v La vista del fragment
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
     * @param v La vista del fragment
     */
    private void onShuffleButton(View v) {
        musicSrv.shuffle();
    }

    /**
     * Actualiza el titulo del audio
     */

    public void updateInfo(){
        if(musicSrv==null) return;
        titleText.setText(musicSrv.getPlayingAudio().getTittle());
        if(!musicSrv.isPlaying()){
            buttonPlay.setImageResource(R.drawable.play_button_selector);
        }else{
            buttonPlay.setImageResource(R.drawable.pause_button_selector);
        }
    }
    /**
     * Handler para el boton de atras de audio
     * @param v La vista del fragment
     */
    private void onBackButton(View v) {
        musicSrv.back();
    }
    /**
     * Handler para el boton de adelante de audio
     * @param v La vista del fragment
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
     * Conexion y desconexion con el servicio de reproduccion de audio
     */
    private final ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            musicSrv = binder.getService();
            musicSrv.setList(audioList);
            musicSrv.setFragment(thisFragment);
            updateInfo();
            musicSrv.stopForeground();
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

}
