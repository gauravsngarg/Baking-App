package gauravsngarg.com.bakingrecipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gauravsngarg.com.bakingrecipes.R;
import gauravsngarg.com.bakingrecipes.model.RecipeIngredients;


public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientViewHolder> {

    private List<RecipeIngredients> ingredients;
    private Context mContext;
    private int count;


    public RecipeIngredientsAdapter(Context context, int count, List<RecipeIngredients> ingredients) {
        this.ingredients = ingredients;
        this.count = count;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.ingredient_card_list_item, parent, false);

        IngredientViewHolder holder = new IngredientViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {

        Log.d("Gaurav31", ingredients.get(position).getIngredient());
        holder.tv_ingredient.setText(ingredients.get(position).getIngredient());
        holder.tv_measure.setText(ingredients.get(position).getMeasure());
        holder.tv_quantity.setText(ingredients.get(position).getQuantity().toString());
    }

    @Override
    public int getItemCount() {
        return count;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        final TextView tv_ingredient;
        final TextView tv_measure;
        final TextView tv_quantity;

        public IngredientViewHolder(View view) {
            super(view);

            tv_ingredient = (TextView) view.findViewById(R.id.tv_ingredient);
            tv_measure = (TextView) view.findViewById(R.id.tv_measure);
            tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
        }
    }
}

