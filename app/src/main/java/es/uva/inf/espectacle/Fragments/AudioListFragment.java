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

import es.uva.inf.espectacle.adapters.AudioAdapter;
import es.uva.inf.espectacle.interfaces.ComunicationListener;
import es.uva.inf.espectacle.modelo.Audio;
import es.uva.inf.espectacle.R;
/**
 * Clase que modela el fragment de la lista de audio
 */
public class AudioListFragment extends BaseListFragment {

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

        Button interprete_button = (Button) view.findViewById(R.id.filtro1);
        Button album_button = (Button) view.findViewById(R.id.filtro2);
        Button cancion_button = (Button) view.findViewById(R.id.filtro3);
        interprete_button.setOnClickListener(this);
        album_button.setOnClickListener(this);
        cancion_button.setOnClickListener(this);
        interprete_button.setText(R.string.interprete);
        album_button.setText(R.string.album);
        cancion_button.setText(R.string.cancion);

        RecyclerView mListView = (RecyclerView) view.findViewById(android.R.id.list);
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
            case R.id.filtro1:
                //Ordenar por intérprete
                Comparator<Audio> OrderByInterprete = new Comparator<Audio>() {
                    @Override
                    public int compare(Audio lhs, Audio rhs) {
                        String another =(lhs).getArtist() ;
                        String other = (rhs).getArtist();
                        return another.compareTo(other);
                    }
                };

                Collections.sort( mAdapter.getDatos(), OrderByInterprete);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro2).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);

                mAdapter.setPos_seleccionado(-1);
                mAdapter.setSeleccionado(null);
                mAdapter.notifyDataSetChanged();

                Log.d("espectacle", "Pulsado interprete_button");
                break;
            case R.id.filtro2:
                //Ordenar por album
                Comparator<Audio> OrderByAlbum = new Comparator<Audio>() {
                    @Override
                    public int compare(Audio lhs, Audio rhs) {
                        String another =(lhs).getAlbum() ;
                        String other = (rhs).getAlbum();
                        return another.compareTo(other);
                    }
                };

                Collections.sort( mAdapter.getDatos(), OrderByAlbum);
                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);

                mAdapter.setPos_seleccionado(-1);
                mAdapter.setSeleccionado(null);
                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado album_button");
                break;
            case R.id.filtro3:
                //Ordenar por cancion
                Comparator<Audio> OrderByTitulo = new Comparator<Audio>() {
                    @Override
                    public int compare(Audio lhs, Audio rhs) {
                        String another =(lhs).getTittle() ;
                        String other = (rhs).getTittle();
                        return another.compareTo(other);
                    }
                };

                Collections.sort( mAdapter.getDatos(), OrderByTitulo);
                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro2).setActivated(false);

                mAdapter.setPos_seleccionado(-1);
                mAdapter.setSeleccionado(null);
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
