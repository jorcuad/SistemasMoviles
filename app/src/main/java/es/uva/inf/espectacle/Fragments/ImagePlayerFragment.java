package es.uva.inf.espectacle.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import es.uva.inf.espectacle.Interfaces.ComunicationListener;
import es.uva.inf.espectacle.Modelo.Imagen;
import es.uva.inf.espectacle.R;

public class ImagePlayerFragment extends Fragment {
    /**
     * Clase que modela el fragment del reproductor de imagenes
     */
    private ComunicationListener mListener;
    ImageView image;

    public ImagePlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_player, container, false);
        image = (ImageView) view.findViewById(R.id.imagePlayer);
        if(this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            image.setImageBitmap(Imagen.getBitmap(bundle.getString("path")));
        } else {
            image.setImageResource(R.drawable.side_nav_bar);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * Establece una imagen para la visualizacion
     * @param imagen La imagen a visualizar
     */
    public void setImage(Imagen imagen){
        image.setImageBitmap(Imagen.getBitmap(imagen.getPath()));
    }

}
