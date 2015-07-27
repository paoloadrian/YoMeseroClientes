package android.hmkcode.com.yomeseroclientes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#078673")));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void registerUser(View view){
        EditText emailEditText = (EditText) findViewById(R.id.email_register);
        EditText passwordEditText = (EditText) findViewById(R.id.password_register);
        EditText password_confirmationEditText = (EditText) findViewById(R.id.password_confirmation_register);

        String email_validation = emailEditText.getText().toString();
        String password_validation = passwordEditText.getText().toString();
        String password_confirmation_validation = password_confirmationEditText.getText().toString();

        HttpAsyncTask task = new HttpAsyncTask();
        Log.d("ENTRO: SIIII", "entro");

        if (!email_validation.contains("@") || password_validation.length()<8 ||!password_validation.equals(password_confirmation_validation)){
            if (!email_validation.contains("@")) {
                emailEditText.setError("El correo no es correcto");
            }
            if (password_validation!=password_confirmation_validation) {
                password_confirmationEditText.setError("Las contrasenas no coinciden");
            }
            if(password_validation.length()<8){
                passwordEditText.setError("La contrasena es muy corta");
            }
            /*Toast toast=Toast.makeText(getApplicationContext(), "El formulario no fue llenado correctamente", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();*/
        }else {
            task.execute();
        }

    }

    private class HttpAsyncTask extends AsyncTask<Void,Void,Boolean>{
        String email;
        String password;
        String password_confirmation;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            EditText email_r = (EditText) findViewById(R.id.email_register);
            EditText password_r = (EditText) findViewById(R.id.password_register);
            EditText password_c_r = (EditText) findViewById(R.id.password_confirmation_register);
            email = email_r.getText().toString();
            password = password_r.getText().toString();
            password_confirmation = password_c_r.getText().toString();

        }

        @Override
        protected Boolean doInBackground(Void... params){

            String url = "https://frozen-springs-8168.herokuapp.com/register_user?email="+email+"&password="+password+"&password_confirmation="+password_confirmation;
            try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResponse = httpClient.execute(new HttpGet(url));

            }catch(Exception e){
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success){
            Log.d("Esta en post execute: ", "Siii");
            if (success){
                SaveSharedPreference.setUserName(RegisterActivity.this, email);
                Log.d("Usuario: ", email);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
    }

}
