package com.example.dany.movies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dany.movies.Model.Movie;
import com.example.dany.movies.R;
import com.example.dany.movies.Utils.Constants;
import com.example.dany.movies.View.MovieDetailsActivity;

import java.util.List;

/**
 * Created by Dany on 11.2.2018 г..
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> listMovies;
    private Context context;

    public MoviesAdapter(List<Movie>listMovies, Context context) {
        this.listMovies = listMovies;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewName;
        public TextView textViewActors;
        public TextView textViewDirector;
        public TextView textViewYear;
        public RatingBar ratingBar;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.textViewName = view.findViewById(R.id.textViewName);
            this.textViewActors = view.findViewById(R.id.textViewActors);
            this.textViewDirector = view.findViewById(R.id.textViewDirector);
            this.textViewYear = view.findViewById(R.id.textViewYear);
            this.ratingBar = view.findViewById(R.id.ratingBar);
        }

        @Override
        public void onClick(View view) {
            Intent intentEdit = new Intent(context, MovieDetailsActivity.class);
            intentEdit.putExtra(Constants.ID, listMovies.get(getAdapterPosition()).getId());
            intentEdit.putExtra(Constants.NAME, listMovies.get(getAdapterPosition()).getName());
            intentEdit.putExtra(Constants.ACTORS, listMovies.get(getAdapterPosition()).getActors());
            intentEdit.putExtra(Constants.DIRECTOR, listMovies.get(getAdapterPosition()).getDirector());
            intentEdit.putExtra(Constants.YEAR, listMovies.get(getAdapterPosition()).getYear());
            intentEdit.putExtra(Constants.RATING, listMovies.get(getAdapterPosition()).getRating());
            context.startActivity(intentEdit);
        }
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_movies_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {
        Movie movie = listMovies.get(position);
        holder.textViewName.setText(context.getResources().getString(R.string.name,movie.getName()));
        holder.textViewActors.setText(context.getResources().getString(R.string.actors, movie.getActors()));
        holder.textViewDirector.setText(context.getResources().getString(R.string.director,movie.getDirector()));
        holder.textViewYear.setText(context.getResources().getString(R.string.year, movie.getYear()));
        holder.ratingBar.setRating(movie.getRating());
    }

    @Override
    public int getItemCount() {

        if(listMovies != null)
            return listMovies.size();
        else return 0;
    }

    public void addItems(List<Movie> listMovies) {
        this.listMovies = listMovies;
        notifyDataSetChanged();
    }

}
