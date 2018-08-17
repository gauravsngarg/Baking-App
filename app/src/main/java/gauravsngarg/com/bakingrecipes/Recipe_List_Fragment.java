package gauravsngarg.com.bakingrecipes;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gauravsngarg.com.bakingrecipes.adapter.RecipeAdapter;
import gauravsngarg.com.bakingrecipes.model.Recipe;
import gauravsngarg.com.bakingrecipes.model.RecipeIngredients;
import gauravsngarg.com.bakingrecipes.model.RecipeSteps;
import gauravsngarg.com.bakingrecipes.utils.NetworkUtils;


public class Recipe_List_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recycler_view_recipes;
    private ProgressBar pb_indicator;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static List<Recipe> list;


    public RecipeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public Recipe_List_Fragment() {
        // Required empty public constructor
    }

    public static Recipe_List_Fragment newInstance(String param1, String param2) {
        Recipe_List_Fragment fragment = new Recipe_List_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe__list_, container, false);
        recycler_view_recipes = (RecyclerView) view.findViewById(R.id.recycler_view_recipes);
        pb_indicator = (ProgressBar) view.findViewById(R.id.pb_progress_recipes);

        layoutManager = new LinearLayoutManager(this.getActivity());
        recycler_view_recipes.setLayoutManager(layoutManager);
        recycler_view_recipes.setHasFixedSize(true);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        URL url = null;
        url = NetworkUtils.buildURL();
        new ShowRecipes().execute(url);
    }

    public class ShowRecipes extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb_indicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchURL = params[0];

            String recipeList = null;

            try {
                recipeList = NetworkUtils.getResponseFromHttpUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return recipeList;
        }

        @Override
        protected void onPostExecute(String json) {
            if (json != null) {
                JSONArray jsonArray = null;
                try {
                    list.clear();
                    jsonArray = new JSONArray(json);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Recipe newRecipe = new Recipe();
                        newRecipe.setRecipe_id(Integer.parseInt(jsonObject.getString("id")));
                        newRecipe.setRecipeName(jsonObject.getString("name"));

                        JSONArray jsonIngredientsArray = jsonObject.getJSONArray("ingredients");

                        List<RecipeIngredients> ingredients = new ArrayList<>();

                        for (int p = 0; p < jsonIngredientsArray.length(); p++) {

                            JSONObject jsonIngredient = jsonIngredientsArray.getJSONObject(p);
                            RecipeIngredients ingredientItem = new RecipeIngredients();

                            ingredientItem.setQuantity(jsonIngredient.getInt("quantity"));
                            ingredientItem.setMeasure(jsonIngredient.getString("measure"));
                            ingredientItem.setIngredient(jsonIngredient.getString("ingredient"));

                            ingredients.add(ingredientItem);
                        }

                        newRecipe.setIngredients(ingredients);

                        JSONArray jsonStepsArray = jsonObject.getJSONArray("steps");

                        List<RecipeSteps> steps = new ArrayList<>();

                        for (int p = 0; p < jsonStepsArray.length(); p++) {

                            JSONObject jsonStep = jsonStepsArray.getJSONObject(p);
                            RecipeSteps stepItem = new RecipeSteps();

                            stepItem.setSteps_id(jsonStep.getInt("id"));
                            stepItem.setShortDescription(jsonStep.getString("shortDescription"));
                            stepItem.setDescription(jsonStep.getString("videoURL"));
                            stepItem.setThumbnailURL(jsonStep.getString("thumbnailURL"));

                            steps.add(stepItem);
                        }

                        newRecipe.setSteps(steps);

                        newRecipe.setServings(jsonObject.getInt("servings"));
                        newRecipe.setImage(jsonObject.getString("image"));

                        list.add(newRecipe);

                    }

                    adapter = new RecipeAdapter(getActivity(), list.size(), list);
                    recycler_view_recipes.setAdapter(adapter);
                    pb_indicator.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                pb_indicator.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
