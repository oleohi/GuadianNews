package com.example.guadiannews;

/**
 * Created by USER on 21/03/2017.
 */

public class News {
    private int mSectionColor;

    /*
     * Private class member variables.
     */

    private String mTitle;
    private String mSection;
    private String mDate;
    private String mUrl;

    /*
     * Public constructor for class private variables.
     */

    public News(String title, String section, String date, String url) {
        mTitle = title;
        mSection = section;
        mDate = date;
        mUrl = url;
    }

    /*
     * public Getter and Setter methods for private class members.
     */

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public void setmSection(String mSection) {
        this.mSection = mSection;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
