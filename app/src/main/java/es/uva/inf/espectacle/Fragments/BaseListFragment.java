package es.uva.inf.espectacle.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import es.uva.inf.espectacle.R;

public class BaseListFragment extends Fragment implements OnClickListener {

    public BaseListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        RecyclerView mListView = (RecyclerView) view.findViewById(android.R.id.list);
        mListView.setVisibility(View.GONE);
        view.findViewById(R.id.emptyList).setVisibility(View.VISIBLE);
        ((TextView) view.findViewById(R.id.emptyList)).setText("No hay elementos que mostrar");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View v){
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
