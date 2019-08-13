package ir.alizeyn.rxsearchsample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author alizeyn
 * Created at 8/13/19
 */
public class Movie {

    private long id;
    private String title;
    @SerializedName("poster")
    private String posterUrl;
    private List<String> genres;
    @SerializedName("images")
    private List<String> imageUrls;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
