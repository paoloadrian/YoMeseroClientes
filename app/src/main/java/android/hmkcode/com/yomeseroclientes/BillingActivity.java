package android.hmkcode.com.yomeseroclientes;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class BillingActivity extends ActionBarActivity {
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        order = (Order) getIntent().getSerializableExtra("order");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_facturation, menu);
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
            SaveSharedPreference.setUserId(BillingActivity.this,"");
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setBillingData(View view){
        EditText nameEditText = (EditText) findViewById(R.id.name);
        EditText nitEditText = (EditText) findViewById(R.id.nit);

        String name = nameEditText.getText().toString();
        String nit = nitEditText.getText().toString();
        String url = "https://yomeseroserver.herokuapp.com/billing_data?name="+ name +"&nit="+ nit +"&id="+ order.id;
        url = url.replaceAll(" ","%20");
        Log.d("url: ", url);

        if (name.replaceAll(" ","").isEmpty()){
            if (nit.length() == 0)
                confirmData(url);
            else
                nameEditText.setError("Debe llenar ambos datos o ninguno");
        }
        else {
            if ((nit.length() > 5))
                confirmData(url);
            else{
                if (nit.length() == 0)
                    nitEditText.setError("Debe llenar ambos datos o ninguno");
                else
                    nitEditText.setError("El NIT es demasiado corto");
            }
        }
    }

    private void confirmData(String url){
        HttpAsyncTask task = new HttpAsyncTask();
        task.execute(url);
        Intent intent = new Intent(getApplicationContext(), OrderView.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }

    private class HttpAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            HttpResponse response = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(urls[0]));
                response = client.execute(request);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
