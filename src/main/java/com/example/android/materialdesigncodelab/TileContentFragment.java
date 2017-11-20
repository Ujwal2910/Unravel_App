package com.example.android.materialdesigncodelab;

import  android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TileContentFragment extends Fragment {

    private List<Topic> topicsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        topicsList = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,container,false);
        TopicAdapter topicAdapter = new TopicAdapter(recyclerView.getContext(), topicsList, new TopicsItemClickHandler() {
            @Override
            public void onListItemClick(Topic clickedTopic) {
                //TODO something
            }
        });
        recyclerView.setAdapter(topicAdapter);
        recyclerView.setHasFixedSize(true);

        //padding for tile content
        int tilepadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilepadding,tilepadding,tilepadding,tilepadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        topicsList.add(new Topic("Machine Learning","https://www.kdnuggets.com/wp-content/uploads/blackboard-header.jpg"));
        topicsList.add(new Topic("Artificial Intelligence","http://robohub.org/wp-content/uploads/2017/02/grid-AI.jpg"));
        topicsList.add(new Topic("Python Programming","http://www.vishalavalani.com/wp-content/uploads/2017/08/python-programming.jpg"));
        topicsList.add(new Topic("Andoid Development","http://www.theappguruz.com/public/assets/new_site/images/android_app_banner.jpg"));
        topicsList.add(new Topic("Web Development","https://www.indianic.com/wp-content/uploads/2016/07/webdevelopment-bg.jpg"));
        topicsList.add(new Topic("Competitive Programming","http://nihal111.github.io/WnCC/images/competitive3.png"));

        return recyclerView;
    }

    public static class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

        private List<Topic> mTopics;
        private TopicsItemClickHandler mOnClickListener;
        private Context mContext;

        public TopicAdapter(Context context, List<Topic> topics, TopicsItemClickHandler listener) {
            mContext = context;
            mTopics = topics;
            mOnClickListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()),parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Context context = holder.mView.getContext();

            Topic topic = mTopics.get(position);
            holder.mTopicTitle.setText(topic.getTopicTitle());

            Picasso.with(context)
                    .load(topic.getTopicThumbnail())
                    .into(holder.mTopicThumbnail);

            holder.mView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnClickListener.onListItemClick(holder.mTopic);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTopics.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public View mView;
            public Topic mTopic;
            public TextView mTopicTitle;
            public ImageView mTopicThumbnail;

            public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_tile,parent,false));
                mView = itemView;
                mTopicTitle = (TextView) itemView.findViewById(R.id.tile_text);
                mTopicThumbnail = (ImageView) itemView.findViewById(R.id.tile_picture);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                mOnClickListener.onListItemClick(mTopic);
            }
        }
    }
}
