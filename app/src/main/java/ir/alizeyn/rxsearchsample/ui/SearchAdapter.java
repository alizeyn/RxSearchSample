package ir.alizeyn.rxsearchsample.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.alizeyn.rxsearchsample.R;
import ir.alizeyn.rxsearchsample.model.Movie;

/**
 * @author alizeyn
 * Created at 8/13/19
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private List<Movie> data;

    public SearchAdapter(Context context) {
        this.context = context;
        this.data = Collections.emptyList();
    }

    public void updateData(List<Movie> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_search, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Movie movie = data.get(position);
        List<String> genres = movie.getGenres();
        if (genres != null &&
                genres.size() > 0) {
            holder.genresTextView.setText(genres.get(0));
        }
        if (movie.getTitle() != null) {
            holder.nameTextView.setText(movie.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.genresTextView)
        TextView genresTextView;

        @BindView(R.id.nameTextView)
        TextView nameTextView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

