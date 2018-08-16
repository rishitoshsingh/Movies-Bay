package alphae.download.movies.moviesbay.Api.Clients

import alphae.download.movies.moviesbay.POJOs.MovieDetails.MovieDetails
import alphae.download.movies.moviesbay.POJOs.MovieList.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by rishi on 3/7/18.
 */
interface YifyClient {
    @GET("list_movies.json")
    fun getMoviesList(
            @Query("page") page: Int,
            @Query("minimum_rating") minimumRating: String?,
            @Query("genre") genre: String?,
            @Query("sort_by") sortBy: String?,
            @Query("order_by") orderBy: String?,
            @Query("query_term") query: String?): Call<MovieList>

    @GET("list_movies.json")
    fun getMoviesFromId(
            @Query("query_term") query: String?): Call<MovieList>

    @GET("movie_details.json")
    fun getMovieDetails(
            @Query("movie_id") movieId: Long,
            @Query("with_images") withImages: Boolean,
            @Query("with_cast") withCast: Boolean): Call<MovieDetails>

}