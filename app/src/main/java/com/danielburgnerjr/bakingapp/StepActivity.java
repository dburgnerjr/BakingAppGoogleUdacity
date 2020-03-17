package com.danielburgnerjr.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.danielburgnerjr.bakingapp.model.Step;
import com.danielburgnerjr.bakingapp.utils.PlayerFragment;

import java.util.List;
/**
 * A fullscreen activity to play audio or video streams.
 */
public class StepActivity extends AppCompatActivity {

    public static String STEPS_EXTRA = "steps";
    public static String POS_EXTRA = "pos";
    public static String RECIPE_NAME_EXTRA = "name";

    Toolbar toolBar;
    private Button nextButton;
    private Button previousButton;

    private FragmentManager fragmentManager;
    PlayerFragment playerFragment;
    String recipeName = "";

    private int currentPos;
    int defaultPos = 0;
    private List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_recipe_step);

        toolBar = findViewById(R.id.toolBar);
        nextButton = findViewById(R.id.forward_button);
        previousButton = findViewById(R.id.back_button);
        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }
        if (intent != null) {
            steps = intent.getParcelableArrayListExtra(STEPS_EXTRA);
            recipeName = intent.getStringExtra(RECIPE_NAME_EXTRA);
        }
        if (savedInstanceState != null) {
            currentPos = savedInstanceState.getInt(POS_EXTRA);
        } else {
            if (intent != null)
                currentPos = intent.getIntExtra(POS_EXTRA, defaultPos);
            populatePlayerView();
        }

        toolBar.setTitle(recipeName);
        toolBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp, null));
        toolBar.setNavigationOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"Back button is clicked",Toast.LENGTH_LONG).show();
            finish();
        });

        setEnablePreviousNextButton();
    }

    private void populatePlayerView(){
        String stepDescription = steps.get(currentPos).getDescription();
        String url0 = steps.get(currentPos).getVideoURL();
        String url1 = steps.get(currentPos).getThumbnailURL();
        String videoUrl = (url1.equals(""))?url0:url1;

        Bundle bundle = new Bundle();
        bundle.putString(PlayerFragment.DESCRIPTION_EXTRA,stepDescription);
        bundle.putString(PlayerFragment.VIDEO_URL_EXTRA,videoUrl);
        playerFragment = new PlayerFragment();
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
        setEnablePreviousNextButton();
        populatePlayerView();
    }

    public void previousOnClick(View view) {
        Toast.makeText(this,"Previous",Toast.LENGTH_SHORT).show();
        currentPos--;
        setEnablePreviousNextButton();
        populatePlayerView();
    }

    public void setEnablePreviousNextButton(){
        if (currentPos == steps.size() - 1) {
            nextButton.setEnabled(false);
        } else if (currentPos == 0) {
            previousButton.setEnabled(false);
        } else {
            nextButton.setEnabled(true);
            previousButton.setEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POS_EXTRA,currentPos);
    }
}
