package com.example.guadiannews;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USER on 21/03/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public String URL; // To store our URL string web API.

    /*
     * Public constructor matching parent class.
     */

    public NewsAdapter(NewsActivity context, int resource, ArrayList<News> news) {
        super(context, resource, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View newsListItem = convertView;
        if(newsListItem == null) {
            newsListItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Get the News object located at this position in the list
        News currentNews = getItem(position);

        // Find the TextView in the news_list_item.xml layout with the ID tvTitle
        TextView title = (TextView) newsListItem.findViewById(R.id.tvTitle);
        title.setText(currentNews.getmTitle());

        // Find the TextView in the news_list_item.xml layout with the ID tvSection
        TextView section = (TextView) newsListItem.findViewById(R.id.tvSection);
        section.setText(currentNews.getmSection());

        // Find the TextView in the news_list_item.xml layout with the ID tvDate
        TextView date = (TextView) newsListItem.findViewById(R.id.tvDate);

        //String formattedDate = formatDate(dateObject);
        date.setText(currentNews.getmDate());

        //Return the url
        URL = currentNews.getmUrl();

        // Set the proper background color on the section rectangle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable sectionRectangle = (GradientDrawable) section.getBackground();

        // Get the appropriate background color based on the current news section
        int sectionColor = getSectionColor(currentNews.getmSection());

        // Set the color on the magnitude circle
        sectionRectangle.setColor(sectionColor);

        return newsListItem;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    public int getSectionColor(String section){
        int sectionColorResId;
        switch (section){
            case "Sport":
            case "Football":
                sectionColorResId = R.color.sports;
                break;
            case "Fashion":
            case "Culture":
                sectionColorResId = R.color.fashion;
                break;
            case "Business":
            case "US news":
                sectionColorResId = R.color.business;
                break;
            case "Art and design":
            case "Science":
                sectionColorResId = R.color.artanddesign;
                break;
            case "Politics":
            case "Opinion":
                sectionColorResId = R.color.politics;
                break;
            default:
                sectionColorResId = R.color.default_;
                break;
        }
        return ContextCompat.getColor(getContext(), sectionColorResId);
    }
}
