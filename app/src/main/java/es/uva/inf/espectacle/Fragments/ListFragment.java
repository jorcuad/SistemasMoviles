package es.uva.inf.espectacle.Fragments;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Collection;
import java.util.Collections;

import es.uva.inf.espectacle.Adapters.AudioAdapter;
import es.uva.inf.espectacle.Adapters.ImageAdapter;
import es.uva.inf.espectacle.Adapters.VideoAdapter;
import es.uva.inf.espectacle.Modelo.Audio;
import es.uva.inf.espectacle.Modelo.Imagen;
import es.uva.inf.espectacle.Modelo.Video;
import es.uva.inf.espectacle.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ListFragment extends Fragment implements
        NavigationView.OnNavigationItemSelectedListener, OnClickListener {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mListView;
    private ImageAdapter mAdapter;

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //obtain arguments del bundle del intent
        }

        //TODO obtener contenido en funcion de los argumentos obtenidos del bundle del if anterior

        // TODO: Change Adapter to display your content // este adapter funciona con un string, si la clase a representar tiene toString va solo
        mAdapter = new ImageAdapter();
        mAdapter.setContext(getContext());
        mAdapter.setDatos(Imagen.getAllImagenes(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        Button interprete_button = (Button) view.findViewById(R.id.interprete_button);
        interprete_button.setOnClickListener(this);
        Button album_button = (Button) view.findViewById(R.id.album_button);
        album_button.setOnClickListener(this);
        Button cancion_button = (Button) view.findViewById(R.id.cancion_button);
        cancion_button.setOnClickListener(this);

        // Set the adapter
        mListView = (RecyclerView) view.findViewById(android.R.id.list);
        mListView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mListView.setLayoutManager(llm);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.interprete_button:
                Collections.sort(Imagen.getAllImagenes(getContext()));
                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado interprete_button");
                break;
            case R.id.album_button:
                Log.d("espectacle", "Pulsado album_button");
                break;
            case R.id.cancion_button:
                Log.d("espectacle", "Pulsado cacnion_button");
                break;
            default: Log.d("espectacle", "Yo no he sido");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        /*View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }*/
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
