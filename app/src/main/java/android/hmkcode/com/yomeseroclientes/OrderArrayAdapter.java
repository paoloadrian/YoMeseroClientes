package android.hmkcode.com.yomeseroclientes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 26/07/2015.
 */
public class OrderArrayAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private ArrayList<Item> items = new ArrayList<>();

    public OrderArrayAdapter(Context context, Order order) {
        super(context, R.layout.item_list, order.items);
        this.context = context;
        this.items = order.items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.order_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.orderItemName);
        TextView textView2 = (TextView) rowView.findViewById(R.id.itemQuantity);
        TextView textView3 = (TextView) rowView.findViewById(R.id.itemPrice);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        if (items.get(position).item_image.equals("no+image")){
            if (items.get(position).item_type.equals("Comida")){
                if(items.get(position).item_price>20){
                    imageView.setImageResource(R.mipmap.food1);
                }
                else{
                    imageView.setImageResource(R.mipmap.food);
                }
            }
            if (items.get(position).item_type.equals("Bebida")){
                if(items.get(position).item_price>20){
                    imageView.setImageResource(R.mipmap.drink1);
                }
                else{
                    imageView.setImageResource(R.mipmap.drink);
                }
            }
            if (items.get(position).item_type.equals("Postre")){
                if(items.get(position).item_price>10){
                    imageView.setImageResource(R.mipmap.dessert1);
                }
                else{
                    imageView.setImageResource(R.mipmap.dessert);
                }
            }
        }
        else{
            byte[] decodedByte = Base64.decode(items.get(position).item_image, 0);
            Bitmap bm = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            Drawable img_drawable = new BitmapDrawable(context.getResources(), bm);
            imageView.setImageDrawable(img_drawable);
        }

        textView3.setText(Float.toString(items.get(position).item_price));
        textView.setText(items.get(position).item_name);
        textView2.setText(Integer.toString(items.get(position).quantity));
        // change the icon for Windows and iPhone
        return rowView;
    }
}
