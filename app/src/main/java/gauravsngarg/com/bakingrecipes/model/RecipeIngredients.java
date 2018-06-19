package gauravsngarg.com.bakingrecipes.model;

/**
 * Created by GG on 19/06/18.
 */

public class RecipeIngredients {
//    quantity: 2,
//    measure: "CUP",
//    ingredient: "Graham Cracker crumbs"

    int quantity;
    String measure;
    String ingredient;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
