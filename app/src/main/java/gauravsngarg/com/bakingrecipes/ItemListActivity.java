package gauravsngarg.com.bakingrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gauravsngarg.com.bakingrecipes.adapter.RecipeDetailsAdapter;
import gauravsngarg.com.bakingrecipes.model.Recipe;
import gauravsngarg.com.bakingrecipes.model.RecipeSteps;

public class ItemListActivity extends AppCompatActivity implements RecipeDetailsAdapter.IngredientsStepClickListener {

    private List<String> list;
    private boolean mTwoPane;
    private int index, step_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if(savedInstanceState!=null){
            index = savedInstanceState.getInt("item_id");
        }else
        index = Integer.parseInt(getIntent().getExtras().getString("item_id"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Recipe_List_Fragment.list.get(index).getRecipeName());
        actionBar.setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();
        List<Recipe> list2 = Recipe_List_Fragment.list;
        list.add("Recipe Ingredients");
        for (RecipeSteps j : list2.get(index).getSteps()) {
            list.add(j.getShortDescription());
        }

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.item_list);
        assert recyclerView != null;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecipeDetailsAdapter(this, list.size(), list, mTwoPane));

    }

    @Override
    public void onIngredientsStepItemCLick(int clickedItemIndex) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString("step_id", clickedItemIndex + "");
            arguments.putString("item_id", index + "");
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra("step_id", clickedItemIndex + "");
            intent.putExtra("item_id", index + "");
            //step_id = clickedItemIndex;
            this.startActivity(intent);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("item_id", index+"");
    }
}
