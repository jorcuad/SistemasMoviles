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
 * Clase que modela el adaptador para la lista de videos
 */
public class VideoAdapter extends RecyclerView.Adapter<MediaHolder>{

    private ArrayList<Video> datos = new ArrayList<>();
    private Context context; //TODO meterlo con un bundle en el intent;
    private int pos_seleccionado;
    private MediaHolder seleccionado;

    public VideoAdapter() {
        this.pos_seleccionado = -1;
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
        //holder.subtitle.setVisibility(View.GONE); //Escondemos el subtitulo ya que en el video no nos interesa.
        holder.duration.setText(getDatos().get(position).getStringDuration());
        holder.imagen.setImageBitmap(Video.getThumbnail(getContext(), getDatos().get(position).getId())); //TODO refactor, obtenerlas de carpeta de app.

        if(getPos_seleccionado() == holder.getAdapterPosition() ) {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(0xff7280ce);
        } else {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(0xffffffff);
        }
        
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Rober ya puedes reproducir el item con la posicionint pos_anterior = getPos_seleccionado();
                int pos_anterior = getPos_seleccionado();
                MediaHolder anterior = getSeleccionado();

                setPos_seleccionado(holder.getAdapterPosition());
                setSeleccionado(holder);
                //Log.d("espectacle", Integer.toString(getPos_seleccionado()));
                v.findViewById(R.id.item_texts).setBackgroundColor(0xff7280ce);
                if(anterior != null) {
                    anterior.itemView.findViewById(R.id.item_texts).setBackgroundColor(0xffffffff);
                }
                Log.d("espectacle", "Seleccionado elemento de la lista: " + getDatos().get(position).getTittle());
            }
        });
    }

    public void setSeleccionado (MediaHolder seleccionado) {
        this.seleccionado = seleccionado;
    }
    public MediaHolder getSeleccionado () {
        return this.seleccionado;
    }
    public void setPos_seleccionado (int pos) {
        this.pos_seleccionado = pos;
        //Log.d("espectacle", Integer.toString(getPos_seleccionado()));
    }
    public int getPos_seleccionado () {
        return this.pos_seleccionado;
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
        this.notifyDataSetChanged();
    }

    /**
     * Retorna el contexto de la aplicacion
     * @return Contexto de la aplicacion
     */
    public Context getContext() {
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
