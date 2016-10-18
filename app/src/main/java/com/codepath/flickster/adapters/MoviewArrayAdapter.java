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

    // View lookup cache

    private static class ViewHolder {

        ImageView image;
        TextView title;
        TextView overview;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data for the positon
        Movie movie = getItem(position);

        int orientation = getContext().getResources().getConfiguration().orientation;

        ViewHolder viewHolder;
        // check the existing view being reused
        if(convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                viewHolder.image = (ImageView) convertView.findViewById(R.id.ivMoviewImage);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewHolder.image = (ImageView) convertView.findViewById(R.id.tvImageViewLand);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitleLand);
                viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverviewLand);
            }


            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.overview.setText(movie.getOverview());
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.image.setImageResource(0);



        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath()).placeholder(R.drawable.placeholder).fit().centerInside().into(viewHolder.image);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).into(viewHolder.image);
        }

        // return the view
        return convertView;
    }


}
