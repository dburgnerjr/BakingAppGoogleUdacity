package com.danielburgnerjr.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.ArrayList;
/**
 * A fullscreen activity to play audio or video streams.
 */
public class StepActivity extends AppCompatActivity {

    private static final String DESCRIPTION_FRAGMENT = "description_fragment";

    Step mStep;
    ArrayList<Step> mStepList;
    private int nStepPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onclick_steps);
        mStepList = getIntent().getExtras().getParcelableArrayList(StepFragment.PROCESS_EXTRA);
        nStepPosition = getIntent().getExtras().getInt(StepFragment.PROCESS_POSITION);
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelableArrayList("ArrayList", mStepList);
        stepsBundle.putInt("step_position", nStepPosition);
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        descriptionFragment.setArguments(stepsBundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.description_container, descriptionFragment, DESCRIPTION_FRAGMENT).commit();
}
