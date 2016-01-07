package es.uva.inf.espectacle.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import es.uva.inf.espectacle.interfaces.ComunicationListener;
import es.uva.inf.espectacle.modelo.Imagen;
import es.uva.inf.espectacle.R;

/**
 * Clase que modela el fragment del reproductor de imagenes
 */
public class ImagePlayerFragment extends Fragment {

    private ComunicationListener mListener;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_player, container, false);
        image = (ImageView) view.findViewById(R.id.imagePlayer);
        if(this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            image.setImageBitmap(Imagen.getBitmap(bundle.getString("path")));
        } else {
            //image.setImageResource(R.drawable.side_nav_bar);
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
