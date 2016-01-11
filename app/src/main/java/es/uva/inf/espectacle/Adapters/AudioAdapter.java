package es.uva.inf.espectacle.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.uva.inf.espectacle.fragments.AudioListFragment;
import es.uva.inf.espectacle.modelo.Audio;
import es.uva.inf.espectacle.R;

/**
 * Clase que modela el adaptador para la lista de audio
 */
public class AudioAdapter extends RecyclerView.Adapter<MediaHolder>{
    private ArrayList<Audio> datos = new ArrayList<>();
    private Context context;
    private final AudioListFragment fragment;
    private MediaHolder seleccionado;
    private Audio audio_seleccionado;

    public AudioAdapter(AudioListFragment fragment){
        this.fragment=fragment;
    }

    @Override
    public MediaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MediaHolder(view);
    }

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
    private MediaHolder getSeleccionado() {
        return this.seleccionado;
    }
    public void setAudio_seleccionado(Audio audio) {
        this.audio_seleccionado = audio;
        //Log.d("espectacle", Integer.toString(getPos_seleccionado()));
    }

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

    public void setAudioSel (int pos, RecyclerView mListView) {
        Audio audio = this.getDatos().get(pos);

        MediaHolder anterior = this.getSeleccionado();

        LinearLayoutManager lm = (LinearLayoutManager) mListView.getLayoutManager();
        MediaHolder nuevo = (MediaHolder) mListView.findViewHolderForAdapterPosition(pos);

        this.setSeleccionado(nuevo);
        this.setAudio_seleccionado(audio);

        if(anterior != null) {
            anterior.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.backgroundLight));
        }
        if(nuevo != null) {
            nuevo.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimaryLight));
        }
        lm.scrollToPositionWithOffset(pos, 1);
    }
}
