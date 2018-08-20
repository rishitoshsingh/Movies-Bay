package alphae.download.movies.moviesbay.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import alphae.download.movies.moviesbay.Api.Clients.YifyClient
import alphae.download.movies.moviesbay.Api.ServiceGenerator
import alphae.download.movies.moviesbay.BuildConfig
import alphae.download.movies.moviesbay.Fragments.ListFragment
import alphae.download.movies.moviesbay.POJOs.MovieList.MovieList
import alphae.download.movies.moviesbay.R
import alphae.download.movies.moviesbay.Utils.CrossfadeDrawer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ShareCompat
import android.support.v7.app.AlertDialog
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {

    private var actionSearchView: android.support.v7.widget.SearchView? = null
    private lateinit var mainFragment: ListFragment
    private var mMainInterstitialAd: InterstitialAd? = null
    override fun onDestroy() {
        super.onDestroy()
        mMainInterstitialAd = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this, BuildConfig.AdMobAppId)

        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
        window.navigationBarColor = resources.getColor(R.color.accent)

        val context = this
        val drawer = CrossfadeDrawer(context, my_toolbar, context, savedInstanceState, 1)
        drawer.getCrossfadeDrawer()
        drawer.expandDrawer()

        setSupportActionBar(my_toolbar)

        val mainFragment = ListFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, mainFragment)
                .disallowAddToBackStack()
                .commit()

        mMainInterstitialAd = InterstitialAd(this)
        mMainInterstitialAd?.adUnitId = BuildConfig.AdmobInterstisial
        mMainInterstitialAd?.loadAd(AdRequest.Builder().build())
        mMainInterstitialAd?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                if (mMainInterstitialAd != null) mMainInterstitialAd?.show()
            }
        }


        val extras = intent.extras
        if (extras != null) {
            try {
                val value1 = extras.getString(Intent.EXTRA_TEXT)
                if (value1 != null) {
                    val client = ServiceGenerator.createService(YifyClient::class.java)
                    val call = client.getMoviesFromId(value1)
                    call.enqueue(object :Callback<MovieList>{
                        override fun onFailure(call: Call<MovieList>?, t: Throwable?) {}
                        override fun onResponse(call: Call<MovieList>?, response: Response<MovieList>?) {
                            val body = response?.body()
                            if (body?.data?.movieCount == 1.toLong()){
                                val movie = body.data.movies[0]
                                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                                intent.putExtra("MovieId", movie.id)
                                intent.putExtra("MovieCover", movie.largeCoverImage)
                                startActivity(intent)
                            } else {
                                val alertDialog = AlertDialog.Builder(this@MainActivity).create()
                                alertDialog.setTitle("No Torrents Found")
                                alertDialog.setMessage("You have a great taste of movies. This movie is still not uploaded. Check back later.")
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                                alertDialog.show()
                            }
                        }
                    })
                }
            }catch (ex:Exception){
                Log.d("Intent",ex.toString())
            }
        }


        val towatchInstalled = isPackageInstalled("com.alphae.rishi.towatch", packageManager)

        if (!towatchInstalled) {
            val alertDialog = AlertDialog.Builder(this@MainActivity).create()
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



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)

        val searchItem = menu?.findItem(R.id.app_bar_search)
        actionSearchView = searchItem?.actionView as android.support.v7.widget.SearchView

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?) = true
            override fun onMenuItemActionCollapse(item: MenuItem?) = true
        })

        actionSearchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?) = false
            override fun onQueryTextSubmit(query: String?): Boolean {
                val query = actionSearchView?.query.toString()
                val bundle: Bundle = Bundle()
                Log.d("Search", query)
                bundle.putString("query", query)
                val searchFragment = ListFragment()
                searchFragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                        .replace(R.id.mainContainer, searchFragment)
                        .addToBackStack(null)
                        .commit()
                return false
            }
        })

        return true

    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getApplicationInfo(packageName, 0).enabled
        } catch (ex: PackageManager.NameNotFoundException) {
            false
        }
    }
}
