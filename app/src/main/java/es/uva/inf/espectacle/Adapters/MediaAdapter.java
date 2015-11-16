package es.uva.inf.espectacle.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.uva.inf.espectacle.Modelo.Video;
import es.uva.inf.espectacle.R;

/**
 * Created by coke on 16/11/15.
 */
public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaHolder>{

    private ArrayList<Video> datos = new ArrayList<>();
    private Context context; //TODO meterlo con un bundle en el intent;

    @Override
    public MediaAdapter.MediaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MediaHolder(view);
    }

    @Override
    public void onBindViewHolder(MediaAdapter.MediaHolder holder, int position) {
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

    public static class MediaHolder extends RecyclerView.ViewHolder {
        FrameLayout listItem;
        TextView title;
        TextView subtitle;
        TextView duration;
        ImageView imagen;

        MediaHolder(View itemView) {
            super(itemView);
            listItem = (FrameLayout)itemView.findViewById(R.id.list_item);
            title = (TextView)itemView.findViewById(R.id.title);
            subtitle = (TextView)itemView.findViewById(R.id.subtitle);
            duration = (TextView)itemView.findViewById(R.id.duration);
            imagen = (ImageView)itemView.findViewById(R.id.imagen);
        }
    }

}
