package gauravsngarg.com.bakingrecipes.model;

import java.util.List;

/**
 * Created by GG on 19/06/18.
 */

public class Recipe {
//    id: 1,
//    name: "Nutella Pie",
//    ingredients: [],
//    steps: [],
//    servings: 8,
//    image: ""

    int recipe_id;
    String recipeName;
    List<RecipeIngredients> mIngredients;
    List<RecipeSteps> mSteps;
    int servings;
    String image;

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<RecipeIngredients> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<RecipeIngredients> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public List<RecipeSteps> getSteps() {
        return mSteps;
    }

    public void setSteps(List<RecipeSteps> mSteps) {
        this.mSteps = mSteps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
