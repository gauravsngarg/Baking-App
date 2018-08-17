package gauravsngarg.com.bakingrecipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gauravsngarg.com.bakingrecipes.R;


public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.IngredientsStepViewHolder> {

    private Context context;
    private List<String> list;
    private static int count;

    private View view;
    private boolean mTwoPane;
    private final IngredientsStepClickListener mOnClickListener;

    public RecipeDetailsAdapter(Context mContext, int count, List<String> listitem, boolean mTwoPane) {
        context = mContext;
        this.count = count;
        list = listitem;
        this.mTwoPane = mTwoPane;
        mOnClickListener = (IngredientsStepClickListener) context;
    }

    public interface IngredientsStepClickListener {
        void onIngredientsStepItemCLick(int clickedItemIndex);
    }

    @Override
    public IngredientsStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_list_content, parent, false);
        IngredientsStepViewHolder holder = new IngredientsStepViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IngredientsStepViewHolder holder, int position) {

        holder.tv_step_description.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return count;
    }

    class IngredientsStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tv_step_description;

        public IngredientsStepViewHolder(View itemView) {
            super(itemView);

            tv_step_description = (TextView) itemView.findViewById(R.id.content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int clickPos = getAdapterPosition();
            mOnClickListener.onIngredientsStepItemCLick(clickPos);
        }
    }
}
