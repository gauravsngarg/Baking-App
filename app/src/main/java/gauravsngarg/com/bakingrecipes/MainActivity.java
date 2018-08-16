package gauravsngarg.com.bakingrecipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import gauravsngarg.com.bakingrecipes.adapter.RecipeAdapter;

public class MainActivity extends AppCompatActivity implements Recipe_List_Fragment.OnFragmentInteractionListener,
        RecipeAdapter.RecipeListItemClickListener{

    // private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//       FragmentManager fragmentManager = getFragmentManager();
//       Recipe_List_Fragment fragment = new Recipe_List_Fragment();
//       FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
//       fragmentTransaction.add(R.id.container,fragment );
//       fragmentTransaction.commit(); // save the changes


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRecipeListItemClick(int clickedItemIndex) {
        Toast.makeText(this, "Item Clicked" + (clickedItemIndex+1),Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, RecipeDetails.class);
        startActivity(i);
    }
}
