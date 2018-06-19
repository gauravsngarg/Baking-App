package gauravsngarg.com.bakingrecipes.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by GG on 19/06/18.
 */

public class NetworkUtils {

    private final static String THE_BAKING_DB_API_URL = "https://d17h27t6h515a5.cloudfront.net/" +
            "topher/2017/May/59121517_baking/baking.json";


    public static URL buildURL(){
        Uri builtURI = Uri.parse(THE_BAKING_DB_API_URL).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtURI.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
