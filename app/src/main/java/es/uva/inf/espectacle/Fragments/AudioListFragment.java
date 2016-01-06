package es.uva.inf.espectacle.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.uva.inf.espectacle.adapters.AudioAdapter;
import es.uva.inf.espectacle.interfaces.ComunicationListener;
import es.uva.inf.espectacle.modelo.Audio;
import es.uva.inf.espectacle.R;
/**
 * Clase que modela el fragment de la lista de audio
 */
public class AudioListFragment extends BaseListFragment {

    private RecyclerView mListView;
    private AudioAdapter mAdapter;
    private ComunicationListener mListener;

    public AudioListFragment() {
    }

    public ComunicationListener getmListener(){
        return mListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new AudioAdapter(this);
        mAdapter.setContext(getContext());
        mAdapter.setDatos(Audio.getAllAudios(getContext()));
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

        mListView = (RecyclerView) view.findViewById(android.R.id.list);
        if(mAdapter.getDatos().size() > 0 ) {
            mListView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            mListView.setLayoutManager(llm);
            mListView.setAdapter(mAdapter);
        } else {
            mListView.setVisibility(View.GONE);
            view.findViewById(R.id.emptyList).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.emptyList)).setText(R.string.no_hay_elementos);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mListener = (ComunicationListener) context;
        }

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.interprete_button:
                //Ordenar por itnérprete
                Comparator<Audio> OrderByInterprete = new Comparator<Audio>() {
                    @Override
                    public int compare(Audio lhs, Audio rhs) {
                        String another =((Audio)lhs).getArtist() ;
                        String other = ((Audio)rhs).getArtist();
                        return another.compareTo(other);
                    }
                };
                Collections.sort((List<Audio>) mAdapter.getDatos(), OrderByInterprete);
                mAdapter.notifyDataSetChanged();

                Log.d("espectacle", "Pulsado interprete_button");
                break;
            case R.id.album_button:
                //Ordenar por album
                Comparator<Audio> OrderByAlbum = new Comparator<Audio>() {
                    @Override
                    public int compare(Audio lhs, Audio rhs) {
                        String another =((Audio)lhs).getAlbum() ;
                        String other = ((Audio)rhs).getAlbum();
                        return another.compareTo(other);
                    }
                };
                Collections.sort((List<Audio>) mAdapter.getDatos(), OrderByAlbum);
                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado album_button");
                break;
            case R.id.cancion_button:
                //Ordenar por cancion
                Comparator<Audio> OrderByTitulo = new Comparator<Audio>() {
                    @Override
                    public int compare(Audio lhs, Audio rhs) {
                        String another =((Audio)lhs).getTittle() ;
                        String other = ((Audio)rhs).getTittle();
                        return another.compareTo(other);
                    }
                };
                Collections.sort((List<Audio>) mAdapter.getDatos(), OrderByTitulo);
                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado cancion_button");
                break;
            default: Log.d("espectacle", "Yo no he sido");
        }
        getmListener().setAudio(mAdapter.getDatos());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
