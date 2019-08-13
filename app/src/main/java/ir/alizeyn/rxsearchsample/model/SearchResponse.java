package ir.alizeyn.rxsearchsample.model;

import java.util.List;

/**
 * @author alizeyn
 * Created at 8/13/19
 */
public class SearchResponse {
    private List<Movie> data;
    private Metadata metadata;

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
