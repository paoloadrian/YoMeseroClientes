package android.hmkcode.com.yomeseroclientes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ShowItemActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        Item item = (Item) getIntent().getSerializableExtra("item");

        ImageView imageView = (ImageView) findViewById(R.id.iconRest);
        TextView name = (TextView) findViewById(R.id.item_name);
        TextView description = (TextView) findViewById(R.id.item_description);
        TextView type = (TextView) findViewById(R.id.item_type);
        TextView time = (TextView) findViewById(R.id.item_info);

        name.setText(item.item_name);
        description.setText(item.item_description);
        type.setText("Tipo: " + item.item_type);
        String last_line = "Precio: " + Float.toString(item.item_price);
        last_line = last_line + "   Tiempo: " + item.item_time;
        time.setText(last_line);

        if (item.item_image.equals("no+image")){
            if (item.item_type.equals("Comida")){
                if(item.item_time>20.0){
                    imageView.setImageResource(R.mipmap.food1);
                }
                else{
                    imageView.setImageResource(R.mipmap.food);
                }
            }
            if (item.item_type.equals("Bebida")){
                if(item.item_time>20.0){
                    imageView.setImageResource(R.mipmap.drink1);
                }
                else{
                    imageView.setImageResource(R.mipmap.drink);
                }
            }
            if (item.item_type.equals("Postre")){
                if(item.item_time>10.0){
                    imageView.setImageResource(R.mipmap.dessert1);
                }
                else{
                    imageView.setImageResource(R.mipmap.dessert);
                }
            }
        }
        else{
            byte[] decodedByte = Base64.decode(item.item_image, 0);
            Bitmap bm = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            Drawable img_drawable = new BitmapDrawable(this.getResources(), bm);
            imageView.setImageDrawable(img_drawable);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out){
            SaveSharedPreference.setUserId(ShowItemActivity.this,"");
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
