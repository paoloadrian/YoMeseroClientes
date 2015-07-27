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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView imageView = (ImageView) findViewById(R.id.iconRest);
        TextView name = (TextView) findViewById(R.id.item_name);
        TextView description = (TextView) findViewById(R.id.item_description);
        TextView type = (TextView) findViewById(R.id.item_type);
        TextView time = (TextView) findViewById(R.id.item_info);

        String type1 = getIntent().getStringExtra("type");
        float price = getIntent().getFloatExtra("price",0);

        name.setText(getIntent().getStringExtra("name"));
        description.setText(getIntent().getStringExtra("description"));
        type.setText("Tipo: " + type1);
        String last_line = "Precio: " + Float.toString(price);
        last_line = last_line + "   Tiempo: " + getIntent().getIntExtra("time",0);
        time.setText(last_line);

        String image = getIntent().getStringExtra("image");



        if (image.equals("no+image")){
            if (type1.equals("Comida")){
                if(price>20.0){
                    imageView.setImageResource(R.mipmap.food1);
                }
                else{
                    imageView.setImageResource(R.mipmap.food);
                }
            }
            if (type1.equals("Bebida")){
                if(price>20.0){
                    imageView.setImageResource(R.mipmap.drink1);
                }
                else{
                    imageView.setImageResource(R.mipmap.drink);
                }
            }
            if (type1.equals("Postre")){
                if(price>10.0){
                    imageView.setImageResource(R.mipmap.dessert1);
                }
                else{
                    imageView.setImageResource(R.mipmap.dessert);
                }
            }
        }
        else{
            byte[] decodedByte = Base64.decode(image, 0);
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
