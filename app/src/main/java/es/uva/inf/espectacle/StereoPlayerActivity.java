package es.uva.inf.espectacle;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.google.vrtoolkit.cardboard.CardboardActivity;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.cardboard.RajawaliCardboardView;

import es.uva.inf.espectacle.utils.StereoscopicRenderer;

/**
 * Actividad que se va a ejecutar cuando lanzemos el reproductor 360
 * la clase requiere ejecutarse en modo landscape. Para obtener la vista stereoscopica
 * hacemos un extend a cardboard activity. Basicamente tenemos que crear una vista
 * de tipo cardboard(Rajawalicardboardview) y asignarsela a nuestro renderizador,
 * para lo cual creamos un objeto StereoscopicRenderer al que pasamos el path del video
 * que se estaba reproduciendo en ese momento para que lo convierta a modo 360.
 * Ademas hemos añadido un listener para que cuando se toque la pantalla el video se pause
 * o continue su reproduccion.
 */
public class StereoPlayerActivity extends CardboardActivity {
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
