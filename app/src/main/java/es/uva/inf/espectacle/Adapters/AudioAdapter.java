package es.uva.inf.espectacle.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.uva.inf.espectacle.Fragments.AudioListFragment;
import es.uva.inf.espectacle.Modelo.Audio;
import es.uva.inf.espectacle.R;

/**
 * Clase que modela el adaptador para la lista de audio
 */
public class AudioAdapter extends RecyclerView.Adapter<MediaHolder>{
    private ArrayList<Audio> datos = new ArrayList<>();
    private Context context; //TODO meterlo con un bundle en el intent;
    private AudioListFragment fragment;
    private int pos_seleccionado;
    private MediaHolder seleccionado;
   /* private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO conseguir obtener la posición del elemento clickado
            Log.d("espectacle", "Seleccionado elemento de la lista: " + getDatos().toString());
        }
    };*/

    public AudioAdapter(AudioListFragment fragment){
        this.fragment=fragment;
        this.pos_seleccionado = -1;
    }

    @Override
    public MediaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        //view.setOnClickListener(mOnClickListener);
        return new MediaHolder(view);
    }

    @Override
    public void onBindViewHolder(final MediaHolder holder, final int position) {
        holder.title.setText(getDatos().get(position).getTittle());
        holder.subtitle.setText(getDatos().get(position).getArtist()+ " - "+ getDatos().get(position).getAlbum());
        //holder.subtitle.setVisibility(View.GONE); //Escondemos el subtitulo ya que en el video no nos interesa.
        holder.duration.setText(getDatos().get(position).getStringDuration());
        holder.imagen.setImageResource(R.drawable.side_nav_bar);

        if(getPos_seleccionado() == holder.getAdapterPosition() ) {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
        } else {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundLight));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos_anterior = getPos_seleccionado();
                MediaHolder anterior = getSeleccionado();

                setPos_seleccionado(holder.getAdapterPosition());
                setSeleccionado(holder);
                //Log.d("espectacle", Integer.toString(getPos_seleccionado()));
                v.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
                if(( anterior != null) && (anterior != holder)) {
                    anterior.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundLight));
                }
                //TODO Rober ya puedes reproducir el item con la posicion
                Log.d("espectacle", "Seleccionado elemento de la lista: " + getDatos().get(position).getDisplay_name()+ " pos: "+ position);
                fragment.getmListener().setAudioPos(position);
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
    }
    public int getPos_seleccionado () {
        return this.pos_seleccionado;
    }

    @Override
    public int getItemCount() {
        return getDatos().size();
    }

    /**
     * Retorna los datos de audio en forma de ArrayList
     * @return ArrayList con los datos de audio
     */
    public ArrayList<Audio> getDatos() {
        return datos;
    }

    /**
     * Establece los datos de audio en forma de ArrayList
     * @param datos Los datos de audio
     */
    public void setDatos(ArrayList<Audio> datos) {
        this.datos = datos;
        fragment.getmListener().setAudio(datos);
        Log.d("asdaflsdkjflaskdjf","DataSetChangeeeeeeeeeed");
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
