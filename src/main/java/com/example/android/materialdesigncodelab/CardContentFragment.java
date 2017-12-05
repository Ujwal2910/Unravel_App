package com.example.android.materialdesigncodelab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CardContentFragment extends Fragment {

    private List<Video> videosList;
    private ImageView mThumbnail;
    private TextView mTitle;
    private TextView mDescription;

    public static Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_card, container, false);

        videosList = new ArrayList<>();
        videosList.add(new Video("Artificial Intelligence","https://www.youtube.com/watch?v=5J5bDQHQR1g","Intelligent machines are no longer science fiction and experts seem divided as to whether artificial intelligence should be feared or welcomed. In this video I explore a broad range AI research while attempting to envision a future where man and machine can coexist.","http://i.ytimg.com/vi/5J5bDQHQR1g/sddefault.jpg"));
        videosList.add(new Video("The Story of An Idea - Google I/O 2017 ","https://www.youtube.com/watch?v=BEtPCT7ZcE0","This is a story for anyone who has ever had an idea, big or small. ","http://i.ytimg.com/vi/BEtPCT7ZcE0/sddefault.jpg"));

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,container,false);
        ContentAdapter contentAdapter = new ContentAdapter(recyclerView.getContext(), videosList, new VideoItemClickHandler() {
            @Override
            public void onListItemClick(Video clickedVideo) {
                // TODO : Implement something
            }
        });
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mThumbnail = (ImageView) view.findViewById(R.id.card_image);
        mTitle = (TextView) view.findViewById(R.id.card_title);
        mDescription = (TextView) view.findViewById(R.id.card_text);

        return recyclerView;
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder>
    {

        private List<Video> mVideos;
        private VideoItemClickHandler mOnClickListener;
        private Context mContext;

        public ContentAdapter(Context context, List<Video> videos, VideoItemClickHandler listener)
        {
            mContext = context;
            mVideos = videos;
            mOnClickListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()),parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Context context = holder.mView.getContext();

            Video video = mVideos.get(position);
            holder.mVideo = video;
            holder.mTitle.setText(video.getTitle());
            holder.description.setText(video.getDescription());
            Log.e("Hello", "Workd");
            Picasso.with(context)
                    .load(video.getThumbnailUrl())
                    .into(holder.mPicture);

            Log.e("ThumbnailUrl",video.getThumbnailUrl());
            holder.mView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnClickListener.onListItemClick(holder.mVideo);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mVideos.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public View mView;
            public Video mVideo;
            public ImageView mPicture;
            public TextView mTitle;
            public TextView description;

            public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_card,parent,false));
                mView = itemView;
                mPicture = (ImageView) itemView.findViewById(R.id.card_image);
                mTitle = (TextView) itemView.findViewById(R.id.card_title);
                description = (TextView) itemView.findViewById(R.id.card_text);

                itemView.setOnClickListener(this);

            }
            @Override
            public void onClick(View v) {
                mOnClickListener.onListItemClick(mVideo);
            }
        }
    }


}
