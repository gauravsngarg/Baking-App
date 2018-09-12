package gauravsngarg.com.bakingrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by gaurav.g4 on 08-09-2018.
 */

public class RecipeList /*implements Parcelable*/{

    ArrayList<Recipe> recipes = new ArrayList<>();

    public RecipeList(ArrayList<Recipe> recipes){
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.recipes);
    }

    protected RecipeList(Parcel in) {
        this.recipes = in.createTypedArrayList(Recipe.CREATOR);
    }

    public static final Parcelable.Creator<RecipeList> CREATOR = new Parcelable.Creator<RecipeList>() {
        @Override
        public RecipeList createFromParcel(Parcel source) {
            return new RecipeList(source);
        }

        @Override
        public RecipeList[] newArray(int size) {
            return new RecipeList[size];
        }
    };*/

}
