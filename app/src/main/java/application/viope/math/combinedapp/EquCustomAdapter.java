package application.viope.math.combinedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EquCustomAdapter extends ArrayAdapter<String> {

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public void ListAdapter(ArrayList<String> list, Context context){
        this.list = list;
        this.context = context;
    }

    public EquCustomAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public EquCustomAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.equ_itemlistrow, null);
        }

        String p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.eq);

            if (tt1 != null) {
                tt1.setText(p.toString());
            }
        }
        return v;
    }
}