package gauravsngarg.com.bakingrecipes.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;


public class RecipeContentProvider extends ContentProvider {

    public static final int RECIPES = 100;
    public static final int RECIPE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String TAG = RecipeContentProvider.class.getName();

    public static UriMatcher buildUriMatcher() {
        // Initialize a UriMatcher
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // Add URI matches
        uriMatcher.addURI(RecipesContract.AUTHORITY, RecipesContract.PATH_RECIPES, RECIPES);
        uriMatcher.addURI(RecipesContract.AUTHORITY, RecipesContract.PATH_RECIPES + "/#", RECIPE_WITH_ID);
        return uriMatcher;
    }

    private RecipeDbHelper mRecipeDbHelper;

    @Override
    public boolean onCreate() {

        Context context = getContext();
        mRecipeDbHelper = new RecipeDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mRecipeDbHelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            // Query for the plants directory
            case RECIPES:
                retCursor = db.query(RecipesContract.RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RECIPE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                retCursor = db.query(RecipesContract.RecipeEntry.TABLE_NAME,
                        projection,
                        "_id=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned
        switch (match) {
            case RECIPES:
                // Insert new values into the database
                long id = db.insert(RecipesContract.RecipeEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(RecipesContract.RecipeEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted plants
        int recipeDeleted; // starts as 0
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case RECIPE_WITH_ID:
                // Get the plant ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                recipeDeleted = db.delete(RecipesContract.RecipeEntry.TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Notify the resolver of a change and return the number of items deleted
        if (recipeDeleted != 0) {
            // A plant (or more) was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of plant deleted
        return recipeDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        // Keep track of the number of updated plants
        int recipesUpdated;

        switch (match) {
            case RECIPES:
                recipesUpdated = db.update(RecipesContract.RecipeEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case RECIPE_WITH_ID:
                if (selection == null) selection = RecipesContract.RecipeEntry._ID + "=?";
                else selection += " AND " + RecipesContract.RecipeEntry._ID + "=?";
                // Get the place ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Append any existing selection options to the ID filter
                if (selectionArgs == null) selectionArgs = new String[]{id};
                else {
                    ArrayList<String> selectionArgsList = new ArrayList<String>();
                    selectionArgsList.addAll(Arrays.asList(selectionArgs));
                    selectionArgsList.add(id);
                    selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);
                }
                recipesUpdated = db.update(RecipesContract.RecipeEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items updated
        if (recipesUpdated != 0) {
            // A place (or more) was updated, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of places deleted
        return recipesUpdated;
    }
}
