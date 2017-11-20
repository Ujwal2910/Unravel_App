package com.example.android.materialdesigncodelab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

public class ChannelsFragment extends Fragment {

    private List<Playlist> playlistsList;
    private PlaylistAdapter mAdapter;
    private RecyclerView recyclerView;

    public ChannelsFragment(){
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channels, container, false);



        recyclerView =(RecyclerView) view.findViewById(R.id.rv_channels);
        playlistsList = new ArrayList<>();
        mAdapter = new PlaylistAdapter(getContext(), playlistsList, new PlaylistItemClickHandler() {
            @Override
            public void onListItemClick(Playlist clickedPlaylist) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public static class PlaylistAdapter extends RecyclerView.Adapter<ChannelsFragment.PlaylistAdapter.ViewHolder> {

        private List<Playlist> mPlaylists;
        private PlaylistItemClickHandler mOnClickListener;
        private Context mContext;

        public PlaylistAdapter(Context context, List<Playlist> playlists, PlaylistItemClickHandler listener) {
            mContext = context;
            mPlaylists = playlists;
            mOnClickListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()),parent);
        }

        @Override
        public void onBindViewHolder(final ChannelsFragment.PlaylistAdapter.ViewHolder holder, int position) {

            Context context = holder.mView.getContext();

            Playlist playlist = mPlaylists.get(position);
            holder.mPlaylistTitle.setText(playlist.getPlavlistTitle());
            holder.mChannelName.setText(playlist.getPlavlistCHannelName());

            Picasso.with(context)
                    .load(playlist.getPlavlistThumbnailUrl())
                    .into(holder.mPlaylistThumbnail);

            holder.mView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnClickListener.onListItemClick(holder.mPlaylist);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mPlaylists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public View mView;
            public Playlist mPlaylist;
            public ImageView mPlaylistThumbnail;
            public TextView mPlaylistTitle;
            public TextView mChannelName;

            public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.channel_list_item,parent,false));
                mView = itemView;
                mPlaylistThumbnail = (ImageView) itemView.findViewById(R.id.channels_list_playlist_thumbnail);
                mPlaylistTitle = (TextView) itemView.findViewById(R.id.channels_list_playlist_title);
                mChannelName = (TextView) itemView.findViewById(R.id.channels_list_playlist_channel_name);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                mOnClickListener.onListItemClick(mPlaylist);
            }
        }
    }
}
