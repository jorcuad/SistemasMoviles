package es.uva.inf.espectacle.fragments;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import java.util.ArrayList;

import es.uva.inf.espectacle.R;
import es.uva.inf.espectacle.StereoPlayerActivity;
import es.uva.inf.espectacle.interfaces.ComunicationListener;
import es.uva.inf.espectacle.modelo.Video;

/**
 * Clase que modela el fragment del reproductor de video
 */
public class VideoPlayerFragment extends Fragment implements View.OnClickListener {

    //private boolean pause = false;
    private String path;
    private int savePos = 0;
    private ComunicationListener mListener;
    private VideoView video;
    private boolean isEmpty;
    private ArrayList<Video> videoList;

    public VideoPlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoList = Video.getAllVideos(getContext());
        if (videoList != null) {
            if(videoList.size() > 0) {
                path = videoList.get(0).getPath();
                isEmpty = false;
            } else isEmpty = true;

            Log.d("OnCreateFragment:", "Arguments==null");
        } else {
            isEmpty = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_player, container, false);
        Button vrButton = (Button) view.findViewById(R.id.VRButton);
        if(!isEmpty) {
            MediaController mediaController = new MediaController(this.getActivity());
            video = (VideoView) view.findViewById(R.id.surfaceView);
            DisplayMetrics dm = new DisplayMetrics();
            this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int height = dm.heightPixels;
            int width = dm.widthPixels;
            video.setMinimumWidth(width);
            video.setMinimumHeight(height);
            video.setMediaController(mediaController);
            mediaController.setAnchorView(video);
            vrButton.setOnClickListener(this);
            video.setVideoPath(path);
        } else {
            vrButton.setVisibility(View.GONE);
        }
        return view;
    }
// --Commented out by Inspection START (11/01/2016 1:18):
//    /**
//     * Handler para el boton de reproducir video
//     */
//    private void onPlayButton() {
//        try {
//            if(pause){
//                video.pause();
//            }else{
//                video.start();
//            }
//        } catch (Exception e) {
//            Log.d("ERROR" , e.getMessage());
//        }
//        pause = !pause;
//    }
// --Commented out by Inspection STOP (11/01/2016 1:18)



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
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        if (video != null) {
            savePos = video.getCurrentPosition();
            guardarEstado.putString("ruta", path);
            guardarEstado.putInt("posicion", savePos);
        }
    }

    @Override
    public void onActivityCreated(Bundle guardarEstado) {
        super.onActivityCreated(guardarEstado);
        if (guardarEstado != null) {
            // Restore last state for checked position.
            path = guardarEstado.getString("ruta");
            savePos = guardarEstado.getInt("posicion");
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.VRButton){
            onVrButton();
        }
        /*if(v.getId()==R.id.buttonPlay){
            onPlayButton();
        }else if(v.getId()==R.id.buttonNext){
            onNextButton();
        }else if(v.getId()==R.id.buttonBack){
            onBackButton();
        }*/
    }


    private void onVrButton(){
        Intent intent = new Intent(this.getContext(), StereoPlayerActivity.class);
        intent.putExtra("path",path);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        video.stopPlayback();
        video = null;
    }

    public void setVideoPos(int pos){
        path = videoList.get(pos).getPath();
        video.setVideoPath(path);
        //updateInfo();
    }

    public void setPlayList(ArrayList<Video> list){
        if(video!=null) {
            videoList = list;
        }
    }

}
