package com.danielburgnerjr.bakingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.danielburgnerjr.bakingapp.utils.DetailRecipeListFragment;
import com.danielburgnerjr.bakingapp.utils.PlayerFragment;
import com.danielburgnerjr.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;
/**
 * A fullscreen activity to play audio or video streams.
 */
public class StepActivity extends AppCompatActivity {

    private static String TAG = StepActivity.class.getSimpleName();
    public static String STEPS_EXTRA = "steps";
    public static String POS_EXTRA = "pos";
    public static String RECIPE_NAME_EXTRA = "name";

    private Toolbar toolBar;
    private Button nextButton;
    private Button previousButton;

    private FragmentManager fragmentManager;
    private String recipeName = "";

    private int currentPos;
    private int defaultPos = 0;
    private List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_recipe_step);

        toolBar = (Toolbar) findViewById(R.id.toolBar);
        nextButton = (Button)findViewById(R.id.forward_button);
        previousButton = (Button)findViewById(R.id.back_button);
        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }
        currentPos = intent.getIntExtra(POS_EXTRA,defaultPos);
        steps = intent.getParcelableArrayListExtra(STEPS_EXTRA);
        recipeName = intent.getStringExtra(RECIPE_NAME_EXTRA);

        toolBar.setTitle(recipeName);
        toolBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Back button is clicked",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        populatePlayerView();
    }

    private void populatePlayerView(){
        String stepDescription = steps.get(currentPos).getDescription();
        String url0 = steps.get(currentPos).getVideoURL();
        String url1 = steps.get(currentPos).getThumbnailURL();
        String videoUrl = (url1.equals(""))?url0:url1;

        Bundle bundle = new Bundle();
        bundle.putString(PlayerFragment.DESCRIPTION_EXTRA,stepDescription);
        bundle.putString(PlayerFragment.VIDEO_URL_EXTRA,videoUrl);
        PlayerFragment playerFragment = new PlayerFragment();
        playerFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.player_fragment, playerFragment)
                .commit();

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.close_on_intent_error), Toast.LENGTH_SHORT).show();
    }

    public void nextOnClick(View view) {
        Toast.makeText(this,"Next",Toast.LENGTH_SHORT).show();
        currentPos++;
        if (currentPos == steps.size()-1) {
            nextButton.setEnabled(false);
        } else if (currentPos == 0) {
            previousButton.setEnabled(true);
        }
        populatePlayerView();
    }

    public void previousOnClick(View view) {
        Toast.makeText(this,"Previous",Toast.LENGTH_SHORT).show();
        currentPos--;
        if (currentPos == 0) {
            previousButton.setEnabled(false);
        } else if (currentPos == steps.size() - 1) {
            nextButton.setEnabled(true);
        }
        populatePlayerView();
    }

}
