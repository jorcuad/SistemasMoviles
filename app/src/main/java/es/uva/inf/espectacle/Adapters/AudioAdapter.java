package es.uva.inf.espectacle.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.uva.inf.espectacle.fragments.AudioListFragment;
import es.uva.inf.espectacle.modelo.Audio;
import es.uva.inf.espectacle.R;

/**
 * Adaptador de las imagenes, nos permite obtener los datos pertenecientes a los archivos de tipo
 * audio para mostrarlos en la lista.
 */
public class AudioAdapter extends RecyclerView.Adapter<MediaHolder>{
    private ArrayList<Audio> datos = new ArrayList<>();
    private Context context; //TODO meterlo con un bundle en el intent;
    private final AudioListFragment fragment;
    private int pos_seleccionado;
    private MediaHolder seleccionado;
    private Audio audio_seleccionado;

    public AudioAdapter(AudioListFragment fragment){
        this.fragment=fragment;
    }

    /**
     * Cuando creamos el holder de la informacion devolvemos el mediaholder que tiene los datos del archivo
     * @param parent vista de la clase padre
     * @param viewType tipo de vista
     * @return mediaHolder del archivo
     */
    @Override
    public MediaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MediaHolder(view);
    }

    /**
     * Hacemos un bind de los datos del mediaHolder
     * @param holder mediaHolder del archivo
     * @param position posicion del archivo en la lista
     */
    @Override
    public void onBindViewHolder(final MediaHolder holder, final int position) {
        holder.title.setText(getDatos().get(position).getTittle());
        String subtitle = getDatos().get(position).getArtist()+ " - "+ getDatos().get(position).getAlbum();
        holder.subtitle.setText(subtitle);
        holder.duration.setText(getDatos().get(position).getStringDuration());
        holder.imagen.setImageResource(R.drawable.side_nav_bar);

        if( holder.getAdapterPosition() == getDatos().indexOf(getAudio_seleccionado()) ) {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
            setSeleccionado(holder);
        } else {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundLight));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Audio audio_anterior = getAudio_seleccionado();
                MediaHolder anterior = getSeleccionado();

                setAudio_seleccionado(getDatos().get(holder.getAdapterPosition()));
                setSeleccionado(holder);

                v.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
                if((audio_anterior != null) && (!audio_anterior.equals(audio_seleccionado))) {

                    anterior.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundLight));

                }

                fragment.getmListener().setAudioPos(position);

            }
        });
    }

    private void setSeleccionado(MediaHolder seleccionado) {
        this.seleccionado = seleccionado;
    }

    private MediaHolder getSeleccionado () {
        return this.seleccionado;
    }
    private void setAudio_seleccionado(Audio audio) {
        this.audio_seleccionado = audio;
    }

    private int getPos_seleccionado () {
        return this.pos_seleccionado;


    public Audio getAudio_seleccionado () {
        return this.audio_seleccionado;

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
        this.notifyDataSetChanged();
    }

    /**
     * Establece el contexto de la aplicacion
     * @param context Contexto de la aplicacion
     */
    public void setContext(Context context) {
        this.context = context;
    }
}
