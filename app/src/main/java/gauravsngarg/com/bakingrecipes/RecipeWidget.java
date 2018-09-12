package gauravsngarg.com.bakingrecipes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gauravsngarg.com.bakingrecipes.activities.ItemDetailActivity;
import gauravsngarg.com.bakingrecipes.activities.MainActivity;
import gauravsngarg.com.bakingrecipes.model.Recipe;
import gauravsngarg.com.bakingrecipes.model.RecipeIngredients;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    public static final String UPDATE_MEETING_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String EXTRA_ITEM = "com.example.edockh.EXTRA_ITEM";

    public static List<RecipeIngredients> ingredients;

    public RecipeWidget() {
        super();
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, RecipeWidget.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(UPDATE_MEETING_ACTION)) {

            int appWidgetIds[] = manager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class));

            // Log.e("received", intent.getAction());

            manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);


        }

        super.onReceive(context, intent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_view);
        remoteViews.setRemoteAdapter(appWidgetId, R.id.widget_list_view, intent);


        Intent mainIntent = new Intent(context, ItemDetailActivity.class).putExtra("fromWidget", "true");
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.widget_list_view, pendingIntent);

        //remoteViews.setOnClickPendingIntent(R.id.tv_widget_text, pendingIntent);

        Gson gson = new Gson();
        String json = Utils.getFromPreference(context, "Recipe");

        JSONArray jsonArray = null;
        List<Recipe> recipes = new ArrayList<>();
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray.length() > 0) {
            recipes = Arrays.asList(gson.fromJson(jsonArray.toString(), Recipe[].class));
        }

        //Log.d("Gaurav310", json +"");

        Recipe recipe = recipes.get(0);

        Log.d("Gaurav31", recipe.getRecipeName() + "List check");


        ingredients = new ArrayList<>();
        if (recipe != null) {
            StringBuilder outIngredient = new StringBuilder();
            outIngredient.append(recipe.getRecipeName() + "\n");
            ingredients = recipe.getIngredients();

            for (int i = 0; i < ingredients.size(); i++) {
                outIngredient.append(ingredients.get(i).getQuantity() + " " + ingredients.get(i).getMeasure().toString() + " - " +
                        ingredients.get(i).getIngredient().toString() + "\n");
            }
            remoteViews.setTextViewText(R.id.tv_widget_text, recipe.getRecipeName());

        }
       // remoteViews.setRemoteAdapter(appWidgetId, R.id.widget_list_view, intent);



        // Trigger listview item click

        Intent startActivityIntent = new Intent(context, MainActivity.class);

        PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setPendingIntentTemplate(R.id.widget_list_view, startActivityPendingIntent);

        // The empty view is displayed when the collection has no items.

        // It should be in the same layout used to instantiate the RemoteViews  object above.

        remoteViews.setEmptyView(R.id.widget_list_view, R.id.widget_list_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

