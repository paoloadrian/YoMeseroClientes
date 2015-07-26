package android.hmkcode.com.yomeseroclientes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class DisplayMenuActivity extends ActionBarActivity {

    ListView itemsListView;
    ArrayList<Item> items;
    ItemsArrayAdapter itemsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        itemsListView = (ListView) findViewById(R.id.itemsListView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#078673")));

        new HttpAsyncTask(this).execute("https://frozen-springs-8168.herokuapp.com/items.json");
    }

    public void goToConfirmOrder(View view){
        Intent intent = new Intent(getApplicationContext(), ConfirmOrderActivity.class);
        ArrayList<Integer> quantities = itemsArrayAdapter.quantities;
        intent.putExtra("items",items);
        intent.putExtra("quantities",quantities);
        startActivity(intent);
    }

    public ArrayList<Integer> getQuantities(){
        ArrayList<Integer> quantities = new ArrayList<>();
        View item_view;
        TextView item_quantity;
        int aux;
        Log.d("Items",Integer.toString(itemsListView.getCount()));
        for (int i = 0;i < itemsListView.getCount(); i++){
            item_view = itemsListView.getChildAt(i);
            item_quantity = (TextView) item_view.findViewById(R.id.quantity);
            aux = Integer.parseInt(item_quantity.getText().toString());
            quantities.add(aux);
        }
        return quantities;
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream!=null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work";
        }catch (Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        String result = "";
        while ((line = bufferedReader.readLine())!=null)
            result+=line;
        inputStream.close();
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String,Void,String> {
        private Context context;
        public HttpAsyncTask(Context context){
            this.context = context;
        }
        @Override
        protected String doInBackground(String... urls){
            return GET(urls[0]);
        }

        protected void onPostExecute(String result){
            try {
                JSONArray json_items = new JSONArray(result);
                items = new ArrayList<>();
                Item aux;
                for (int i = 0; i < json_items.length(); i++) {
                    aux = new Item();
                    aux.parseFromJson(json_items.getJSONObject(i));
                    items.add(aux);
                }

                itemsArrayAdapter = new ItemsArrayAdapter(context,items);
                itemsListView.setAdapter(itemsArrayAdapter);
                itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(context, ShowItemActivity.class);
                        intent.putExtra("name", items.get(position).item_name);
                        intent.putExtra("type", items.get(position).item_type);
                        intent.putExtra("description", items.get(position).item_description);
                        intent.putExtra("price", items.get(position).item_price);
                        intent.putExtra("time", items.get(position).item_time);
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
