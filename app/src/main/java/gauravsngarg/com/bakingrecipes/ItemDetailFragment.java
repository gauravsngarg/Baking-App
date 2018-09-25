package gauravsngarg.com.bakingrecipes;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gauravsngarg.com.bakingrecipes.adapter.RecipeIngredientsAdapter;
import gauravsngarg.com.bakingrecipes.dummy.DummyContent;
import gauravsngarg.com.bakingrecipes.model.RecipeIngredients;
import gauravsngarg.com.bakingrecipes.model.RecipeSteps;
import gauravsngarg.com.bakingrecipes.widget.RecipeAppWidgetProvider;

public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private DummyContent.DummyItem mItem;
    private List<RecipeIngredients> ingredientsList;
    private List<RecipeSteps> stepsList;
    private int index;
    private int step_index;
    private TextView tv_description;
    private TextView tv_videor_url;
    private ImageView iv_thumbnail;
    private TextView ingredients;
    private Button btn_openEXO;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Uri uri_current;
    private Bundle bundle;
    private RecyclerView rv_ingredients;


    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        index = Integer.parseInt(getArguments().getString("item_id"));
        step_index = Integer.parseInt(getArguments().getString("step_id"));

        ingredientsList = new ArrayList<>();
        stepsList = new ArrayList<>();
        bundle = new Bundle();
        if (savedInstanceState != null) {
            bundle = savedInstanceState;
        } else {
            bundle.putBoolean("player_state", false);
            bundle.putLong("player_pos", 0);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        StringBuilder outIngredient = new StringBuilder();

        if (step_index == 0) {
            rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

            ingredientsList.addAll(Recipe_List_Fragment.list.get(index).getIngredients());
            rv_ingredients = (RecyclerView) rootView.findViewById(R.id.ingredients_recycler_view);
            if (ingredientsList != null) {
                rv_ingredients.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                rv_ingredients.setLayoutManager(linearLayoutManager);
                RecipeIngredientsAdapter recipeIngredientsAdapter;

                recipeIngredientsAdapter = new RecipeIngredientsAdapter(getActivity(), ingredientsList.size(), ingredientsList);
                rv_ingredients.setAdapter(recipeIngredientsAdapter);

                RecipeAppWidgetProvider.sendRefreshBroadcast(getActivity());

            }
            /*for (int i = 0; i < ingredientsList.size(); i++) {
                outIngredient.append(ingredientsList.get(i).getQuantity() + " " + ingredientsList.get(i).getMeasure().toString() + " - " +
                        ingredientsList.get(i).getIngredient().toString() + "\n");
            }
            ingredients = ((TextView) rootView.findViewById(R.id.item_ingredients));
            RecipeWidget.sendRefreshBroadcast(getActivity());
            ingredients.setText(outIngredient);*/
        } else {
            rootView = inflater.inflate(R.layout.item_detail_step, container, false);
            tv_description = (TextView) rootView.findViewById(R.id.tv_description);
            iv_thumbnail = (ImageView) rootView.findViewById(R.id.iv_thumbnail);

            stepsList.addAll(Recipe_List_Fragment.list.get(index).getSteps());
            RecipeSteps step = stepsList.get(step_index - 1);

            String description = step.getDescription();

            String path_thumbnail = step.getThumbnailURL();
            if (path_thumbnail != null) {
                Uri uri_thumbnail = Uri.parse(path_thumbnail).buildUpon().build();

                iv_thumbnail.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(uri_thumbnail).into(iv_thumbnail);
            } else
                iv_thumbnail.setVisibility(View.INVISIBLE);

            mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.question_mark));


            if (step.getDescription() != null) {
                tv_description.setText(step.getDescription());
                tv_description.setVisibility(View.VISIBLE);
            } else
                tv_description.setVisibility(View.GONE);

            if (step.getVideoURL() != null) {
                uri_current = Uri.parse(step.getVideoURL());
            }
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (step_index > 0 && Util.SDK_INT > 23 && uri_current != null) {
            initializePlayer(uri_current, bundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (step_index > 0 && uri_current != null && (Util.SDK_INT <= 23 || mPlayerView == null))
            initializePlayer(uri_current, bundle);
    }

    private void initializePlayer(Uri mediaUri, Bundle bundle) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(bundle.getLong("player_pos"));
            mExoPlayer.setPlayWhenReady(bundle.getBoolean("player_state"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        long playerPos = mPlayerView.getPlayer().getCurrentPosition();
        boolean playerState = mExoPlayer.getPlayWhenReady();
        outState.putLong("player_pos", playerPos);
        outState.putBoolean("player_state", playerState);
    }

    private void releasePlayer() {
        if (step_index != 0) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23)
            releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23)
            releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
