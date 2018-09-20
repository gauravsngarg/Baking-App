package gauravsngarg.com.bakingrecipes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import gauravsngarg.com.bakingrecipes.R;
import gauravsngarg.com.bakingrecipes.activities.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_LABEL = "TASK_TEXT";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget_provider);

        Intent titleIntent = new Intent(context, MainActivity.class);
        PendingIntent titlePendingIntent = PendingIntent.getActivity(context,0 , titleIntent, 0);
        views.setOnClickPendingIntent(R.id.widgetTitleLabel, titlePendingIntent);

        Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widgetListView,  intent);



        /*CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget_provider);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget*/
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if(action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, RecipeAppWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widgetListView);
        }

        super.onReceive(context, intent);
    }


    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, RecipeAppWidgetProvider.class));
        context.sendBroadcast(intent);
    }
}



