package es.uva.inf.espectacle.Fragments;

import android.os.Bundle;

import com.google.vrtoolkit.cardboard.CardboardActivity;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.cardboard.RajawaliCardboardView;

import es.uva.inf.espectacle.Utils.StereoscopicRenderer;

/**
 * Created by Rober on 07/01/2016.
 */
public class StereoPlayer extends CardboardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RajawaliCardboardView view = new RajawaliCardboardView(this);
        setContentView(view);
        setCardboardView(view);

        RajawaliCardboardRenderer renderer = new StereoscopicRenderer(this); // your renderer
        view.setRenderer(renderer);        // required for CardboardView
        view.setSurfaceRenderer(renderer); // required for RajawaliSurfaceView
        System.out.println("VRVRVRVRVRVRVRVRVRVRVRaldkfjasdkjfalksdfasdfasdfasdfasdfssssssssssssssss");
    }

}
