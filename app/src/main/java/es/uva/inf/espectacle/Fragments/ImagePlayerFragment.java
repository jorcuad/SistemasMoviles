package es.uva.inf.espectacle.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import es.uva.inf.espectacle.R;
import es.uva.inf.espectacle.modelo.Imagen;

/**
 * Implementacion del visor de imagenes. En el recuperamos el bitmap
 * de cada archivo para su visualizacion
 */
public class ImagePlayerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_player, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.imagePlayer);
        if(this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            image.setImageBitmap(Imagen.getBitmap(bundle.getString("path")));
        }

        return view;
    }
}
