package gauravsngarg.com.bakingrecipes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class WidgetRecipeAdapter extends ArrayAdapter<String> {

    List<String> list;

    public WidgetRecipeAdapter(@NonNull Context context, int resource,  List<String> list ) {
        super(context, resource ,list);
        this.list = list;
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return list.indexOf(item);
    }
}
