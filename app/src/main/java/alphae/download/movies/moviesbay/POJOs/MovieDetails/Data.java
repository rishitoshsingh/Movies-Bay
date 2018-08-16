
package alphae.download.movies.moviesbay.POJOs.MovieDetails;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("movie")
    private Movie mMovie;

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

}
