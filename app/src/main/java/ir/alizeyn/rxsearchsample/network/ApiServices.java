package ir.alizeyn.rxsearchsample.network;

import io.reactivex.Observable;
import ir.alizeyn.rxsearchsample.model.SearchResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author alizeyn
 * Created at 8/13/19
 */
public interface ApiServices {

    @GET("api/v1/movies")
    Observable<SearchResponse> search(@Query("q") String name);
}

