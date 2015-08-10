package android.hmkcode.com.yomeseroclientes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ConfirmOrderActivity extends ActionBarActivity {
    private Context context;
    private Order new_order;
    private String[] res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        new_order = new Order();
        ArrayList<Item> items = (ArrayList<Item>) getIntent().getSerializableExtra("items");
        new_order.getSelectedItemsInOrder(items);
        String total = getIntent().getStringExtra("total");
        new_order.total = Float.parseFloat(total);
        ListView orderItemsListView = (ListView) findViewById(R.id.orderItemsListView);
        TextView totalView = (TextView) findViewById(R.id.totalOrder);
        totalView.setText("Total:   Bs. " + total);
        res = getIntent().getStringArrayExtra("qr");

        OrderArrayAdapter orderItemsArrayAdapter = new OrderArrayAdapter(this, new_order);
        orderItemsListView.setAdapter(orderItemsArrayAdapter);
    }

    public void goToGetPersonalData(View view){
        HttpAsyncTask task = new HttpAsyncTask();
        task.execute();
    }

    private class HttpAsyncTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params){
            InputStream inputStream = null;
            String result = "";
            String order_id;
            //aca empieza el codigo para guardar una orden
            String url = "https://yomeseroserver.herokuapp.com/create_pedido_json?consumo="+Float.toString(new_order.total)+
                    "&rest="+res[1]+"&mesa="+res[2];

            url = url.replaceAll(" ","%20");
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
                inputStream = httpResponse.getEntity().getContent();
            }catch(Exception e){
                Log.d("exception: ",e.toString());
                return false;
            }
            try {
                result = convertInputStreamToString(inputStream);
            }catch (Exception e){
                Log.d("exception: ",e.toString());
                return false;
            }
            try{
                JSONObject obj = new JSONObject(result);
                order_id = obj.getString("id");
                new_order.id = Integer.parseInt(order_id);
            }catch(Throwable t){
                Log.d("exception: ", t.toString());
                return false;
            }
            //aca empieza a guardar los items de la orden
            for (int i=0;i<new_order.items.size();i++) {
                url = "https://yomeseroserver.herokuapp.com/create_item_pedido_json?pedido=" + order_id + "&quantity="
                        + new_order.items.get(i).quantity + "&item=" + new_order.items.get(i).id;
                url = url.replaceAll(" ","%20");
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
                } catch (Exception e) {
                    Log.d("Exception: ", e.toString());
                    return  false;
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success){
            if (success){
                Intent intent = new Intent(getApplicationContext(), BillingActivity.class);
                intent.putExtra("order", new_order);
                startActivity(intent);
            }
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {
            SaveSharedPreference.setUserId(ConfirmOrderActivity.this,"");
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
