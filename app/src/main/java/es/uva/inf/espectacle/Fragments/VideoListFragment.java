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

import es.uva.inf.espectacle.Adapters.VideoAdapter;
import es.uva.inf.espectacle.Interfaces.ComunicationListener;
import es.uva.inf.espectacle.Modelo.Video;
import es.uva.inf.espectacle.R;
/**
 * Clase que modela el fragment de la lista de video
 */
public class VideoListFragment extends BaseListFragment {

    private RecyclerView mListView;
    private VideoAdapter mAdapter;
    private ComunicationListener mListener;

    public VideoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new VideoAdapter();
        mAdapter.setContext(getContext());
        mAdapter.setDatos(Video.getAllVideos(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        Button duracion_button = (Button) view.findViewById(R.id.filtro1);
        duracion_button.setOnClickListener(this);
        duracion_button.setText(R.string.duracion);
        Button calidad_button = (Button) view.findViewById(R.id.filtro2);
        calidad_button.setOnClickListener(this);
        calidad_button.setText(R.string.calidad);
        Button nombre_button = (Button) view.findViewById(R.id.filtro3);
        nombre_button.setText(R.string.nombre);
        nombre_button.setOnClickListener(this);

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
                //Ordenar por duracion
                Comparator<Video> OrderByDuracion = new Comparator<Video>() {
                    @Override
                    public int compare(Video lhs, Video rhs) {
                        Long another =((Video)lhs).getDuration() ;
                        Long other = ((Video)rhs).getDuration();
                        if(another>other){
                            return 1;
                        }if(another==other){
                            return 0;
                        }else{
                            return -1;
                        }
                    }
                };
                Collections.sort((List<Video>) mAdapter.getDatos(), OrderByDuracion);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro2).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);

                mAdapter.setPos_seleccionado(-1);
                mAdapter.setSeleccionado(null);

                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado interprete_button");
                break;
            case R.id.filtro2:
                //Ordenar por calidad
                Comparator<Video> OrderByCalidad = new Comparator<Video>() {
                    @Override
                    public int compare(Video lhs, Video rhs) {
                        String another =((Video)lhs).getResolution() ;
                        String other = ((Video)rhs).getResolution();
                        return another.compareTo(other);
                    }
                };
                Collections.sort((List<Video>) mAdapter.getDatos(), OrderByCalidad);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);

                mAdapter.setPos_seleccionado(-1);
                mAdapter.setSeleccionado(null);

                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado album_button");
                break;
            case R.id.filtro3:
                //Ordenar por nombre
                Comparator<Video> OrderByTitulo = new Comparator<Video>() {
                    @Override
                    public int compare(Video lhs, Video rhs) {
                        String another =((Video)lhs).getTittle() ;
                        String other = ((Video)rhs).getTittle();
                        return another.compareTo(other);
                    }
                };
                Collections.sort((List<Video>) mAdapter.getDatos(), OrderByTitulo);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro2).setActivated(false);

                mAdapter.setPos_seleccionado(-1);
                mAdapter.setSeleccionado(null);

                mAdapter.notifyDataSetChanged();
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
}