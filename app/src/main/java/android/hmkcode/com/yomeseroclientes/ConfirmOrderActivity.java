package android.hmkcode.com.yomeseroclientes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ConfirmOrderActivity extends ActionBarActivity {
    private Context context;
    private Order new_order;
    private ListView orderItemsListView;
    private OrderArrayAdapter orderItemsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        new_order = new Order();
        ArrayList<Item> items = (ArrayList<Item>) getIntent().getSerializableExtra("items");
        ArrayList<Integer> quantities = (ArrayList<Integer>) getIntent().getSerializableExtra("quantities");
        new_order.getSelectedItemsInOrder(items, quantities);

        orderItemsListView = (ListView) findViewById(R.id.orderItemsListView);
        TextView total = (TextView) findViewById(R.id.totalOrder);
        total.setText("Total:   Bs. " + getIntent().getStringExtra("total"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        orderItemsArrayAdapter = new OrderArrayAdapter(this, new_order);
        orderItemsListView.setAdapter(orderItemsArrayAdapter);
        context = this;
        orderItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ShowItemActivity.class);
                intent.putExtra("name", new_order.items.get(position).item_name);
                intent.putExtra("type", new_order.items.get(position).item_type);
                intent.putExtra("description", new_order.items.get(position).item_description);
                intent.putExtra("price", new_order.items.get(position).item_price);
                intent.putExtra("time", new_order.items.get(position).item_time);
                startActivity(intent);
            }
        });
    }

    public void goToGetPersonalData(View view){
        /*Intent intent = new Intent(getApplicationContext(), ConfirmOrderActivity.class);
        ArrayList<Integer> quantities = itemsArrayAdapter.quantities;
        intent.putExtra("items",items);
        intent.putExtra("quantities",quantities);
        intent.putExtra("total",totalTextView.getText().toString());
        startActivity(intent);*/
        Log.d("Confirmar","Si");
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
