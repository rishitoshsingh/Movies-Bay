package alphae.download.movies.moviesbay.Fragments


import alphae.download.movies.moviesbay.Adapters.MovieAdapter
import alphae.download.movies.moviesbay.Api.Clients.YifyClient
import alphae.download.movies.moviesbay.Api.ServiceGenerator
import alphae.download.movies.moviesbay.BuildConfig
import alphae.download.movies.moviesbay.Listners.PaginationScrollListner
import alphae.download.movies.moviesbay.POJOs.MovieList.MovieList
import alphae.download.movies.moviesbay.R
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.ads.AdError
import com.facebook.ads.NativeAdsManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.recycler_view.*
import retrofit2.Call
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private var moviesList: ArrayList<kotlin.Any> = ArrayList<kotlin.Any>()
    private lateinit var client: YifyClient
    private val PAGE_START = 1
    private var isLoading = false
    private var isLastPage = false
    private var TOTAL_PAGES = 2
    private var currentPage = PAGE_START
    private lateinit var viewAdapter: MovieAdapter
    private lateinit var viewManager: GridLayoutManager

    private var minimumRating: String? = null
    private var genre: String? = null
    private var sortBy: String? = null
    private var orderBy: String? = null
    private var query: String? = null

    private var lastAdPosition: Int = -1
    private val ADS_PER_ITEMS: Int = 9
    private var mInterstitialAd: InterstitialAd? = null

    companion object {
        fun newInstance() = ListFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        mInterstitialAd = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mInterstitialAd = InterstitialAd(this.context)
        mInterstitialAd!!.adUnitId = BuildConfig.AdmobInterstisial

        minimumRating = arguments?.getString("minimumRating", null)
        genre = arguments?.getString("genre", null)
        sortBy = arguments?.getString("sortBy", null)
        orderBy = arguments?.getString("orderBy", null)
        query = arguments?.getString("query", null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        client = ServiceGenerator.createService(YifyClient::class.java)

        mInterstitialAd?.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd?.loadAd(AdRequest.Builder().build())
            }
        }
        mInterstitialAd?.loadAd(AdRequest.Builder().build())


        viewManager = GridLayoutManager(context, 2)
        viewManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (viewAdapter.getItemViewType(position)) {
                    viewAdapter.MOVIE -> 1
                    viewAdapter.NATIVE_AD -> (viewManager).spanCount
                    else -> 1
                }
            }
        }
        viewAdapter = MovieAdapter(context!!, moviesList)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            itemAnimator = DefaultItemAnimator()
        }
        recyclerView.addOnScrollListener(object : PaginationScrollListner(viewManager) {
            override fun getCurrentPage() = currentPage
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                if (refresh_layout != null) {
                    refresh_layout.isRefreshing = true
                }
                loadNextPage()
            }

            override fun getTotalPageCount() = TOTAL_PAGES
            override fun isLastPage() = isLastPage
            override fun isLoading() = isLoading
        })
        if (refresh_layout != null) {
            refresh_layout.isRefreshing = true
        }
        loadFirstPage()
        refresh_layout.setOnRefreshListener {
            isLoading = false
            isLastPage = false
            TOTAL_PAGES = 2
            currentPage = PAGE_START
            loadFirstPage()
            refresh_layout.isRefreshing = false
        }
    }


    private fun loadAdsToList() {
        try {
            val nativeAdsManager = NativeAdsManager(activity!!, BuildConfig.FanNative, 2)
            nativeAdsManager.setListener(object : NativeAdsManager.Listener {
                override fun onAdError(adError: AdError) {}
                override fun onAdsLoaded() {
                    try {
                        while (lastAdPosition + ADS_PER_ITEMS < moviesList.size) {
                            val nextNativeAd = nativeAdsManager.nextNativeAd()
                            lastAdPosition += ADS_PER_ITEMS
                            Log.d("AdLoaded fb", "1")
                            moviesList.add(lastAdPosition, nextNativeAd)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("Error Ad fb", e.toString())
                    }
                    viewAdapter.notifyDataSetChanged()
                }
            })
            nativeAdsManager.loadAds()
        } catch (e: Exception) {
            val str = "TAG"
            val stringBuilder = StringBuilder()
            stringBuilder.append("loadAdsToList: ")
            stringBuilder.append(e.toString())
            Log.e(str, stringBuilder.toString())
        }
    }

    private fun loadFirstPage() {
        val call = callMovieList()
        call.enqueue(object : retrofit2.Callback<MovieList> {
            override fun onFailure(p0: Call<MovieList>?, p1: Throwable?) {
                Log.v("loadFirstPage()", "failed", p1)
                if (refresh_layout != null) {
                    refresh_layout.isRefreshing = false
                }
                val alertDialog = context?.let { AlertDialog.Builder(it).create() }
                alertDialog?.setTitle("Network Error")
                alertDialog?.setMessage("We are facing problem with your network. It seems like content is blocked. Please change your network.")
                alertDialog?.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                if (mInterstitialAd != null && mInterstitialAd!!.isLoaded) {
                    mInterstitialAd!!.show()
                }
                alertDialog?.show()
                Toast.makeText(activity, "Change Network", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(p0: Call<MovieList>?, p1: Response<MovieList>?) {
                Log.d("response", p1.toString())
                if (mInterstitialAd != null && mInterstitialAd?.isLoaded!!) {
                    mInterstitialAd?.show()
                }
                try {
                    val resultBody: MovieList? = p1?.body()!!
                    val movieCount = resultBody?.data?.movieCount
                    val limit = resultBody?.data?.limit
                    TOTAL_PAGES = (movieCount?.div(limit!!))?.toInt()!! + 1

                    moviesList.clear()
                    try {
                        for (item in resultBody.data.movies) moviesList.add(item)
                        viewAdapter.notifyDataSetChanged()
                        loadAdsToList()
                        isLoading = false
                        if (refresh_layout != null) {
                            refresh_layout.isRefreshing = false
                        }
                    } catch (ex: Exception) {
                        isLoading = false
                        isLastPage = true
                        Log.d("loadFirstPage", ex.toString())
                    }

                } catch (ex: Exception) {
                    isLoading = false
                    isLastPage = true
                    Log.d("loadFirstPage", ex.toString())
                }

            }
        })


    }

    private fun loadNextPage() {

        val call = callMovieList()
        call.enqueue(object : retrofit2.Callback<MovieList> {
            override fun onFailure(p0: Call<MovieList>?, p1: Throwable?) {
                Log.v("temp", "failed Next Page", p1)
            }

            override fun onResponse(p0: Call<MovieList>?, p1: Response<MovieList>?) {
                val resultBody: MovieList? = p1?.body()!!
                try {
                    for (item in resultBody?.data?.movies!!) moviesList.add(item)
                    viewAdapter.notifyDataSetChanged()
                    loadAdsToList()
                    isLoading = false
                    if (refresh_layout != null) {
                        refresh_layout.isRefreshing = false
                    }
                } catch (ex: Exception) {
                    isLoading = false
                    isLastPage = true
                    Log.d("loadNextPage", ex.toString())
                }
            }
        })

    }

    private fun callMovieList(): Call<MovieList> {

        val call = client.getMoviesList(
                currentPage,
                minimumRating,
                genre,
                sortBy,
                orderBy,
                query
        )
        return call
    }

}
