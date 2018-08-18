package gauravsngarg.com.bakingrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import gauravsngarg.com.bakingrecipes.adapter.RecipeDetailsAdapter;
import gauravsngarg.com.bakingrecipes.model.Recipe;
import gauravsngarg.com.bakingrecipes.model.RecipeSteps;

public class ItemListActivity extends AppCompatActivity implements RecipeDetailsAdapter.IngredientsStepClickListener{

    private List<String> list;
    private boolean mTwoPane;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        index = getIntent().getExtras().getInt("item_id");


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
        if(mTwoPane){
            Bundle arguments = new Bundle();
            arguments.putString("step_id", clickedItemIndex +"");
            arguments.putString("item_id", index +"");
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        }else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra("step_id", clickedItemIndex +"");
            intent.putExtra("item_id", index +"");
            this.startActivity(intent);
        }

    }


}
