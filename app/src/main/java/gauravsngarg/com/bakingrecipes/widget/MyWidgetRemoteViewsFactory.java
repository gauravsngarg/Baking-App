package gauravsngarg.com.bakingrecipes.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gauravsngarg.com.bakingrecipes.R;
import gauravsngarg.com.bakingrecipes.Utils;
import gauravsngarg.com.bakingrecipes.model.Recipe;
import gauravsngarg.com.bakingrecipes.model.RecipeIngredients;

/**
 * Created by GG on 13/09/18.
 */

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe mRecipe;
    private int recipe_Index;

    private List<RecipeIngredients> list;


    public MyWidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
       // recipe_Index = Integer.parseInt(intent.getExtras().getString("index_id"));
    }

    @Override
    public void onCreate() {
        Log.d("Gaurav31", "onCreate");
        Gson gson = new Gson();
        String json = Utils.getFromPreference(mContext, "Recipe");

       // Utils.setRecipe_Index(recipe_Index);
        recipe_Index = Utils.getRecipe_Index();
        if (json != null) {
            list = new ArrayList<>();

            GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.create();
            try {
                JSONArray jsonArray = new JSONArray(json);

                int i = recipe_Index;
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Recipe recipe = gson.fromJson(jsonObject.toString(), Recipe.class);
                list = recipe.getIngredients();
                mRecipe = recipe;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

        @Override
    public void onDataSetChanged(){
        Log.d("Gaurav31", "DataSetChanged");
        if (list != null) {
        }

        final long identityToken = Binder.clearCallingIdentity();
        list = mRecipe.getIngredients();
        Binder.restoreCallingIdentity(identityToken);


    }

    @Override
    public void onDestroy() {
        if (list != null) {
            list.clear();

        }
        Log.d("Gaurav31", "onDestroy");

    }

    @Override
    public int getCount() {
        Log.d("Gaurav31", "getCount");
        if (list != null)
            return list.size();
        else return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION || list == null) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_list_item);
        rv.setTextViewText(R.id.tv_widget_text, mRecipe.getRecipeName());
        rv.setTextViewText(R.id.tv_ingredient, mRecipe.getIngredients().get(position).getIngredient());
        rv.setTextViewText(R.id.tv_measure, mRecipe.getIngredients().get(position).getMeasure());
        rv.setTextViewText(R.id.tv_quantity, mRecipe.getIngredients().get(position).getQuantity().toString());

        /*Intent fillInIntent = new Intent();
        fillInIntent.putExtra(RecipeAppWidgetProvider.EXTRA_LABEL, list.get(0).getIngredient().toString());
        rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);*/

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.d("Gaurav31", "getViewTypeCount");
        return 1;
    }

    @Override
    public long getItemId(int position) {
        Log.d("Gaurav31", "getItemId" + position);
        return position;
    }

    @Override
    public boolean hasStableIds() {
        Log.d("Gaurav31", "hasStableIds");
        return true;
    }
}
