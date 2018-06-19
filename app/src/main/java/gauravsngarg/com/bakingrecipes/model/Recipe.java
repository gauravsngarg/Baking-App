package gauravsngarg.com.bakingrecipes.model;

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
    RecipeIngredients mIngredients;
    RecipeSteps mSteps;
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

    public RecipeIngredients getIngredients() {
        return mIngredients;
    }

    public void setIngredients(RecipeIngredients ingredients) {
        mIngredients = ingredients;
    }

    public RecipeSteps getSteps() {
        return mSteps;
    }

    public void setSteps(RecipeSteps steps) {
        mSteps = steps;
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
