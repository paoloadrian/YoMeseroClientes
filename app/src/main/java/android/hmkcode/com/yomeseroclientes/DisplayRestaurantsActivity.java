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
import android.widget.ListView;

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


public class DisplayRestaurantsActivity extends ActionBarActivity {

    ListView restaurantsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_restaurants);

        restaurantsListView = (ListView) findViewById(R.id.restaurantsListView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#078673")));

        new HttpAsyncTask(this).execute("https://frozen-springs-8168.herokuapp.com/restaurants.json");
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

    public Boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected())
            return true;
        else
            return false;
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
                JSONArray json_restaurants = new JSONArray(result);
                ArrayList<Restaurant> restaurants = new ArrayList<>();
                Restaurant aux;
                for (int i = 0; i < json_restaurants.length(); i++) {
                    aux = new Restaurant();
                    aux.parseFromJson(json_restaurants.getJSONObject(i));
                    restaurants.add(aux);
                }

                RestaurantsArrayAdapter itemsArrayAdapter = new RestaurantsArrayAdapter(context,restaurants);
                restaurantsListView.setAdapter(itemsArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_restaurants, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_log_out){
            SaveSharedPreference.setUserName(DisplayRestaurantsActivity.this, "");
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
