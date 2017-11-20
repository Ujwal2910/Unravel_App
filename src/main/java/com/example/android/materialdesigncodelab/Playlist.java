package com.example.android.materialdesigncodelab;


import java.util.List;

public class Playlist {
    private String mTitle;
    private String mId;
    private String mChannelId;
    private String mChannelName;
    private String mThumbnailUrl;
    private List<Video> mVideoList;

    public Playlist(String title, String id, String channelId, String channelName, String thumbnalUrl){
        mTitle = title;
        mId = id;
        mChannelId = channelId;
        mChannelName = channelName;
        mThumbnailUrl = thumbnalUrl;
    }

    public Playlist(String title, String id, String channelId, String channelName, String thumbnalUrl, List<Video> videoList){
        mTitle = title;
        mId = id;
        mChannelId = channelId;
        mChannelName = channelName;
        mThumbnailUrl = thumbnalUrl;
        mVideoList = videoList;
    }

    public Playlist(String title){
        this.mTitle = title;
    }

    public String getPlavlistTitle(){
        return mTitle;
    }
    public String getPlavlistId(){
        return mId;
    }
    public String getPlavlistChannelId(){
        return mChannelId;
    }
    public String getPlavlistCHannelName(){
        return mChannelName;
    }
    public String getPlavlistThumbnailUrl(){
        return mThumbnailUrl;
    }
    public List<Video> getPlavlistVideoList(){
        return mVideoList;
    }
}
