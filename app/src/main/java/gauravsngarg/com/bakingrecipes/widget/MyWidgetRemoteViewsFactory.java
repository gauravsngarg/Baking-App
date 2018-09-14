package gauravsngarg.com.bakingrecipes.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

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

    private List<RecipeIngredients> list;


    public MyWidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        Gson gson = new Gson();
        String json = Utils.getFromPreference(mContext, "Recipe");
        mRecipe = gson.fromJson(json, Recipe.class);

        list = mRecipe.getIngredients();
        Log.d("Gaurav31", "onCreate");
    }

    @Override
    public void onDataSetChanged() {
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

        Log.d("Gaurav31", "getViewAt");
        if (position == AdapterView.INVALID_POSITION || list == null)
            return null;

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_list_item);
        rv.setTextViewText(R.id.tv_widget_text, mRecipe.getRecipeName());

       /* Intent fillInIntent = new Intent();
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
        Log.d("Gaurav31", "getItemId");
        return position;
    }

    @Override
    public boolean hasStableIds() {
        Log.d("Gaurav31", "hasStableIds");
        return true;
    }
}
