package gauravsngarg.com.bakingrecipes.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import gauravsngarg.com.bakingrecipes.ItemDetailFragment;
import gauravsngarg.com.bakingrecipes.R;
import gauravsngarg.com.bakingrecipes.Recipe_List_Fragment;

public class ItemDetailActivity extends AppCompatActivity {



    private int index;
    private int step_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        if(savedInstanceState!=null) {
            index = savedInstanceState.getInt("item_id");
            step_index = savedInstanceState.getInt("step_id");
        }else{
            index = Integer.parseInt(getIntent().getExtras().getString("item_id"));
            step_index = Integer.parseInt(getIntent().getExtras().getString("step_id"));
        }


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(step_index == 0){
            actionBar.setTitle("Ingredients");
        }else {
            String str = Recipe_List_Fragment.list.get(index).getSteps().get(step_index -1).getShortDescription();
            actionBar.setTitle(str);

        }

        if (savedInstanceState == null) {
            Bundle argBundle = new Bundle();
            ItemDetailFragment fragment = new ItemDetailFragment();
            argBundle.putString("item_id", getIntent().getExtras().getString("item_id"));
            argBundle.putString("step_id", getIntent().getExtras().getString("step_id"));
            fragment.setArguments(argBundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("item_id", index);
        outState.putInt("step_id", step_index);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
