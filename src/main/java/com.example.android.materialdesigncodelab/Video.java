package com.example.android.materialdesigncodelab;

/**
 * Created by Prakhar on 31-Oct-17.
 */

public class Video {
    private String mTitle;
    private String mUrl;
    private String mId;
    private String mChannelName;
    private String mDescription;
    private String mThumbnailUrl;
    private long mViewCount;
    private long mLikeCount;
    private long mDislikeCount;

    public Video(String title, String url, String id, String channelName, String description, String thumbnailUrl, long viewCount, long likeCount, long dislikeCount){
        mTitle = title;
        mUrl = url;
        mId = id;
        mChannelName = channelName;
        mDescription = description;
        mThumbnailUrl = thumbnailUrl;
        mViewCount = viewCount;
        mLikeCount = likeCount;
        mDislikeCount = dislikeCount;
    }

    public String getTitle(){
        return mTitle;
    }
    public String getUrl(){
        return mUrl;
    }
    public String getId(){
        return mId;
    }
    public String getChannelName(){
        return mChannelName;
    }
    public String getDescription(){
        return mDescription;
    }
    public String getThumbnailUrl(){
        return mThumbnailUrl;
    }
    public long getViewCount(){
        return mViewCount;
    }
    public long getLikeCount(){
        return mLikeCount;
    }
    public long getDislikeCount(){
        return mDislikeCount;
    }

}
