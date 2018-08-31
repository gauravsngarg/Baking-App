package gauravsngarg.com.bakingrecipes;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

import gauravsngarg.com.bakingrecipes.dummy.DummyContent;
import gauravsngarg.com.bakingrecipes.model.RecipeIngredients;
import gauravsngarg.com.bakingrecipes.model.RecipeSteps;

public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private DummyContent.DummyItem mItem;
    private List<RecipeIngredients> ingredientsList;
    private List<RecipeSteps> stepsList;
    private int index;
    private int step_index;
    private TextView tv_description;
    private TextView tv_videor_url;
    private TextView tv_thumbnail;
    TextView ingredients;
    private Button btn_openEXO;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Uri uri_current;
    private Bundle bundle;


    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        index = Integer.parseInt(getArguments().getString("item_id"));
        step_index = Integer.parseInt(getArguments().getString("step_id"));

//        Log.d("Gaurav31", step_index + "= step");
//        Log.d("Gaurav31", index + "= index");

        ingredientsList = new ArrayList<>();
        stepsList = new ArrayList<>();
        bundle = new Bundle();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        StringBuilder outIngredient = new StringBuilder();

        if (step_index == 0) {
            rootView = inflater.inflate(R.layout.item_detail, container, false);
            ingredientsList.addAll(Recipe_List_Fragment.list.get(index).getIngredients());
            for (int i = 0; i < ingredientsList.size(); i++) {
                outIngredient.append(ingredientsList.get(i).getQuantity() + " " + ingredientsList.get(i).getMeasure().toString() + " - " +
                        ingredientsList.get(i).getIngredient().toString() + "\n");
            }
            ingredients = ((TextView) rootView.findViewById(R.id.item_ingredients));
            ingredients.setText(outIngredient);
        } else {
            Log.d("Gaurav31", "detail_step");
            rootView = inflater.inflate(R.layout.item_detail_step, container, false);
            tv_description = (TextView) rootView.findViewById(R.id.tv_description);

            //tv_videor_url = (TextView) rootView.findViewById(R.id.video_urla);
            //tv_thumbnail = (TextView) rootView.findViewById(R.id.tv_thumbmnail);

            stepsList.addAll(Recipe_List_Fragment.list.get(index).getSteps());
            RecipeSteps step = stepsList.get(step_index - 1);

            // tv_description.setText(step.getDescription() + "");
            //tv_videor_url.setText(step.getVideoURL() + "");
            //tv_thumbnail.setText(step.getThumbnailURL() + "");
            //btn_openEXO = (Button) rootView.findViewById(R.id.btn_openexo);

            mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.question_mark));


            if (step.getDescription() != null) {
                tv_description.setText(step.getDescription());
                tv_description.setVisibility(View.VISIBLE);
            } else
                tv_description.setVisibility(View.GONE);

            // mPlayerView.setVisibility(View.VISIBLE);

            if (step.getVideoURL() != null) {
                uri_current = Uri.parse(step.getVideoURL());
                //initializePlayer(uri, savedInstanceState);
            }
            if (savedInstanceState != null)
                bundle = savedInstanceState;
            else {
                bundle.putBoolean("player_state", false);
                bundle.putLong("player_pos", 0);
            }
        }


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 && uri_current != null) {
            initializePlayer(uri_current, bundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (uri_current != null && (Util.SDK_INT <= 23 || mPlayerView == null))
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
            mExoPlayer.setPlayWhenReady(bundle.getBoolean("player_pos"));
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
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


}
