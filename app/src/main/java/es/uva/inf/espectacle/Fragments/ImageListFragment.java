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

import es.uva.inf.espectacle.Adapters.ImageAdapter;
import es.uva.inf.espectacle.Interfaces.ComunicationListener;
import es.uva.inf.espectacle.Modelo.Imagen;
import es.uva.inf.espectacle.R;
/**
 * Clase que modela el fragment de la lista de imagenes
 */
public class ImageListFragment extends BaseListFragment {

    private RecyclerView mListView;
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
                //Ordenar por fecha
                Comparator<Imagen> OrderByFecha = new Comparator<Imagen>() {
                    @Override
                    public int compare(Imagen lhs, Imagen rhs) {
                        Long another = ((Imagen) lhs).getDateLong();
                        Long other = ((Imagen) rhs).getDateLong();
                        if(another>other){
                            return 1;
                        }if(another==other){
                            return 0;
                        }else{
                            return -1;
                        }
                    }
                };
                Collections.sort((List<Imagen>) mAdapter.getDatos(), OrderByFecha);

                v.setActivated(true);
                getActivity().findViewById(R.id.filtro2).setActivated(false);
                getActivity().findViewById(R.id.filtro3).setActivated(false);

                mAdapter.setPos_seleccionado(-1);
                mAdapter.setSeleccionado(null);

                mAdapter.notifyDataSetChanged();
                Log.d("espectacle", "Pulsado interprete_button");
                break;
            case R.id.filtro2:
                //Ordenar por tamano
                Comparator<Imagen> OrderByTamano = new Comparator<Imagen>() {
                    @Override
                    public int compare(Imagen lhs, Imagen rhs) {
                        Long another =((Imagen)lhs).getSize() ;
                        Long other = ((Imagen)rhs).getSize();
                        if(another>other){
                            return 1;
                        }if(another==other){
                            return 0;
                        }else{
                            return -1;
                        }
                    }
                };
                Collections.sort((List<Imagen>) mAdapter.getDatos(), OrderByTamano);

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
                Comparator<Imagen> OrderByTitulo = new Comparator<Imagen>() {
                    @Override
                    public int compare(Imagen lhs, Imagen rhs) {
                        String another =((Imagen)lhs).getTitle() ;
                        String other = ((Imagen)rhs).getTitle();
                        return another.compareTo(other);
                    }
                };
                Collections.sort((List<Imagen>) mAdapter.getDatos(), OrderByTitulo);

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

    /**
     * Retorna el listener para comunicación
     * @return Listener
     */
    public ComunicationListener getmListener() {
        return mListener;
    }
}