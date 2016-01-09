package es.uva.inf.espectacle.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.google.vrtoolkit.cardboard.CardboardActivity;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.cardboard.RajawaliCardboardView;

import es.uva.inf.espectacle.utils.StereoscopicRenderer;

/**
 * Created by Rober on 07/01/2016.
 */
public class StereoPlayer extends CardboardActivity {
    private boolean paused = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        String path = getIntent().getStringExtra("path");

        RajawaliCardboardView view = new RajawaliCardboardView(this);
        setContentView(view);
        setCardboardView(view);

        final RajawaliCardboardRenderer renderer = new StereoscopicRenderer(this, path); // your renderer
        view.setRenderer(renderer);        // required for CardboardView
        view.setSurfaceRenderer(renderer); // required for RajawaliSurfaceViez
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paused){
                    renderer.onResume();
                    paused = false;
                }else{
                    renderer.onPause();
                    paused = true;
                }
            }
        });
    }



}
