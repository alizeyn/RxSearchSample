package ir.alizeyn.rxsearchsample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.alizeyn.rxsearchsample.R;
import ir.alizeyn.rxsearchsample.model.Movie;
import ir.alizeyn.rxsearchsample.model.SearchResponse;
import ir.alizeyn.rxsearchsample.network.ApiServices;
import ir.alizeyn.rxsearchsample.network.RetrofitFactory;

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = SearchActivity.class.getName();

    private static final int MIN_CHAR_TO_SEARCH = 2;
    private static final long SEARCH_QUERY_DELAY = 500;
    @BindView(R.id.searchEditText) EditText searchEditText;
    @BindView(R.id.searchRecyclerView) RecyclerView searchRecyclerView;
    private CompositeDisposable compositeDisposable;
    private SearchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        ApiServices api = RetrofitFactory.getApiServices();
        compositeDisposable = new CompositeDisposable();
        adapter = new SearchAdapter(this);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(adapter);

        Disposable disposable = RxTextView.textChanges(searchEditText)
                .map(CharSequence::toString)
                .doOnNext(this::showSearchViews)
                .filter(string -> string.length() >= MIN_CHAR_TO_SEARCH)
                .debounce(SEARCH_QUERY_DELAY, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .switchMap(api::search)
                .retry()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::next, Throwable::printStackTrace);

        compositeDisposable.add(disposable);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private void showSearchViews(String s) {
        if (s.length() > MIN_CHAR_TO_SEARCH) {
            searchRecyclerView.setVisibility(View.VISIBLE);
        } else {
            adapter.updateData(Collections.emptyList());
            searchRecyclerView.setVisibility(View.GONE);
        }
    }

    private void next(SearchResponse searchResponse) {
        List<Movie> data = searchResponse.getData();
        if (data != null) {
            adapter.updateData(data);

            if (data.size() == 0) {
                Toast.makeText(SearchActivity.this,
                        "no result",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @OnClick(R.id.clearButton)
    void clearSearchText() {
        searchEditText.setText("");
    }

}
