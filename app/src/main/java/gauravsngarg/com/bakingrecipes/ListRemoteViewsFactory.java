package gauravsngarg.com.bakingrecipes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import gauravsngarg.com.bakingrecipes.adapter.WidgetRecipeAdapter;
import gauravsngarg.com.bakingrecipes.model.Recipe;
import gauravsngarg.com.bakingrecipes.model.RecipeIngredients;


public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe recipe;

    WidgetRecipeAdapter adapter;

    public ListRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        Gson gson = new Gson();
        String json = Utils.getFromPreference(mContext, "Recipe");
        recipe = gson.fromJson(json, Recipe.class);

    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (recipe != null) {
            return recipe.getIngredients().size();
        } else return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        final RemoteViews remoteView = new RemoteViews(
                mContext.getPackageName(), R.layout.ingredient_widget_list_item);

        Log.d("Gaurav31", "ListRemoteViewsFactory");
        RecipeIngredients listItem = recipe.getIngredients().get(position);

        Log.d("Gaurav31", listItem.getQuantity() + " " + listItem.getMeasure() + " " + listItem.getIngredient());
        remoteView.setTextViewText(R.id.tv_quantity, String.valueOf(listItem.getQuantity()));
        remoteView.setTextViewText(R.id.tv_measure, listItem.getMeasure());
        remoteView.setTextViewText(R.id.tv_ingredient, listItem.getIngredient());

        Bundle extras = new Bundle();
        extras.putInt(RecipeWidget.EXTRA_ITEM, position);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}