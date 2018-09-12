package gauravsngarg.com.bakingrecipes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by gaurav.g4 on 08-09-2018.
 */

public class Utils {
    public static void saveObjectInPreference(Context context, String key, Object value){
        SharedPreferences preferences = context.getSharedPreferences("pbSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public static String getFromPreference(Context context, String key){

        SharedPreferences preferences = context.getSharedPreferences("pbSave", Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }
}
