package android.hmkcode.com.yomeseroclientes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 26/07/2015.
 */
public class OrderArrayAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private ArrayList<Item> items = new ArrayList<>();
    public ArrayList<String> quantities = new ArrayList<>();

    public OrderArrayAdapter(Context context, Order order) {
        super(context, R.layout.item_list, order.items);
        this.context = context;
        this.items = order.items;
        for (int i = 0; i < order.quantities.size(); i++) {
            this.quantities.add("Cantidad: "+Integer.toString(order.quantities.get(i)));
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.order_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.orderItemName);
        TextView textView2 = (TextView) rowView.findViewById(R.id.itemQuantity);
        TextView textView3 = (TextView) rowView.findViewById(R.id.itemPrice);
        textView3.setText(Float.toString(items.get(position).item_price));
        textView.setText(items.get(position).item_name);
        textView2.setText(quantities.get(position));
        // change the icon for Windows and iPhone
        return rowView;
    }
}
