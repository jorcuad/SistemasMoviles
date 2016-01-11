package es.uva.inf.espectacle.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Comparator;

import es.uva.inf.espectacle.fragments.ImageListFragment;
import es.uva.inf.espectacle.modelo.Imagen;
import es.uva.inf.espectacle.R;

/**
 * Adaptador de las imagenes, nos permite obtener los datos pertenecientes a los archivos de tipo
 * imagen para mostrarlos en la lista.
 */
public class ImageAdapter extends RecyclerView.Adapter<MediaHolder> implements Comparator{
    private ArrayList<Imagen> datos = new ArrayList<>();
    private Context context;
    private final ImageListFragment fragment;
    private MediaHolder seleccionado;
    private Imagen img_seleccionada;

    public ImageAdapter(ImageListFragment fragment){
        this.fragment = fragment;
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
        holder.title.setText(getDatos().get(position).getDisplay_name());
        holder.subtitle.setText(getDatos().get(position).getDateAdded());
        holder.duration.setText(getDatos().get(position).getSize(context));
        holder.imagen.setImageBitmap(getDatos().get(position).getThumbnail());


        if( holder.getAdapterPosition() == getDatos().indexOf(getImg_seleccionada()) ) {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
            setSeleccionado(holder);
        } else {
            holder.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundLight));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagen img_anterior = getImg_seleccionada();
                MediaHolder anterior = getSeleccionado();

                setImg_seleccionada(getDatos().get(holder.getAdapterPosition()));
                setSeleccionado(holder);

                v.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
                if((img_anterior != null) && (!img_anterior.equals(img_seleccionada))) {
                    anterior.itemView.findViewById(R.id.item_texts).setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundLight));

                }
                fragment.getmListener().setMedia(getDatos().get(position));
            }
        });
    }

    private void setSeleccionado(MediaHolder seleccionado) {
        this.seleccionado = seleccionado;
    }
    private MediaHolder getSeleccionado() {
        return this.seleccionado;
    }


    private void setImg_seleccionada(Imagen imagen) {
        this.img_seleccionada = imagen;
    }

    public Imagen getImg_seleccionada () {
        return this.img_seleccionada;
    }

    @Override
    public int getItemCount() {
        return getDatos().size();
    }

    /**
     * Retorna los datos de imagen en forma de ArrayList
     * @return ArrayList con los datos de imagen
     */
    public ArrayList<Imagen> getDatos() {
        return datos;
    }

    /**
     * Establece los datos de imagen en forma de ArrayList
     * @param datos Los datos de imagen
     */
    public void setDatos(ArrayList<Imagen> datos) {
        this.datos = datos;
        this.notifyDataSetChanged();
    }

    /**
     * Establece el contexto de la aplicacion
     * @param context Contexto de la aplicacion
     */
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int compare(Object lhs, Object rhs) {
        return 0;
    }
}
