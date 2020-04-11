package com.danielburgnerjr.bakingapp.utils;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.danielburgnerjr.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class PlayerFragment extends Fragment {

    public static String VIDEO_URL_EXTRA = "video_url";
    public static String DESCRIPTION_EXTRA = "description";
    private static String PLAYBACK_POS_EXTRA = "pbpe";
    private static String CURRENT_WINDOW_EXTRA = "window";
    private static String PLAY_WHEN_READY_EXTRA = "play";

    private SimpleExoPlayer player;
    private PlayerView playerView;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    private String videoUrl;

    public PlayerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player,container, false);

        playerView = view.findViewById(R.id.video_view);
        TextView stepDescriptionTv = view.findViewById(R.id.step_instruction_tv);

        if (getArguments() != null) {
            videoUrl = getArguments().getString(VIDEO_URL_EXTRA);
            String stepDescription = getArguments().getString(DESCRIPTION_EXTRA);
            initializePlayer(savedInstanceState);
            stepDescriptionTv.setText(stepDescription);
        }
        if (videoUrl.equals("")) {
            playerView.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        playbackPosition = player.getCurrentPosition();
        currentWindow = player.getCurrentWindowIndex();
        playWhenReady = player.getPlayWhenReady();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer(Bundle savedInstanceState) {
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        playerView.setPlayer(player);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(new
                DefaultHttpDataSourceFactory("bakingApp"))
                .createMediaSource(Uri.parse(videoUrl));
        player.prepare(mediaSource, false, false);

        if (savedInstanceState == null) {
            player.setPlayWhenReady(true);
        } else if (savedInstanceState.containsKey(PLAYBACK_POS_EXTRA)) {
            player.setPlayWhenReady(savedInstanceState.getBoolean(PLAY_WHEN_READY_EXTRA));
            player.seekTo(savedInstanceState.getInt(CURRENT_WINDOW_EXTRA),
                    savedInstanceState.getLong(PLAYBACK_POS_EXTRA));
        }

    }

    private void releasePlayer() {
        if (player != null) {

            player.release();
            player = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(PLAYBACK_POS_EXTRA, playbackPosition);
        outState.putInt(CURRENT_WINDOW_EXTRA, currentWindow);
        outState.putBoolean(PLAY_WHEN_READY_EXTRA, playWhenReady);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
