package com.example.android.materialdesigncodelab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
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

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoDetailFragment extends Fragment {

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

    private OnFragmentInteractionListener mListener;

    public VideoDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoDetailFragment newInstance(Video video2) {
        VideoDetailFragment fragment = new VideoDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playWhenReady = true;
        initializePlayer();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_detail, container, false);

        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.video_view);

        contentRootLayoutHiding = (LinearLayout) view.findViewById(R.id.detail_content_hiding);

        videoTitleRoot = (View) view.findViewById(R.id.detail_title_layout);
        videoTitleTextView = (TextView) view.findViewById(R.id.detail_video_title_view);
        videoTitleToggleArrow = (ImageView) view.findViewById(R.id.detail_toggle_description_view);
        videoCountView = (TextView) view.findViewById(R.id.detail_view_count_view);

        videoDescriptionRootLayout = (LinearLayout) view.findViewById(R.id.detail_description_layout);
        videoUploadDateView = (TextView) view.findViewById(R.id.detail_upload_date_view);
        videoDescriptionView = (TextView)  view.findViewById(R.id.detail_description_view);

        thumbsUpTextView = (TextView) view.findViewById(R.id.detail_thumbs_up_count_view);
        thumbsUpImageView = (ImageView) view.findViewById(R.id.detail_thumbs_up_img_view);
        thumbsDownTextView = (TextView) view.findViewById(R.id.detail_thumbs_down_count_view);
        thumbsDownImageView = (ImageView) view.findViewById(R.id.detail_thumbs_down_img_view);
        thumbsDisabledTextView = (TextView) view.findViewById(R.id.detail_thumbs_disabled_view);

        uploaderRootLayout = (LinearLayout) view.findViewById(R.id.detail_uploader_layout);
        uploaderTextView = (TextView) view.findViewById(R.id.detail_uploader_text_view);
        uploaderThumb = (ImageView) view.findViewById(R.id.detail_uploader_thumbnail_view);

        relatedStreamRootLayout = (LinearLayout) view.findViewById(R.id.detail_related_streams_layout);
        nextStreamTitle = (TextView) view.findViewById(R.id.detail_next_stream_title);
        relatedStreamsView = (LinearLayout) view.findViewById(R.id.detail_related_streams_view);
        relatedStreamExpandButton = (ImageButton) view.findViewById(R.id.detail_related_streams_expand);

        return view;
    }

    private void initializePlayer() {
        if (player == null) {
            // a factory to create an AdaptiveVideoTrackSelection
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
