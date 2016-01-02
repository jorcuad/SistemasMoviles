package es.uva.inf.espectacle.Fragments;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.StreamingTexture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;

import es.uva.inf.espectacle.R;

/**
 * Created by Rober on 01/01/2016.
 */
public class StereoscopicPlayer extends RajawaliCardboardRenderer{
    Context mContext;

    private MediaPlayer mMediaPlayer;
    private StreamingTexture mVideoTexture;

    public StereoscopicPlayer(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void initScene(String path, int savePos) {

        mMediaPlayer = MediaPlayer.create(getContext(),
                R.raw.video);
        //mMediaPlayer.setLooping(true);

        mVideoTexture = new StreamingTexture(path, mMediaPlayer);
        Material material = new Material();
        material.setColorInfluence(0);
        try {
            material.addTexture(mVideoTexture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        Sphere sphere = new Sphere(50, 64, 32);
        sphere.setScaleX(-1);
        sphere.setMaterial(material);

        getCurrentScene().addChild(sphere);

        getCurrentCamera().setPosition(Vector3.ZERO);

        getCurrentCamera().setFieldOfView(75);

        mMediaPlayer.seekTo(savePos);

        mMediaPlayer.start();

    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        mVideoTexture.update();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null)
            mMediaPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMediaPlayer != null)
            mMediaPlayer.start();
    }

    @Override
    public void onRenderSurfaceDestroyed(SurfaceTexture surfaceTexture) {
        super.onRenderSurfaceDestroyed(surfaceTexture);
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}

