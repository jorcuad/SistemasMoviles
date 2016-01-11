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
 * Implementacion del fragment que contiene la lista de las audios del dispositivo
 */
public class AudioListFragment extends BaseListFragment {

    private AudioAdapter mAdapter;
    private ComunicationListener mListener;

    public AudioListFragment() {
    }

    /**
     * Retorna el listener para comunicación
     * @return Listener
     */
    public ComunicationListener getmListener(){
        return mListener;
    }

    /**
     * Creamos el adapter para asi obtener los datos de los archivos
     * @param savedInstanceState datos guardados
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new AudioAdapter(this);
        mAdapter.setContext(getContext());
        mAdapter.setDatos(Audio.getAllAudios(getContext()));
    }

    /**
     * al crear la vista creamos los botones y ordenamos por defecto los archivos
     * @param inflater layout inflater
     * @param container container de nuestro fragment
     * @param savedInstanceState datos guardados
     * @return view
     */
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
        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(mAdapter.getDatos().size() > 0 ) {

            //Ordenar por intérprete
            Comparator<Audio> OrderByInterprete = new Comparator<Audio>() {
                @Override
                public int compare(Audio lhs, Audio rhs) {
                    String another =(lhs).getArtist() ;
                    String other = (rhs).getArtist();
                    return another.compareTo(other);
                }
            };

            Collections.sort(mAdapter.getDatos(), OrderByInterprete);
            getmListener().setAudio(mAdapter.getDatos());

            interprete_button.setActivated(true);

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

    /**
     * obtenemos el listener para el evento de seleccionar un archivo
     * @param context this.context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mListener = (ComunicationListener) context;
        }

    }

    /**
     * Tratamos el evento onClick de los botones de la vista para ordenar los archivos segun los filtros,
     * ademas en este evento tambien tratamos la seleccion de un elemento de la lista que mandamos a traves
     * del listener al reproductor
     * @param v vista del fragment
     */
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

                Collections.sort(mAdapter.getDatos(), OrderByInterprete);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro2).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);

                if(mAdapter.getAudio_seleccionado() != null) {

                    int pos = mAdapter.getDatos().indexOf(mAdapter.getAudio_seleccionado());
                    RecyclerView mListView = (RecyclerView) getActivity().findViewById(android.R.id.list);
                    LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
                    lm.scrollToPositionWithOffset(pos, 1);
                }

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

                Collections.sort(mAdapter.getDatos(), OrderByAlbum);
                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);

                if(mAdapter.getAudio_seleccionado() != null) {

                    int pos = mAdapter.getDatos().indexOf(mAdapter.getAudio_seleccionado());
                    RecyclerView mListView = (RecyclerView) getActivity().findViewById(android.R.id.list);
                    LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
                    lm.scrollToPositionWithOffset(pos, 1);
                }

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

                Collections.sort(mAdapter.getDatos(), OrderByTitulo);
                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro2).setActivated(false);

                if(mAdapter.getAudio_seleccionado() != null) {

                    int pos = mAdapter.getDatos().indexOf(mAdapter.getAudio_seleccionado());
                    RecyclerView mListView = (RecyclerView) getActivity().findViewById(android.R.id.list);
                    LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
                    lm.scrollToPositionWithOffset(pos, 1);
                }

                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado cancion_button");
                break;
            default: Log.d("espectacle", "Yo no he sido");
        }
        getmListener().setAudio(mAdapter.getDatos());
    }

    /**
     * liberamos el listener cuando perdemos el foco
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public AudioAdapter getAdapter() {
        return this.mAdapter;
    }
}
