package alphae.download.movies.moviesbay.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by rishi on 3/7/18.
 */
class ServiceGenerator {
    companion object {

        private val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl("https://yts.am/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
        private val retrofit: Retrofit = builder.build()

        fun <S> createService(serviceClass: Class<S>): S {
            return retrofit.create(serviceClass)
        }
    }
}