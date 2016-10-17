package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vmiha on 10/16/16.
 */
public class MoviewArrayAdapter extends ArrayAdapter<Movie> {

    public MoviewArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data for the positon
        Movie movie = getItem(position);

        // check the existing view being reused
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            // find the image view
            ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMoviewImage);
            // clear out image from convertView
            ivImage.setImageResource(0);

            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            TextView tvOverview = (TextView) convertView.findViewById((R.id.tvOverview));

            //populate data
            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(movie.getOverview());

            Picasso.with(getContext()).load(movie.getPosterPath()).into(ivImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // find the image view
            ImageView ivImage = (ImageView) convertView.findViewById(R.id.tvImageViewLand);
            // clear out image from convertView
            ivImage.setImageResource(0);

            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitleLand);
            TextView tvOverview = (TextView) convertView.findViewById((R.id.tvOverviewLand));

            //populate data
            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(movie.getOverview());

            Picasso.with(getContext()).load(movie.getPosterPath()).into(ivImage);
        }

        // return the view
        return convertView;
    }


}
