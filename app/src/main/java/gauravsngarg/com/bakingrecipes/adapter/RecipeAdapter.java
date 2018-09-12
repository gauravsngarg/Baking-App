package gauravsngarg.com.bakingrecipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gauravsngarg.com.bakingrecipes.R;
import gauravsngarg.com.bakingrecipes.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipesViewHolder> {

    private final RecipeListItemClickListener mOnClickListner;

    private static List<Recipe> list;
    private static int count;

    private Context mContext;

    public RecipeAdapter(Context context, int recipeCount, List<Recipe> listitem) {

        mOnClickListner = (RecipeListItemClickListener) context;
        count = recipeCount;
        list = listitem;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.rv_list_item_recipe_name, parent, false);

        RecipesViewHolder holder = new RecipesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        holder.mRecipes.setText(list.get(position).getRecipeName());

    }

    @Override
    public int getItemCount() {
        return count;
    }

    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mRecipes;

        public RecipesViewHolder(View itemView) {
            super(itemView);

            mRecipes = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListner.onRecipeListItemClick(clickedPosition);
        }
    }

    public interface RecipeListItemClickListener {
        void onRecipeListItemClick(int clickedItemIndex);
    }
}
