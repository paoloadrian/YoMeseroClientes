package android.hmkcode.com.yomeseroclientes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
    private float total;
    private ArrayList<Item> items = new ArrayList<>();
    private TextView totalTextView;

    public ItemsArrayAdapter(Context context, ArrayList<Item> items, TextView totalTextView) {
        super(context, R.layout.item_list, items);
        this.context = context;
        this.items = items;
        total = 0;
        this.totalTextView = totalTextView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);
        TextView textView3 = (TextView) rowView.findViewById(R.id.thirdline);
        textView.setText(items.get(position).item_name);
        textView2.setText(items.get(position).item_type);
        textView3.setText(Float.toString(items.get(position).item_price));
        TextView quantity = (TextView)rowView.findViewById(R.id.quantity);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        Button plus = (Button) rowView.findViewById(R.id.plusbutton);
        Button minus = (Button) rowView.findViewById(R.id.minusbutton);
        ButtonClickListener listener = new ButtonClickListener(position, quantity);
        plus.setOnClickListener(listener);
        minus.setOnClickListener(listener);

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

        quantity.setText(Integer.toString(items.get(position).quantity));
        // change the icon for Windows and iPhone
        return rowView;
    }

    private class ButtonClickListener implements View.OnClickListener {
        private int position;
        private TextView textView;

        public ButtonClickListener(int position, TextView tv) {
            this.position = position;
            textView = tv;
        }

        @Override
        public void onClick(View v) {
            int quantity = Integer.parseInt(textView.getText().toString());
            if (v.getId() == R.id.plusbutton) {
                items.get(position).quantity += 1;
                textView.setText(Integer.toString(quantity + 1));
                total += items.get(position).item_price;
                totalTextView.setText(Float.toString(total));
            }
            else {
                if(quantity > 0) {
                    textView.setText(Integer.toString(quantity - 1));
                    items.get(position).quantity -= 1;
                    total -= items.get(position).item_price;
                    totalTextView.setText(Float.toString(total));
                }
            }
        }
    }
}
