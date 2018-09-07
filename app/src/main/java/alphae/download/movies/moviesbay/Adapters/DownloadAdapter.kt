package alphae.download.movies.moviesbay.Adapters

import alphae.download.movies.moviesbay.BuildConfig
import alphae.download.movies.moviesbay.POJOs.MovieDetails.Torrent
import alphae.download.movies.moviesbay.R
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import java.net.URLEncoder


/**
 * Created by rishi on 6/7/18.
 */
abstract class DownloadAdapter(context: Context, files: ArrayList<Torrent>, color: Int) : RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>() {

    val mContext = context
    val mFiles = files
    val mColor = color
    private var downloadJob: Int = 0
    private lateinit var mFile: alphae.download.movies.moviesbay.POJOs.MovieDetails.Torrent

    override fun getItemCount() = mFiles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.download_layout, parent, false)
        return DownloadViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {

        mFile = mFiles[position]

        holder.downloadRootLayout.backgroundTintList = ColorStateList.valueOf(mColor)
        holder.movieQualityTextView.text = mFile.quality
        holder.movieSizeTextView.text = mFile.size
        holder.torrentSeedsView.text = mFile.seeds.toString()
        holder.torrentPeersView.text = mFile.peers.toString()

        holder.movieMagnetButton.setOnClickListener {
            downloadJob = 0
            magnet()

        }
        holder.movieTorrentButton.setOnClickListener {
            downloadJob = 1
            torrent()
        }
    }

    abstract fun getMovieName(): String

    inner class DownloadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var downloadRootLayout: LinearLayout = view.findViewById(R.id.download_root)
        var movieQualityTextView: TextView = view.findViewById(R.id.movie_quality)
        var movieSizeTextView: TextView = view.findViewById(R.id.file_size)
        var movieMagnetButton: RelativeLayout = view.findViewById(R.id.magnet_button)
        var movieTorrentButton: RelativeLayout = view.findViewById(R.id.torrent_button)
        var torrentSeedsView: TextView = view.findViewById(R.id.torrent_seeds)
        var torrentPeersView: TextView = view.findViewById(R.id.torrent_peers)
    }


    private fun magnet() {
        val magnetUrl = StringBuilder()
        getMovieName()
        magnetUrl.append("magnet:?xt=urn:btih:")
        magnetUrl.append(mFile.hash)
        magnetUrl.append("&dn=")
        magnetUrl.append(URLEncoder.encode(getMovieName(), "UTF-8"))
//            magnetUrl.append(URLEncoder.encode(mMovieName, AudienceNetworkActivity.WEBVIEW_ENCODING))
        magnetUrl.append("&tr=udp%3A%2F%2Fglotorrents.pw%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fp4p.arenabg.ch%3A1337&tr=udp%3A%2F%2Ftracker.internetwarriors.net%3A1337")

        val intent: Intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(magnetUrl.toString())
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(magnetUrl.toString())
        val chooser = Intent.createChooser(intent, "Select Application")
        if (intent.resolveActivity(mContext.packageManager) != null) {
            mContext.startActivity(chooser)
        } else {
            val alertDialog = AlertDialog.Builder(mContext).create()
            alertDialog.setTitle("No App Found")
            alertDialog.setMessage("No Supported Torrent App found. We recommend Flud")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Download",
                    DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.delphicoder.flud"))
                        mContext.startActivity(intent)
                        dialog.dismiss()
                    })
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            alertDialog.show()
        }
    }

    private fun torrent() {
        val intent: Intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.setDataAndType(Uri.parse(mFile.url), "application/x-bittorrent")
        val chooser = Intent.createChooser(intent, "Select Application")
        if (intent.resolveActivity(mContext.packageManager) != null) {
            mContext.startActivity(chooser)
        } else {
            val alertDialog = AlertDialog.Builder(mContext).create()
            alertDialog.setTitle("No App Found")
            alertDialog.setMessage("No Supported Torrent App found. We recommend Flud")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Download",
                    DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.delphicoder.flud"))
                        mContext.startActivity(intent)
                        dialog.dismiss()
                    })
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            alertDialog.show()
        }
    }

}