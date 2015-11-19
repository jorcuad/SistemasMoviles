package es.uva.inf.espectacle.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.uva.inf.espectacle.Modelo.Video;
import es.uva.inf.espectacle.R;

/**
 * Created by coke on 16/11/15.
 */
public class VideoAdapter extends RecyclerView.Adapter<MediaHolder>{

    private ArrayList<Video> datos = new ArrayList<>();
    private Context context; //TODO meterlo con un bundle en el intent;
    private final OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO conseguir obtener la posición del elemento clickado
            Log.d("espectacle", "Seleccionado elemento de la lista: " + getDatos().toString());
        }
    };

    @Override
    public MediaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new MediaHolder(view);
    }

    @Override
    public void onBindViewHolder(MediaHolder holder, int position) {
        holder.title.setText(getDatos().get(position).getTittle());
        holder.subtitle.setText(getDatos().get(position).getResolution());
        //holder.subtitle.setVisibility(View.GONE); //Escondemos el subtitulo ya que en el video no nos interesa.
        holder.duration.setText(getDatos().get(position).getStringDuration());
        holder.imagen.setImageBitmap(Video.getThumbnail(getContext(), getDatos().get(position).getId())); //TODO refactor, obtenerlas de carpeta de app.

    }

    @Override
    public int getItemCount() {
        return getDatos().size();
    }

    public ArrayList<Video> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<Video> datos) {
        this.datos = datos;
        this.notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
