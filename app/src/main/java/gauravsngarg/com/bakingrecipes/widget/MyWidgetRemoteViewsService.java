package gauravsngarg.com.bakingrecipes.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by GG on 14/09/18.
 */

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
