package gauravsngarg.com.bakingrecipes.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class RecipesContract {

    public static final String AUTHORITY = "gauravsngarg.com.bakingrecipes";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_RECIPES = "recipes";

    public static final long INVALID_PLANT_ID = -1;

    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_RECIPE_NAME = "recipeName";
    }


}
