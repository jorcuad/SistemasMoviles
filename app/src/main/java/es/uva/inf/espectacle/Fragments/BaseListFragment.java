package es.uva.inf.espectacle.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import es.uva.inf.espectacle.R;
/**
 * Clase que modela el fragment de la lista base, de la que heredan las listas especificas para audio, video e imagen
 */
public class BaseListFragment extends Fragment implements OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        RecyclerView mListView = (RecyclerView) view.findViewById(android.R.id.list);
        mListView.setVisibility(View.GONE);
        view.findViewById(R.id.emptyList).setVisibility(View.VISIBLE);
        ((TextView) view.findViewById(R.id.emptyList)).setText(R.string.no_hay_elementos);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
