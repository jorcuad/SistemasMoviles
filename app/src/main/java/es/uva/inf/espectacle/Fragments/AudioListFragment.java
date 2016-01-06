package es.uva.inf.espectacle.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import es.uva.inf.espectacle.Adapters.AudioAdapter;
import es.uva.inf.espectacle.Interfaces.ComunicationListener;
import es.uva.inf.espectacle.Modelo.Audio;
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

        Button interprete_button = (Button) view.findViewById(R.id.filtro1);

        interprete_button.setOnClickListener(this);
        interprete_button.setText(R.string.interprete);
        Button album_button = (Button) view.findViewById(R.id.filtro2);
        album_button.setOnClickListener(this);
        album_button.setText(R.string.album);
        Button cancion_button = (Button) view.findViewById(R.id.filtro3);
        cancion_button.setOnClickListener(this);
        cancion_button.setText(R.string.cancion);

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
            case R.id.filtro1:
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
                        String another =((Audio)lhs).getAlbum() ;
                        String other = ((Audio)rhs).getAlbum();
                        return another.compareTo(other);
                    }
                };
                Collections.sort((List<Audio>) mAdapter.getDatos(), OrderByAlbum);

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
                        String another =((Audio)lhs).getTittle() ;
                        String other = ((Audio)rhs).getTittle();
                        return another.compareTo(other);
                    }
                };
                Collections.sort((List<Audio>) mAdapter.getDatos(), OrderByTitulo);

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
