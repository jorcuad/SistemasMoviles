package es.uva.inf.espectacle;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import es.uva.inf.espectacle.fragments.AudioListFragment;
import es.uva.inf.espectacle.fragments.AudioPlayerFragment;
import es.uva.inf.espectacle.fragments.ImageListFragment;
import es.uva.inf.espectacle.fragments.ImagePlayerFragment;
import es.uva.inf.espectacle.fragments.VideoListFragment;
import es.uva.inf.espectacle.fragments.VideoPlayerFragment;
import es.uva.inf.espectacle.interfaces.ComunicationListener;
import es.uva.inf.espectacle.modelo.Audio;
import es.uva.inf.espectacle.modelo.Imagen;
import es.uva.inf.espectacle.modelo.Video;

/**
 * Activity principal de nuestra app, es una activty con drawer menu para poder seleccionar las opciones de la app
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ComunicationListener {
    private static final String VIDEO_FRAGMENT = "video_fragment";
    private ImagePlayerFragment imagen;
    private AudioPlayerFragment audioFragment;
    private VideoPlayerFragment videoFragment;
    public static final String STARTED_FROM = "started_from";
    public static final String SFROM_MUSIC_NOTIFICATION = "started_from_music";
    private RelativeLayout portada;
    private LinearLayout contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        procesarIntent();

        portada = (RelativeLayout) findViewById(R.id.start_app);
        contenido = (LinearLayout) findViewById(R.id.contenido);
        ImageView startApp = (ImageView) findViewById(R.id.imagen_start_app);
        startApp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }

    /**
     * Tratamos el intent del servicio de musica
     */
    private void procesarIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String bund = bundle.getString(STARTED_FROM);
            if (bund != null && bund.equals(SFROM_MUSIC_NOTIFICATION)) musicFragment();
        }


    }

    /**
     * Tratamos el evento de pulsar el backButton, para cerrar el drawer menu
     * en caso de que estuviera abierto o salir de la app
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Creamos nuestro menu
     *
     * @param menu menu de la activity
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Tratamos el evento de seleccionar un item del menu
     *
     * @param item item del menu
     * @return item seleccionado
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Creamos el musicFragment con su lista y realizamos la transaccion
     */
    private void musicFragment() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.setTitle("Música");
        audioFragment = new AudioPlayerFragment();
        AudioListFragment fragment = new AudioListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentList, fragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, audioFragment).commit();
    }

    /**
     * Creamos el videoFragment con su lista y realizamos la transaccion
     */
    private void videoFragment() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.setTitle("Video");
        VideoListFragment fragment = new VideoListFragment();
        videoFragment = new VideoPlayerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentList, fragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, videoFragment, MainActivity.VIDEO_FRAGMENT).commit();
    }

    /**
     * Tratamos el evento de seleccionar un item del drawer menu
     *
     * @param item item del drawer menu
     * @return true
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        portada.setVisibility(View.GONE);
        contenido.setVisibility(View.VISIBLE);
        if (id == R.id.nav_camara) {
            musicFragment();
        } else if (id == R.id.nav_gallery) {
            ActionBar bar = getSupportActionBar();
            if (bar != null) bar.setTitle("Imágenes");
            ImageListFragment fragment = new ImageListFragment();
            imagen = new ImagePlayerFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentList, fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, imagen).commit();
            //setMedia(Imagen.getAllImagenes(getApplicationContext()).get(0));
        } else if (id == R.id.nav_slideshow) {
            videoFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * setter de una imagen para el reproductor de imagenes
     *
     * @param media imagen del gallery de la app
     */
    @Override
    public void setMedia(Object media) {
        Imagen objImagen = (Imagen) media;
        Bundle bundle = new Bundle();
        bundle.putString("path", objImagen.getPath());
        imagen = new ImagePlayerFragment();
        imagen.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, imagen).commit();
    }

    /**
     * setter para reproducir una pista de audio segun su posicion en la lista
     *
     * @param pos posicion de la pista de audio en la lista
     */
    @Override
    public void setAudioPos(int pos) {
        Log.d("SetAudioPos", " " + pos);
        audioFragment.setAudioPos(pos);

    }

    /**
     * setter de la lista de audios
     *
     * @param audio lista con las pistas de audio del reproductor
     */
    @Override
    public void setAudio(ArrayList<Audio> audio) {
        audioFragment.setPlayList(audio);
    }

    /**
     * setter para reproducir una pista de video segun su posicion en la lista
     *
     * @param pos posicion de la pista de video en la lista
     */
    @Override
    public void setVideoPos(int pos) {
        if(videoFragment != null){
            videoFragment.setVideoPos(pos);
        }else{
            videoFragment = (VideoPlayerFragment) getSupportFragmentManager().findFragmentByTag("VIDEO_FRAGMENT");
            videoFragment.setVideoPos(pos);
        }

    }

    /**
     * setter de la lista de videos
     *
     * @param video lista con las pistas de video del reproductor
     */
    @Override
    public void setVideo(ArrayList<Video> video) {
        if(videoFragment!=null){
            videoFragment.setPlayList(video);
        }else{
            videoFragment = (VideoPlayerFragment) getSupportFragmentManager().findFragmentByTag("VIDEO_FRAGMENT");
            videoFragment.setPlayList(video);
        }

    }
}