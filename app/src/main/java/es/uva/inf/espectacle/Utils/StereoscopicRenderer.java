package es.uva.inf.espectacle.utils;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.StreamingTexture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;

import java.io.File;

/**
 * Implementacion de un renderizador 3D para poder reproducir video en 360 grados.
 * Extendemos de la clase RajawaliCardboardRenderer para poder interactuar con el 3D
 * Esta clase genera una escena 3D con una camara dentro de una esfera en la que se esta
 * reproduciendo el video deseado.
 */
public class StereoscopicRenderer extends RajawaliCardboardRenderer {

    private MediaPlayer mMediaPlayer;
    private StreamingTexture mVideoTexture;
    private final String path;

    public StereoscopicRenderer(Context context, String path) {
        super(context);
        mContext = context;
        this.path = path;
    }

    /**
     * En el metodo que se ejecuta cuando se crea el renderizador creamos un mediaPlayer con el video a reproducir,
     * este mediaplayer lo transformamos en una textura con la que posteriormente creamos un material. Al tener el material
     * con la textura del video, se lo tenemos que acoplar a una esfera de manera que coloquemos la camara
     * dentro de la propia esfera y asi podamos ver el video. Para ello tenemos que establecer que la textura
     * se acople en modo espejo(que se pueda ver tambien por la cara interior de la esfera) e invertirla,
     * ya que al acoplarla en modo espejo la veriamos al reves. Una vez hecho esto ya solo nos queda crear
     * una escena y añadir nuestra esfera, para despues iniciar el mediaPlayer con el video.
     */
    @Override
    protected void initScene() {
        mMediaPlayer = MediaPlayer.create(getContext(), Uri.fromFile(new File(path))); //creamos un mediaPlayer con el video que queremos reproducir
        mVideoTexture = new StreamingTexture("video", mMediaPlayer); //convertimos el video en una textura
        Material material = new Material(); //creamos el material
        material.setColorInfluence(0); //hacemos que el material no tenga color
        try {
            material.addTexture(mVideoTexture); //intentamos acoplar la textura al material
        } catch (ATexture.TextureException e) {
            throw new RuntimeException(e);
        }
        Sphere sphere = new Sphere(50, 64, 32); //creamos una esfera con las medidas adecuadas
        sphere.setScaleX(-1); //invertimos la esfera para reflejar el video
        sphere.setMaterial(material); //seleccionamos nuestro material para la esfera
        getCurrentScene().addChild(sphere); //introducimos nuestra esfera en la escena(la view en 3D)
        getCurrentCamera().setPosition(Vector3.ZERO); //colocamos la camara en la posicion(0,0,0)
        getCurrentCamera().setFieldOfView(75); //establecemos el FOV adecuado para cardboard
        mMediaPlayer.start(); //iniciamos el video

    }

    /**
     * Este metodo se ejecuta en cada frame, es decir, se ejecuta muchas veces por segundo
     * para asi poder actualizar la pantalla. En nuestro caso la actualizacion de pantalla
     * es ir actualizandola con el video que se reproduce.
     * @param ellapsedRealtime tiempo transcurrido en realidad
     * @param deltaTime tiempo de ejecucion de los frames
     */
    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        mVideoTexture.update();
    }

    /**
     * Pausamos el reproductor
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null)
            mMediaPlayer.pause();
    }

    /**
     * Reanudamos el reproductor
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mMediaPlayer != null)
            mMediaPlayer.start();
    }

    /**
     * Cuando destruimos el renderizador liberamos los recursos
     * @param surfaceTexture textura de la pantalla
     */
    @Override
    public void onRenderSurfaceDestroyed(SurfaceTexture surfaceTexture) {
        super.onRenderSurfaceDestroyed(surfaceTexture);
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}

