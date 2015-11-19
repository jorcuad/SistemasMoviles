package es.uva.inf.espectacle.Fragments;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import es.uva.inf.espectacle.Interfaces.ComunicationListener;
import es.uva.inf.espectacle.Modelo.Video;
import es.uva.inf.espectacle.R;

public class VideoPlayerFragment extends Fragment {
    //private OrientationEventListener mOrientationListener;
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private boolean pause = false;
    private boolean playing = false;
    private String path;
    private int savePos = 0;
    private ComunicationListener mListener;
    private Button bPlay;

    public VideoPlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_player, container, false);
        final VideoView video = (VideoView) view.findViewById(R.id.surfaceView);
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                video.requestFocus();
                //video.start();
            }
        });
        bPlay = (Button) view.findViewById(R.id.buttonPlay);
        bPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (video != null) {
                    if (pause) {
                        video.start();
                    } else {
                        if(playing){
                            playing = false;
                            pause = true;
                            video.pause();
                        }
                        playing = true;
                        bPlay.setText("Pause");
                        path = Video.getAllVideos(getContext()).get(0).getPath();
                        playVideo(video, path);
                    }
                }
            }
        });

        //path = Video.getAllVideos(getContext()).get(0).getPath();
        //video.setVideoURI(Uri.parse(path));//TODO query for the path

        /*mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            public Activity getActivity() {
                return this.getActivity();
            }

            @Override
            public void onOrientationChanged(int orientation) {
                if(orientation>=90 && orientation<180 || orientation>=270 && orientation<360 ) {
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
        };*/
        return view;
    }

    private void playVideo(VideoView video, String path) {
        try {
            pause = false;
            video.setVideoURI(Uri.parse(path));
            video.seekTo(savePos);
            video.requestFocus();
            video.start();
        } catch (Exception e) {
            Log.d("ERROR" , e.getMessage());
        }
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
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        if (mediaPlayer != null) {
            int pos = mediaPlayer.getCurrentPosition();
            guardarEstado.putString("ruta", path);
            guardarEstado.putInt("posicion", pos);
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

    //TODO implementar controles de pausa y resumen
/*
    @Override public void onPause() {
        super.onPause();
        if (mediaPlayer != null & !pause) {
            mediaPlayer.pause();
        }
    }
    @Override public void onResume() {
        super.onResume();
        if (mediaPlayer != null & !pause) {
            mediaPlayer.start();
        }
    }
*/
}
