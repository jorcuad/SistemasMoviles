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
import android.widget.Button;

import java.util.ArrayList;

import es.uva.inf.espectacle.Interfaces.ComunicationListener;
import es.uva.inf.espectacle.Modelo.Audio;
import es.uva.inf.espectacle.R;
import es.uva.inf.espectacle.Services.MusicService;

public class AudioPlayerFragment extends Fragment implements View.OnClickListener {

    private MusicService musicSrv;
    private ArrayList<Audio> audioList;
    boolean musicBound = false;
    private Intent playIntent;

    private ComunicationListener mListener;
    Button buttonPlay, buttonNext, buttonPause;

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
        buttonPlay = (Button) view.findViewById(R.id.buttonPlayTest);
        buttonNext = (Button) view.findViewById(R.id.buttonNext);
        buttonPause = (Button) view.findViewById(R.id.buttonPause);
        buttonPlay.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
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

    public void onPlayTestButton(View view) {
        Log.d("Set song to", "9");
        musicSrv.setSong(9);
        musicSrv.playSong();
    }

    public void onClick(View v){
        if(v.getId()==R.id.buttonPlayTest){
            onPlayTestButton(v);
        }else if(v.getId()==R.id.buttonPause){
            onPauseButton(v);
        }else if(v.getId()==R.id.buttonNext){
            onNextButton(v);
        }
    }

    private void onPauseButton(View v) {
        musicSrv.pause();
    }

    private void onNextButton(View v) {
        musicSrv.next();
    }

    public void setPlayList(ArrayList<Audio> list){
        audioList = list;
        musicSrv.setList(audioList);
    }

    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(audioList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

}
