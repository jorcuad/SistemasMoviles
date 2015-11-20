package es.uva.inf.espectacle.Fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
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

import es.uva.inf.espectacle.Interfaces.ComunicationListener;
import es.uva.inf.espectacle.Modelo.Audio;
import es.uva.inf.espectacle.R;
import es.uva.inf.espectacle.Services.MusicService;
/**
 * Clase que modela el fragment del reproductor de audio
 */
public class AudioPlayerFragment extends Fragment implements View.OnClickListener {

    private MusicService musicSrv;
    private ArrayList<Audio> audioList;
    boolean musicBound = false;
    private Intent playIntent;

    private ComunicationListener mListener;
    ImageButton buttonPlay, buttonNext, buttonBack, buttonShuffle;
    TextView titleText;

    public AudioPlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }else{
            audioList = new ArrayList<Audio>(Audio.getAllAudios(getContext()));
            playIntent = new Intent(getActivity(), MusicService.class);
            getActivity().bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            getActivity().startService(playIntent);
            Log.d("OnCreateFragment:", "Arguments==null");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio_player, container, false);
        buttonPlay = (ImageButton) view.findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);
        buttonPlay.setImageResource(R.drawable.play_button_selector);
        buttonNext = (ImageButton) view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this);
        buttonBack = (ImageButton) view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);
        buttonShuffle = (ImageButton) view.findViewById(R.id.buttonShuffle);
        buttonShuffle.setOnClickListener(this);
        titleText = (TextView) view.findViewById(R.id.textTitle);
        /*buttonNext = (Button) view.findViewById(R.id.buttonNext);
        buttonPause = (Button) view.findViewById(R.id.buttonPause);
        /*buttonNext.setOnClickListener(this);
        buttonPause.setOnClickListener(this);*/
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
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
        getActivity().unbindService(musicConnection);
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
        //musicSrv.getPlayingAudio();
    }
    /**
     * Handler para el boton de atras de audio
     * @param view La vista del componente
     */
    private void onBackButton(View v) {
        musicSrv.back();
    }
    /**
     * Handler para el boton de adelante de audio
     * @param view La vista del componente
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
        musicSrv.setList(audioList);
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
