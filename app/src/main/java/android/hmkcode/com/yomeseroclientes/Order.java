package android.hmkcode.com.yomeseroclientes;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HP on 26/07/2015.
 */
public class Order implements Serializable {
    public ArrayList<Item> items;
    public ArrayList<Integer> quantities;
    public float total;
    public String state;
    public int id;
    public int rest;
    public int table;

    public Order(){
        items = new ArrayList<>();
        quantities = new ArrayList<>();
        total = 0;
        state = "Pendiente";
    }

    public void getSelectedItemsInOrder(ArrayList<Item> items, ArrayList<Integer> quantities){
        for(int i=0;i<items.size();i++){
            if(quantities.get(i) > 0){
                this.items.add(items.get(i));
                this.quantities.add(quantities.get(i));
            }
        }
    }

    public void parseFromJson(JSONObject json){
        try {
            id = Integer.parseInt(json.getString("id"));
            total = Integer.parseInt(json.getString("total"));
            rest = Integer.parseInt(json.getString("restaurant_id"));
            table = Integer.parseInt(json.getString("mesa_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
