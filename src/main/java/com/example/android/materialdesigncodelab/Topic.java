package com.example.android.materialdesigncodelab;

import java.util.List;

/**
 * Created by Prakhar on 20-Nov-17.
 */

public class Topic {
    private String mTitle;
    private List<Playlist> mPlaylistList;
    private String mThumbnail;

    public Topic(String title, List<Playlist> playlistList){
        mTitle = title;
        mPlaylistList = playlistList;
    }

    public Topic(String title, String thumbnail){
        mTitle = title;
        mThumbnail = thumbnail;
    }
    public String getTopicTitle(){
        return mTitle;
    }

    public String getTopicThumbnail(){
        return mThumbnail;
    }
}
