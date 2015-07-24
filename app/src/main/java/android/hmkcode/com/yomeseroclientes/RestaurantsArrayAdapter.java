package android.hmkcode.com.yomeseroclientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alex on 4/12/15.
 */
public class RestaurantsArrayAdapter extends ArrayAdapter<Restaurant>{
    private final Context context;
    private ArrayList<Restaurant> restaurants;

    public RestaurantsArrayAdapter(Context context, ArrayList<Restaurant> restaurants) {
        super(context, R.layout.restaurant_list, restaurants);
        this.context = context;
        this.restaurants = restaurants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.restaurant_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLineRest);
        TextView textView2 = (TextView) rowView.findViewById(R.id.secondLineRest);
        TextView textView3 = (TextView) rowView.findViewById(R.id.thirdLineRest);
        TextView textView4 = (TextView) rowView.findViewById(R.id.fourthLineRest);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> addresses = new ArrayList<>();
        ArrayList<String> phones = new ArrayList<>();
        for (int i = 0; i < restaurants.size(); i++) {
            names.add(restaurants.get(i).restaurant_name);
            types.add("Tipo: "+ restaurants.get(i).restaurant_type);
            addresses.add("Direccion: "+ restaurants.get(i).restaurant_address);
            phones.add("Telefono: "+ restaurants.get(i).restaurant_phone);
        }
        textView.setText(names.get(position));
        textView2.setText(types.get(position));
        textView3.setText(addresses.get(position));
        textView4.setText(phones.get(position));
        // change the icon for Windows and iPhone
        return rowView;
    }
}
