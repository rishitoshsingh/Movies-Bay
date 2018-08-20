package alphae.download.movies.moviesbay.Activities

import alphae.download.movies.moviesbay.Adapters.DownloadAdapter
import alphae.download.movies.moviesbay.Api.Clients.YifyClient
import alphae.download.movies.moviesbay.Api.ServiceGenerator
import alphae.download.movies.moviesbay.BuildConfig
import alphae.download.movies.moviesbay.POJOs.MovieDetails.Movie
import alphae.download.movies.moviesbay.POJOs.MovieDetails.MovieDetails
import alphae.download.movies.moviesbay.POJOs.MovieDetails.Torrent
import alphae.download.movies.moviesbay.R
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var mMovie: Movie
    private var mMovieId: Long = 0
    private lateinit var mMovieCover: String
    private lateinit var client: YifyClient
    private lateinit var mPalette: Palette
    private var mFiles: ArrayList<Torrent> = ArrayList()
    private lateinit var downloadAdapter: DownloadAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var mColor: Int = 0
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        client = ServiceGenerator.createService(YifyClient::class.java)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd!!.adUnitId = BuildConfig.AdmobInterstisial
        mInterstitialAd!!.loadAd(AdRequest.Builder().build())
        mInterstitialAd!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                val sharedPreferences: SharedPreferences = getSharedPreferences("Notification", Context.MODE_PRIVATE)
                if (sharedPreferences.getBoolean("Clicked", false)) {
                    if (mInterstitialAd != null && mInterstitialAd!!.isLoaded) {
                        mInterstitialAd!!.show()
                    }
                    val sharedPreferenceEditor: SharedPreferences.Editor = sharedPreferences.edit()
                    sharedPreferenceEditor.putBoolean("Clicked", false)
                    sharedPreferenceEditor.commit()
                }
            }
        }

        mColor = resources.getColor(R.color.darkBackground)
        mMovieId = intent.getLongExtra("MovieId", 0)
        mMovieCover = intent.getStringExtra("MovieCover")
        layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        downloadAdapter = object : DownloadAdapter(this, mFiles, mColor) {
            override fun getMovieName() = mMovie.title
        }

        downloads_recycler.adapter = downloadAdapter
        downloads_recycler.setHasFixedSize(true)
        downloads_recycler.layoutManager = layoutManager
        downloads_recycler.itemAnimator = DefaultItemAnimator()

        Glide.with(this)
                .asBitmap()
                .load(Uri.parse(mMovieCover))
                .listener(object : RequestListener<Bitmap> {
                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        movie_poster_progress.visibility = View.GONE
                        mPalette = Palette.from(resource!!).generate()
                        try {
                            description.backgroundTintList = ColorStateList.valueOf(mPalette.darkMutedSwatch?.rgb!!)
                            cast_container.backgroundTintList = ColorStateList.valueOf(mPalette.darkMutedSwatch?.rgb!!)
                            downloads_recycler.backgroundTintList = ColorStateList.valueOf(mPalette.darkMutedSwatch?.rgb!!)
                            mColor = mPalette.darkMutedSwatch?.rgb!!
                            downloadAdapter = object : DownloadAdapter(this@MovieDetailsActivity, mFiles, mColor) {
                                override fun getMovieName() = mMovie.title
                            }
                            downloads_recycler.adapter = downloadAdapter
//                            downloadAdapter.notifyDataSetChanged()
                        } catch (ex: Exception) {
                        }
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        movie_poster_progress.visibility = View.GONE
                        return false
                    }
                })
                .apply(RequestOptions()
                        .centerCrop())
                .into(movie_poster)

        Glide.with(this)
                .asBitmap()
                .load(Uri.parse(mMovieCover))
                .apply(RequestOptions().transforms(BlurTransformation(25)))
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        nested_scrollView.background = BitmapDrawable(resource)
                    }
                })
        getMovieDetails()

        towatch_share_icon.setOnClickListener {
            val towatchInstalled = isPackageInstalled("com.alphae.rishi.towatch", packageManager)

            if (towatchInstalled) {
                val shareIntent: Intent = ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setText(mMovie.imdbCode)
                        .intent
                shareIntent.`package` = "com.alphae.rishi.towatch"
                if (shareIntent.resolveActivity(packageManager) != null) {
                    startActivity(shareIntent)
                }
            } else {
                val alertDialog = AlertDialog.Builder(this@MovieDetailsActivity).create()
                alertDialog.setTitle("ToWatch not found")
                alertDialog.setMessage("MoviesBay works well with ToWatch. Install Now.")
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Install",
                        DialogInterface.OnClickListener { dialog, which ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.alphae.rishi.towatch"))
                            startActivity(intent)
                        })
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Close",
                        DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })

                alertDialog.show()
            }

        }

        val towatchInstalled = isPackageInstalled("com.alphae.rishi.towatch", packageManager)

        if (!towatchInstalled) {
            val alertDialog = AlertDialog.Builder(this@MovieDetailsActivity).create()
            alertDialog.setTitle("Try ToWatch")
            alertDialog.setMessage("MoviesBay works well with ToWatch. Install Now.")
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Install",
                    DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.alphae.rishi.towatch"))
                        startActivity(intent)
                    })
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Close",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })

            alertDialog.show()
        }

        back_button.setOnClickListener {
            finish()
        }

    }

    private fun updateMoviesDetails() {
        movie_name.text = mMovie.title
        year.text = mMovie.year.toString()
        rating.text = mMovie.rating.toString()
        var genreString: String = ""
        if (mMovie.genres != null) {
            for (genre in mMovie.genres) genreString = "$genreString$genre, "
            genreString = genreString.substring(0, genreString.lastIndex - 1)
            movie_genre.text = genreString
        }
        description.text = mMovie.descriptionFull

        for (torrent in mMovie.torrents) mFiles.add(torrent)
        Log.d("files", mFiles.size.toString())
        downloadAdapter.notifyDataSetChanged()

        if (mMovie.cast != null) {
            for (cast in mMovie.cast) {
                val layout = LayoutInflater.from(this).inflate(R.layout.cast_layout, cast_container, false)
                val castNameView = layout.findViewById<TextView>(R.id.cast_name)
                val castRoleView = layout.findViewById<TextView>(R.id.cast_role)
                val castImageView = layout.findViewById<ImageView>(R.id.cast_image)
                castNameView.text = cast.name
                castRoleView.text = cast.characterName
                if (cast.urlSmallImage != null) {
                    Glide.with(this)
                            .load(Uri.parse(cast.urlSmallImage))
                            .into(castImageView)
                } else {
                    castImageView.visibility = View.GONE
                }
                cast_container.addView(layout)
            }
        } else {
            val textView = TextView(this)
            textView.text = "No Data Found"
            textView.textSize = 15.0F
            cast_container.addView(textView)
        }
    }

    private fun getMovieDetails() {
        val call = callMovieDetails()
        call.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>?, response: Response<MovieDetails>?) {
                mMovie = response?.body()!!.data.movie
                updateMoviesDetails()
            }

            override fun onFailure(call: Call<MovieDetails>?, t: Throwable?) {
                val alertDialog = AlertDialog.Builder(this@MovieDetailsActivity).create()
                alertDialog.setTitle("Network Error")
                alertDialog.setMessage("We are facing problem with your network. It seems like content is blocked. Please change your network.")
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                alertDialog.show()
            }

        })
    }

    private fun callMovieDetails(): Call<MovieDetails> {
        val call = client.getMovieDetails(mMovieId, true, true)
        return call
    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getApplicationInfo(packageName, 0).enabled
        } catch (ex: PackageManager.NameNotFoundException) {
            false
        }
    }

}
