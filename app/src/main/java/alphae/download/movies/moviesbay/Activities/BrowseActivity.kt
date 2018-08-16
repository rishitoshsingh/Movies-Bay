package alphae.download.movies.moviesbay.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import alphae.download.movies.moviesbay.Fragments.ListFragment
import alphae.download.movies.moviesbay.R
import alphae.download.movies.moviesbay.Utils.CrossfadeDrawer
import kotlinx.android.synthetic.main.activity_browse.*


class BrowseActivity : AppCompatActivity() {

    private var genreArray: ArrayList<String> = ArrayList()
    private var sortByArray: ArrayList<String> = ArrayList()
    private var orderByArray: ArrayList<String> = ArrayList()
    private var ratingsArray: ArrayList<String> = ArrayList()
    private val arguments = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
        window.navigationBarColor = resources.getColor(R.color.colorAccent)
        val context = this

        val drawer = CrossfadeDrawer(context, browse_toolbar, context, savedInstanceState, 2)
        drawer.getCrossfadeDrawer()

        setSupportActionBar(browse_toolbar)

        resources.getStringArray(R.array.genre_array).toCollection(genreArray)
        resources.getStringArray(R.array.sort_by_array).toCollection(sortByArray)
        resources.getStringArray(R.array.order_by_array).toCollection(orderByArray)
        resources.getStringArray(R.array.rating_array).toCollection(ratingsArray)

        genre_spinner.setItems(genreArray)
        sort_spinner.setItems(sortByArray)
        order_spinner.setItems(orderByArray)
        rating_spinner.setItems(ratingsArray)
        genre_spinner.setOnItemSelectedListener { view, position, id, item ->
            if (position != 0) {
                val selected = item.toString().toLowerCase()
                arguments.putString("genre", selected)
            }
        }
        sort_spinner.setOnItemSelectedListener { view, position, id, item ->
            if (position != 0) {
                val selected = item.toString().toLowerCase().replace(" ", "_")
                arguments.putString("sortBy", selected)
            }
        }
        order_spinner.setOnItemSelectedListener { view, position, id, item ->
            if (position == 0) arguments.putString("orderBy", "desc")
            else if (position == 1) arguments.putString("orderBy", "asc")
        }
        rating_spinner.setOnItemSelectedListener { view, position, id, item ->
            if (position != 0) {
                val selected = item.toString()[0].toString()
                arguments.putString("minimumRating", selected)
            }
        }

        browse_button.setOnClickListener {
            browse_layout.visibility = View.GONE
            val fragment = ListFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (browse_layout.visibility == View.GONE) browse_layout.visibility = View.VISIBLE
    }
}