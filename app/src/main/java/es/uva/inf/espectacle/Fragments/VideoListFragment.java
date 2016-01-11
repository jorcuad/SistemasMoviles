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
import java.util.Objects;

import es.uva.inf.espectacle.adapters.VideoAdapter;
import es.uva.inf.espectacle.interfaces.ComunicationListener;
import es.uva.inf.espectacle.modelo.Video;
import es.uva.inf.espectacle.R;
/**
 * Clase que modela el fragment de la lista de video
 */
public class VideoListFragment extends BaseListFragment {

    private VideoAdapter mAdapter;
    private ComunicationListener mListener;

    public VideoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new VideoAdapter(this);
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

        RecyclerView mListView = (RecyclerView) view.findViewById(android.R.id.list);
        if(mAdapter.getDatos().size() > 0 ) {
            //Ordenar por duracion
            Comparator<Video> OrderByDuracion = new Comparator<Video>() {
                @Override
                public int compare(Video lhs, Video rhs) {
                    Long another =(lhs).getDuration() ;
                    Long other = (rhs).getDuration();
                    if(another>other){
                        return 1;
                    }if(Objects.equals(another, other)){
                        return 0;
                    }else{
                        return -1;
                    }
                }
            };

            Collections.sort(mAdapter.getDatos(), OrderByDuracion);
            duracion_button.setActivated(true);

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
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.filtro1:
                //Ordenar por duracion
                Comparator<Video> OrderByDuracion = new Comparator<Video>() {
                    @Override
                    public int compare(Video lhs, Video rhs) {
                        Long another =(lhs).getDuration() ;
                        Long other = (rhs).getDuration();
                        if(another>other){
                            return 1;
                        }if(Objects.equals(another, other)){
                            return 0;
                        }else{
                            return -1;
                        }
                    }
                };

                Collections.sort(mAdapter.getDatos(), OrderByDuracion);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro2).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);

                if(mAdapter.getVideo_seleccionado() != null) {

                    int pos = mAdapter.getDatos().indexOf(mAdapter.getVideo_seleccionado());
                    RecyclerView mListView = (RecyclerView) getActivity().findViewById(android.R.id.list);
                    LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
                    lm.scrollToPositionWithOffset(pos, 0);
                }

                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado interprete_button");
                break;
            case R.id.filtro2:
                //Ordenar por calidad
                Comparator<Video> OrderByCalidad = new Comparator<Video>() {
                    @Override
                    public int compare(Video lhs, Video rhs) {
                        String another =(lhs).getResolution() ;
                        String other = (rhs).getResolution();
                        return another.compareTo(other);
                    }
                };

                Collections.sort(mAdapter.getDatos(), OrderByCalidad);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);

                if(mAdapter.getVideo_seleccionado() != null) {

                    int pos = mAdapter.getDatos().indexOf(mAdapter.getVideo_seleccionado());
                    RecyclerView mListView = (RecyclerView) getActivity().findViewById(android.R.id.list);
                    LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
                    lm.scrollToPositionWithOffset(pos, 0);
                }

                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado album_button");
                break;
            case R.id.filtro3:
                //Ordenar por nombre
                Comparator<Video> OrderByTitulo = new Comparator<Video>() {
                    @Override
                    public int compare(Video lhs, Video rhs) {
                        String another =(lhs).getTittle() ;
                        String other = (rhs).getTittle();
                        return another.compareTo(other);
                    }
                };

                Collections.sort(mAdapter.getDatos(), OrderByTitulo);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro2).setActivated(false);

                if(mAdapter.getVideo_seleccionado() != null) {

                    int pos = mAdapter.getDatos().indexOf(mAdapter.getVideo_seleccionado());
                    RecyclerView mListView = (RecyclerView) getActivity().findViewById(android.R.id.list);
                    LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
                    lm.scrollToPositionWithOffset(pos, 0);
                }

                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado cancion_button");
                break;
            default: Log.d("espectacle", "Yo no he sido");
        }
        getmListener().setVideo(mAdapter.getDatos());
    }

    public ComunicationListener getmListener() {
        return mListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mListener = (ComunicationListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}