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

import es.uva.inf.espectacle.adapters.ImageAdapter;
import es.uva.inf.espectacle.interfaces.ComunicationListener;
import es.uva.inf.espectacle.modelo.Imagen;
import es.uva.inf.espectacle.R;
/**
 * Clase que modela el fragment de la lista de imagenes
 */
public class ImageListFragment extends BaseListFragment {

    private ImageAdapter mAdapter;
    private ComunicationListener mListener;

    public ImageListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ImageAdapter(this);
        mAdapter.setContext(getContext());
        mAdapter.setDatos(Imagen.getAllImagenes(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        Button fecha_button = (Button) view.findViewById(R.id.filtro1);
        fecha_button.setText(R.string.fecha);
        fecha_button.setOnClickListener(this);
        Button tamano_button = (Button) view.findViewById(R.id.filtro2);
        tamano_button.setText(R.string.tamano);
        tamano_button.setOnClickListener(this);
        Button definicion_button = (Button) view.findViewById(R.id.filtro3);
        definicion_button.setText(R.string.definicion);
        definicion_button.setOnClickListener(this);

        RecyclerView mListView = (RecyclerView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(mAdapter.getDatos().size() > 0 ) {

            //Ordenar por fecha
            Comparator<Imagen> OrderByFecha = new Comparator<Imagen>() {
                @Override
                public int compare(Imagen lhs, Imagen rhs) {
                    Long another = (lhs).getDateLong();
                    Long other = (rhs).getDateLong();
                    if(another>other){
                        return 1;
                    }if(Objects.equals(another, other)){
                        return 0;
                    }else{
                        return -1;
                    }
                }
            };

            Collections.sort(mAdapter.getDatos(), OrderByFecha);
            fecha_button.setActivated(true);


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
                //Ordenar por fecha
                Comparator<Imagen> OrderByFecha = new Comparator<Imagen>() {
                    @Override
                    public int compare(Imagen lhs, Imagen rhs) {
                        Long another = (lhs).getDateLong();
                        Long other = (rhs).getDateLong();
                        if(another>other){
                            return 1;
                        }if(Objects.equals(another, other)){
                            return 0;
                        }else{
                            return -1;
                        }
                    }
                };

                Collections.sort(mAdapter.getDatos(), OrderByFecha);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro2).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);
                if(mAdapter.getImg_seleccionada() != null) {

                    int pos = mAdapter.getDatos().indexOf(mAdapter.getImg_seleccionada());
                    RecyclerView mListView = (RecyclerView) getActivity().findViewById(android.R.id.list);
                    LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
                    lm.scrollToPositionWithOffset(pos, 1);
                }

                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado interprete_button");
                break;
            case R.id.filtro2:
                //Ordenar por tamano
                Comparator<Imagen> OrderByTamano = new Comparator<Imagen>() {
                    @Override
                    public int compare(Imagen lhs, Imagen rhs) {
                        Long another =(lhs).getSize() ;
                        Long other = (rhs).getSize();
                        if(another>other){
                            return 1;
                        }if(Objects.equals(another, other)){
                            return 0;
                        }else{
                            return -1;
                        }
                    }
                };

                Collections.sort(mAdapter.getDatos(), OrderByTamano);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);
                if(mAdapter.getImg_seleccionada() != null) {

                    int pos = mAdapter.getDatos().indexOf(mAdapter.getImg_seleccionada());
                    RecyclerView mListView = (RecyclerView) getActivity().findViewById(android.R.id.list);
                    LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
                    lm.scrollToPositionWithOffset(pos, 1);
                }

                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado album_button");
                break;
            case R.id.filtro3:
                //Ordenar por nombre
                Comparator<Imagen> OrderByTitulo = new Comparator<Imagen>() {
                    @Override
                    public int compare(Imagen lhs, Imagen rhs) {
                        String another =(lhs).getTitle() ;
                        String other = (rhs).getTitle();
                        return another.compareTo(other);
                    }
                };

                Collections.sort(mAdapter.getDatos(), OrderByTitulo);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro1).setActivated(false);
                getActivity().findViewById(R.id.filtro2).setActivated(false);
                if(mAdapter.getImg_seleccionada() != null) {

                    int pos = mAdapter.getDatos().indexOf(mAdapter.getImg_seleccionada());
                    RecyclerView mListView = (RecyclerView) getActivity().findViewById(android.R.id.list);
                    LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
                    lm.scrollToPositionWithOffset(pos, 1);
                }

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

    /**
     * Retorna el listener para comunicación
     * @return Listener
     */
    public ComunicationListener getmListener() {
        return mListener;
    }
}