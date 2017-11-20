package com.example.android.materialdesigncodelab;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayerActivity extends AppCompatActivity {

    private LinearLayout contentRootLayoutHiding;
    private View videoTitleRoot;
    private TextView videoTitleTextView;
    private ImageView videoTitleToggleArrow;
    private TextView videoCountView;

    private LinearLayout videoDescriptionRootLayout;
    private TextView videoUploadDateView;
    private TextView videoDescriptionView;

    private View uploaderRootLayout;
    private TextView uploaderTextView;
    private ImageView uploaderThumb;

    private TextView thumbsUpTextView;
    private ImageView thumbsUpImageView;
    private TextView thumbsDownTextView;
    private ImageView thumbsDownImageView;
    private TextView thumbsDisabledTextView;

    private TextView nextStreamTitle;
    private LinearLayout relatedStreamRootLayout;
    private LinearLayout relatedStreamsView;
    private ImageButton relatedStreamExpandButton;

    private SimpleExoPlayer player;
    private SimpleExoPlayerView mPlayerView;

    private boolean playWhenReady;
    private int currentWindow;
    private long playbackPosition;

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        playWhenReady = true;

        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.video_view);

        contentRootLayoutHiding = (LinearLayout) findViewById(R.id.detail_content_hiding);

        videoTitleRoot = (View) findViewById(R.id.detail_title_layout);
        videoTitleTextView = (TextView) findViewById(R.id.detail_video_title_view);
        videoTitleToggleArrow = (ImageView) findViewById(R.id.detail_toggle_description_view);
        videoCountView = (TextView) findViewById(R.id.detail_view_count_view);

        videoDescriptionRootLayout = (LinearLayout) findViewById(R.id.detail_description_layout);
        videoUploadDateView = (TextView) findViewById(R.id.detail_upload_date_view);
        videoDescriptionView = (TextView)  findViewById(R.id.detail_description_view);

        thumbsUpTextView = (TextView) findViewById(R.id.detail_thumbs_up_count_view);
        thumbsUpImageView = (ImageView) findViewById(R.id.detail_thumbs_up_img_view);
        thumbsDownTextView = (TextView) findViewById(R.id.detail_thumbs_down_count_view);
        thumbsDownImageView = (ImageView) findViewById(R.id.detail_thumbs_down_img_view);
        thumbsDisabledTextView = (TextView) findViewById(R.id.detail_thumbs_disabled_view);

        uploaderRootLayout = (LinearLayout) findViewById(R.id.detail_uploader_layout);
        uploaderTextView = (TextView) findViewById(R.id.detail_uploader_text_view);
        uploaderThumb = (ImageView) findViewById(R.id.detail_uploader_thumbnail_view);

        relatedStreamRootLayout = (LinearLayout) findViewById(R.id.detail_related_streams_layout);
        nextStreamTitle = (TextView) findViewById(R.id.detail_next_stream_title);
        relatedStreamsView = (LinearLayout) findViewById(R.id.detail_related_streams_view);
        relatedStreamExpandButton = (ImageButton) findViewById(R.id.detail_related_streams_expand);

        initializePlayer();

    }
    private void initializePlayer() {
        if (player == null) {
            // a factory to create an AdaptiveVideoTrackSelection
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());

            mPlayerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(getString(R.string.media_url_mp4));
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER);
        DashChunkSource.Factory dashChunkSourceFactory =
                new DefaultDashChunkSource.Factory(dataSourceFactory);

        return new DashMediaSource(uri, dataSourceFactory,
                dashChunkSourceFactory, null, null);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
