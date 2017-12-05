package com.example.android.materialdesigncodelab;

/**
 * Created by Prakhar on 04-Dec-17.
 */

public class Project {
    private String mName;
    private String mDescription;
    private String mLanguage;
    private long mStars;
    private long mForks;
    private String mUrl;

    public Project(String name, String desc, String language, long stars, long forks, String url){
        mName = name;
        mDescription = desc;
        mLanguage = language;
        mStars = stars;
        mForks = forks;
        mUrl = url;
    }

    public String getProjectName(){
        return mName;
    }

    public String getProjectDescription(){
        return mDescription;
    }

    public String getProjectLanguage(){
        return mLanguage;
    }

    public long getProjectStars(){
        return mStars;
    }

    public long getProjectForks(){
        return mForks;
    }

    public String getProjectUrl(){
        return mUrl;
    }
}
