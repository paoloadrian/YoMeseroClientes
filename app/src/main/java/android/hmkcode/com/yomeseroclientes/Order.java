package android.hmkcode.com.yomeseroclientes;

import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HP on 26/07/2015.
 */
public class Order implements Serializable {
    public ArrayList<Item> items;
    public ArrayList<Integer> quantities;

    public Order(){
        items = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    public void getSelectedItemsInOrder(ArrayList<Item> items, ArrayList<Integer> quantities){
        for(int i=0;i<items.size();i++){
            if(quantities.get(i) > 0){
                this.items.add(items.get(i));
                this.quantities.add(quantities.get(i));
            }
        }
    }
}
