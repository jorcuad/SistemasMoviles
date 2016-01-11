package es.uva.inf.espectacle;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import es.uva.inf.espectacle.adapters.AudioAdapter;
import es.uva.inf.espectacle.adapters.MediaHolder;
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
 * Modela la actividad principal de la aplicacion
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ComunicationListener {
    private ImagePlayerFragment imagen;
    private AudioPlayerFragment audioFragment;
    private VideoPlayerFragment videoFragment;
    private AudioListFragment audioListFragment;
    public static final String STARTED_FROM = "started_from";
    public static final String SFROM_MUSIC_NOTIFICATION = "started_from_music";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        procesarIntent();
    }

    private void procesarIntent() {
        //String sFrom = getIntent().getStringExtra(STARTED_FROM);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String bund = bundle.getString(STARTED_FROM);
            if(bund != null && bund.equals(SFROM_MUSIC_NOTIFICATION)) musicFragment();
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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


    private void musicFragment(){
        ActionBar bar= getSupportActionBar();
        if(bar != null) bar.setTitle("Música");
        audioFragment = new AudioPlayerFragment();
        audioListFragment = new AudioListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentList, audioListFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, audioFragment).commit();
    }

    private void videoFragment(){
        ActionBar bar= getSupportActionBar();
        if(bar != null) bar.setTitle("Video");
        VideoListFragment fragment = new VideoListFragment();
        videoFragment = new VideoPlayerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentList, fragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, videoFragment).commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            musicFragment();
        } else if (id == R.id.nav_gallery) {
            ActionBar bar= getSupportActionBar();
            if(bar != null) bar.setTitle("Imágenes");
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

    @Override
    public void setMedia(Object media) {
        Imagen objImagen = (Imagen) media;
        Bundle bundle = new Bundle();
        bundle.putString("path", objImagen.getPath());
        imagen = new ImagePlayerFragment();
        imagen.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, imagen).commit();
    }

    @Override
    public void setAudioPos(int pos) {
        Log.d("SetAudioPos", " " + pos);
        audioFragment.setAudioPos(pos);

    }

    /**
     * Llamada para establecer la cancion seleccionada en la lista al utilizar controles de audio
     * @param pos Cancion a reproducir
     */
    public void setAudioSel (int pos) {
        AudioAdapter audioAdapter = audioListFragment.getAdapter();
        RecyclerView mListView = (RecyclerView) this.findViewById(android.R.id.list);
        audioAdapter.setAudioSel(pos, mListView);
    }

    @Override
    public void setAudio(ArrayList<Audio> audio){
        audioFragment.setPlayList(audio);
    }

    @Override
    public void setVideoPos(int pos) {
        Log.d("SetVideoPos", " " + pos);
        videoFragment.setVideoPos(pos);
    }

    @Override
    public void setVideo(ArrayList<Video> video){
        videoFragment.setPlayList(video);
    }
}
