package gauravsngarg.com.bakingrecipes.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import gauravsngarg.com.bakingrecipes.R;
import gauravsngarg.com.bakingrecipes.Recipe_List_Fragment;
import gauravsngarg.com.bakingrecipes.adapter.RecipeAdapter;

public class MainActivity extends AppCompatActivity implements Recipe_List_Fragment.OnFragmentInteractionListener,
        RecipeAdapter.RecipeListItemClickListener {

    // private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRecipeListItemClick(int clickedItemIndex) {
        Toast.makeText(this, "Item Clicked" + (clickedItemIndex + 1), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, ItemListActivity.class);
        i.putExtra("item_id", clickedItemIndex + "");
        startActivity(i);
    }
}
