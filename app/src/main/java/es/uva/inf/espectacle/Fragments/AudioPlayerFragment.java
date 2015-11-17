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

import es.uva.inf.espectacle.Modelo.Audio;
import es.uva.inf.espectacle.R;
import es.uva.inf.espectacle.Services.MusicService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AudioPlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AudioPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioPlayerFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MusicService musicSrv;
    private ArrayList<Audio> audioList;
    boolean musicBound = false;
    private Intent playIntent;

    private OnFragmentInteractionListener mListener;
    Button buttonPlay, buttonNext, buttonPause;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudioPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AudioPlayerFragment newInstance(String param1, String param2) {
        AudioPlayerFragment fragment = new AudioPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AudioPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

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
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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
