package android.hmkcode.com.yomeseroclientes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alex on 4/12/15.
 */
public class Restaurant {
    public String restaurant_name;
    public String restaurant_description;
    public String restaurant_type;
    public String restaurant_phone;
    public String restaurant_address;

    public String toJSON(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("restaurant_name", restaurant_name);
            jsonObject.put("restaurant_description", restaurant_description);
            jsonObject.put("restaurant_address", restaurant_address);
            jsonObject.put("restaurant_phone", restaurant_phone);
            jsonObject.put("restaurant_type", restaurant_type);
            return jsonObject.toString();
        }catch(Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return null;
    }
    public String fromJSON(String data){
        try{
            JSONObject jsonObject = new JSONObject(data);
            restaurant_name = jsonObject.getString("restaurant_name");
            restaurant_description = jsonObject.getString("restaurant_description");
            restaurant_type = jsonObject.getString("restaurant_type");
            restaurant_phone = jsonObject.getString("restaurant_phone");
            restaurant_address = jsonObject.getString("restaurant_address");
            return show();
        }catch(Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
            return e.getLocalizedMessage();
        }
    }

    public void parseFromJson(JSONObject json){
        try {
            restaurant_name = json.getString("restaurant_name");
            restaurant_description = json.getString("restaurant_description");
            restaurant_type = json.getString("restaurant_type");
            restaurant_phone = json.getString("restaurant_phone");
            restaurant_address = json.getString("restaurant_address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String show(){
        String result="";
        result += restaurant_name;
        result += "\n";
        result += restaurant_description;
        return result;
    }

    @Override
    public String toString(){
        return "Name: "+ restaurant_name +"\n"+"Type: "+ restaurant_type +"\n"+"Time: "+restaurant_phone+"\n"+"Price: "+restaurant_address;
    }
}
