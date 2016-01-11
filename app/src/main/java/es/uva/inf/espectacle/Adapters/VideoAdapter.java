package es.uva.inf.espectacle.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.uva.inf.espectacle.fragments.VideoListFragment;
import es.uva.inf.espectacle.modelo.Video;
import es.uva.inf.espectacle.R;

/**
 * Clase que modela el adaptador para la lista de videos
 */
public class VideoAdapter extends RecyclerView.Adapter<MediaHolder>{

    private ArrayList<Video> datos = new ArrayList<>();
    private Context context; //TODO meterlo con un bundle en el intent;
    private int pos_seleccionado;
    private final VideoListFragment fragment;
    private MediaHolder seleccionado;
    private Video video_seleccionado;

    public VideoAdapter(VideoListFragment fragment) {
        this.fragment=fragment;
    }
    @Override
    public MediaHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        return new MediaHolder(view);
    }

    @Override
    public void onBindViewHolder(final MediaHolder holder, final int position) {
        holder.title.setText(getDatos().get(position).getTittle());
        holder.subtitle.setText(getDatos().get(position).getResolution());
        holder.duration.setText(getDatos().get(position).getStringDuration());
        holder.imagen.setImageBitmap(Video.getThumbnail(getContext(), getDatos().get(position).getId())); //TODO refactor, obtenerlas de carpeta de app.

        if( holder.getAdapterPosition() == getDatos().indexOf(getVideo_seleccionado()) ) {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
            setSeleccionado(holder);
        } else {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundLight));
        }
        
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Rober ya puedes reproducir el item con la posicionint pos_anterior = getPos_seleccionado();
                Video video_anterior = getVideo_seleccionado();
                MediaHolder anterior = getSeleccionado();

                setVideo_seleccionado(getDatos().get(holder.getAdapterPosition()));
                setSeleccionado(holder);

                v.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
                if((video_anterior != null) && (!video_anterior.equals(video_seleccionado))) {
                    anterior.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundLight));
                }
                Log.d("espectacle", "Seleccionado elemento de la lista: " + getDatos().get(position).getTittle());
                fragment.getmListener().setVideoPos(position);
            }
        });
    }

    public void setSeleccionado (MediaHolder seleccionado) {
        this.seleccionado = seleccionado;
    }
    private MediaHolder getSeleccionado() {
        return this.seleccionado;
    }
    public void setVideo_seleccionado (Video video) {
        this.video_seleccionado= video;
    }

    public Video getVideo_seleccionado() {
        return this.video_seleccionado;
    }


    @Override
    public int getItemCount() {
        return getDatos().size();
    }

    /**
     * Retorna los datos de video en forma de ArrayList
     * @return ArrayList con los datos de video
     */
    public ArrayList<Video> getDatos() {
        return datos;
    }

    /**
     * Establece los datos de video en forma de ArrayList
     * @param datos Los datos de video
     */
    public void setDatos(ArrayList<Video> datos) {
        this.datos = datos;
        fragment.getmListener().setVideo(datos);
        this.notifyDataSetChanged();
    }

    /**
     * Retorna el contexto de la aplicacion
     * @return Contexto de la aplicacion
     */
    private Context getContext() {
        return context;
    }

    /**
     * Establece el contexto de la aplicacion
     * @param context Contexto de la aplicacion
     */
    public void setContext(Context context) {
        this.context = context;
    }

}
