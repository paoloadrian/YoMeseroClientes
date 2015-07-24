package android.hmkcode.com.yomeseroclientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alex on 4/11/15.
 */
public class ItemsArrayAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private ArrayList<Item> items;

    public ItemsArrayAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.item_list, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            names.add("Nombre: "+items.get(i).item_name);
            descriptions.add("Tipo: "+items.get(i).item_type+" - Precio: Bs. "+items.get(i).item_price);
        }
        textView.setText(names.get(position));
        textView2.setText(descriptions.get(position));
        // change the icon for Windows and iPhone
        return rowView;
    }
}
