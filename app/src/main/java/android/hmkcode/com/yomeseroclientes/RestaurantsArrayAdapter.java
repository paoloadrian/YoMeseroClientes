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
        textView.setText(restaurants.get(position).restaurant_name);
        textView2.setText(restaurants.get(position).restaurant_type);
        textView3.setText(restaurants.get(position).restaurant_address);
        textView4.setText(restaurants.get(position).restaurant_phone);
        // change the icon for Windows and iPhone
        return rowView;
    }
}
