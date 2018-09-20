package gauravsngarg.com.bakingrecipes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Utils {

    public static String JSON = null;

    public static int  Recipe_Index = 0;

    public static String getJSON() {
        return JSON;
    }

    public static void setJSON(String JSON) {
        Utils.JSON = JSON;
    }

    public static void saveObjectInPreference(Context context, String key, Object value){
        SharedPreferences preferences = context.getSharedPreferences("pbSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public static int getRecipe_Index() {
        return Recipe_Index;
    }

    public static void setRecipe_Index(int recipe_Index) {
        Recipe_Index = recipe_Index;
    }

    public static String getFromPreference(Context context, String key){

        SharedPreferences preferences = context.getSharedPreferences("pbSave", Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }
}
