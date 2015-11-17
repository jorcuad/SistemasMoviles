package es.uva.inf.espectacle.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import es.uva.inf.espectacle.R;

public class MediaHolder extends RecyclerView.ViewHolder {
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
