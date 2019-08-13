package ir.alizeyn.rxsearchsample.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.alizeyn.rxsearchsample.R;
import ir.alizeyn.rxsearchsample.model.Movie;
import ir.alizeyn.rxsearchsample.model.SearchResponse;
import ir.alizeyn.rxsearchsample.network.ApiServices;
import ir.alizeyn.rxsearchsample.network.RetrofitFactory;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    private static final int MIN_CHAR_TO_SEARCH = 2;
    private static final long SEARCH_QUERY_DELAY = 500;
    @BindView(R.id.searchEditText)
    EditText searchEditText;
    @BindView(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;

    private SearchAdapter adapter;
    private SearchObserver searchObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        adapter = new SearchAdapter(this);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(adapter);
        searchObserver = new SearchObserver();

        RxTextView.textChanges(searchEditText)
                .skip(1)
                .map(CharSequence::toString)
                .filter(string -> string.length() >= MIN_CHAR_TO_SEARCH)
                .debounce(SEARCH_QUERY_DELAY, TimeUnit.MILLISECONDS)
                .switchMap(this::search)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchObserver);

    }

    private Observable<SearchResponse> search(String text) {

        ApiServices api = RetrofitFactory.getApiServices();
        return api.search(text, 0);
    }

    class SearchObserver implements Observer<SearchResponse> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(SearchResponse searchResponse) {

            List<Movie> data = searchResponse.getData();
            if (data != null) {
                adapter.updateData(data);
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }


}
