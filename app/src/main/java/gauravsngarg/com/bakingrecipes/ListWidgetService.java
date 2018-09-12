package gauravsngarg.com.bakingrecipes;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;


public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("Gaurav31", "ListWidgetService");
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}


