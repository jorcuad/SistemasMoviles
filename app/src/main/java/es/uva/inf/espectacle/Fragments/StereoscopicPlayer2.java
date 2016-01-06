package es.uva.inf.espectacle.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.StreamingTexture;
import org.rajawali3d.primitives.Sphere;

import java.io.IOException;

import es.uva.inf.espectacle.R;

/**
 * Created by Rober on 06/01/2016.
 */
public class StereoscopicPlayer2 extends RajawaliCardboardRenderer {

    private MediaPlayer mediaPlayer;
    private StreamingTexture videoTexture;

    public StereoscopicPlayer2(Context context) {
        super(context);
        mContext = context;
    }
    @Override
    public void initScene() {

        // setup sphere
        Sphere sphere = new Sphere(1, 100, 100);
        sphere.setPosition(0, 0, 0);
        // invert the sphere normals
        // factor "1" is two small and result in rendering glitches
        sphere.setScaleX(100);
        sphere.setScaleY(100);
        sphere.setScaleZ(-100);

        //initiate MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);
        try {
            Uri videoUrl = Uri.parse("android.resource://" + getContext().getPackageName() + "/"
                    + R.raw.fredy);
            mediaPlayer.setDataSource(getContext(),Uri.parse(videoUrl));
            mediaPlayer.prepareAsync();    //prepare the player (asynchronous)
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start(); //start the player only when it is prepared
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create texture from media player video
        videoTexture = new StreamingTexture("video", mediaPlayer);

        // set material with video texture
        Material material = new Material();
        material.setColorInfluence(0f);
        try {
            material.addTexture(videoTexture);
        } catch (ATexture.TextureException e){
            throw new RuntimeException(e);
        }
        sphere.setMaterial(material);

        // add sphere to scene
        getCurrentScene().addChild(sphere);

        super.initScene();
    }
}
