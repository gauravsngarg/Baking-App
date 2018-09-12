package gauravsngarg.com.bakingrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GG on 19/06/18.
 */

public class Recipe /*implements Parcelable*/{

    @SerializedName("id")
    @Expose
    private Integer recipe_id;
    @SerializedName("name")
    @Expose
    private String recipeName;
    @SerializedName("ingredients")
    @Expose
    private List<RecipeIngredients> mIngredients = null;
    @SerializedName("steps")
    @Expose
    private List<RecipeSteps> mSteps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

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

   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.recipe_id);
        dest.writeString(this.recipeName);
        dest.writeList(this.mIngredients);
        dest.writeList(this.mSteps);
        dest.writeValue(this.servings);
        dest.writeString(this.image);
    }

    protected Recipe(Parcel in) {
        this.recipe_id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.recipeName = in.readString();
        this.mIngredients = in.createTypedArrayList(RecipeIngredients.CREATOR);
        this.mSteps = new ArrayList<RecipeSteps>();
        in.readList(this.mSteps, RecipeSteps.class.getClassLoader());
        this.servings = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image = in.readString();
    }
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
*/
}
