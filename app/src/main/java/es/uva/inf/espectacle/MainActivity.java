package es.uva.inf.espectacle;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import es.uva.inf.espectacle.Fragments.AudioListFragment;
import es.uva.inf.espectacle.Fragments.AudioPlayerFragment;
import es.uva.inf.espectacle.Fragments.BaseListFragment;
import es.uva.inf.espectacle.Fragments.ImageListFragment;
import es.uva.inf.espectacle.Fragments.ImagePlayerFragment;
import es.uva.inf.espectacle.Fragments.VideoListFragment;
import es.uva.inf.espectacle.Fragments.VideoPlayerFragment;
import es.uva.inf.espectacle.Interfaces.ComunicationListener;
import es.uva.inf.espectacle.Modelo.Audio;
import es.uva.inf.espectacle.Modelo.Imagen;

/**
 * Modela la actividad principal de la aplicacion
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ComunicationListener {
    ImagePlayerFragment imagen;
    AudioPlayerFragment audioFragment;
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
        String sFrom = getIntent().getStringExtra(STARTED_FROM);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String a = bundle.getString(STARTED_FROM);

            if(a!=null && a.equals(SFROM_MUSIC_NOTIFICATION)) musicFragment();
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
        getSupportActionBar().setTitle("Música");
        audioFragment = new AudioPlayerFragment();
        AudioListFragment fragment = new AudioListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentList, fragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, audioFragment).commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            musicFragment();
        } else if (id == R.id.nav_gallery) {
            getSupportActionBar().setTitle("Imágenes");
            ImageListFragment fragment = new ImageListFragment();
            imagen = new ImagePlayerFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentList, fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, imagen).commit();
            //setMedia(Imagen.getAllImagenes(getApplicationContext()).get(0));
        } else if (id == R.id.nav_slideshow) {
            getSupportActionBar().setTitle("Video");
            VideoListFragment fragment = new VideoListFragment();
            VideoPlayerFragment video = new VideoPlayerFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentList, fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, video).commit();
        } else if (id == R.id.nav_manage) {
            getSupportActionBar().setTitle("360º");
            BaseListFragment fragment = new BaseListFragment();
            VideoPlayerFragment video = new VideoPlayerFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentList, fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentDisplay, video).commit();
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

    @Override
    public void setAudio(ArrayList<Audio> audio){
        audioFragment.setPlayList(audio);
    }

    @Override
    public Object getMedia(int posicion) {
        return null;
    }
}
