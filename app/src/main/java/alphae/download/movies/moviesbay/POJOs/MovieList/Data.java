
package alphae.download.movies.moviesbay.POJOs.MovieList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("limit")
    private Long mLimit;
    @SerializedName("movie_count")
    private Long mMovieCount;
    @SerializedName("movies")
    private List<Movie> mMovies;
    @SerializedName("page_number")
    private Long mPageNumber;

    public Long getLimit() {
        return mLimit;
    }

    public void setLimit(Long limit) {
        mLimit = limit;
    }

    public Long getMovieCount() {
        return mMovieCount;
    }

    public void setMovieCount(Long movieCount) {
        mMovieCount = movieCount;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    public Long getPageNumber() {
        return mPageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        mPageNumber = pageNumber;
    }

}
