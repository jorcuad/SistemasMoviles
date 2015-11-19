package es.uva.inf.espectacle.Fragments;

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

import es.uva.inf.espectacle.Adapters.VideoAdapter;
import es.uva.inf.espectacle.Interfaces.ComunicationListener;
import es.uva.inf.espectacle.Modelo.Video;
import es.uva.inf.espectacle.R;

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

        Button interprete_button = (Button) view.findViewById(R.id.interprete_button);
        interprete_button.setOnClickListener(this);
        interprete_button.setText("Duración");
        Button album_button = (Button) view.findViewById(R.id.album_button);
        album_button.setOnClickListener(this);
        album_button.setText("Calidad");
        Button cancion_button = (Button) view.findViewById(R.id.cancion_button);
        cancion_button.setText("Nombre");
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
            ((TextView) view.findViewById(R.id.emptyList)).setText("No hay elementos que mostrar");
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
}