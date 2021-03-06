package alphae.download.movies.moviesbay.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import alphae.download.movies.moviesbay.Activities.BrowseActivity
import alphae.download.movies.moviesbay.Activities.MainActivity
import alphae.download.movies.moviesbay.R
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout
import com.mikepenz.materialdrawer.*
import com.mikepenz.materialdrawer.interfaces.ICrossfader
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerUIUtils
import com.mikepenz.materialize.util.UIUtils
import java.util.*


/**
 * Created by rishi on 24/6/18.
 */
class CrossfadeDrawer(context: Context, toolbar: Toolbar, activity: Activity, savedInstanceState: Bundle?, selected: Long) {

    private val mContext = context
    private val mToolbar = toolbar
    private val mActivity = activity
    private val mSavedInstanceState = savedInstanceState
    private val mCrossfadeDrawerLayout = CrossfadeDrawerLayout(mContext)
    private lateinit var mSharedPreferences: SharedPreferences
    private val mSelected: Long = selected
    private lateinit var drawerResult: Drawer

    companion object {
        val rand = Random()
    }

    var item1 = PrimaryDrawerItem().withIdentifier(1).withName(R.string.home).withSelectable(false).withIcon(R.drawable.ic_home_white_48dp).withIconTintingEnabled(true)
    var item2 = PrimaryDrawerItem().withIdentifier(2).withName(R.string.browse).withSelectable(false).withIcon(R.drawable.ic_theaters_white_48dp).withIconTintingEnabled(true)
//    var item3 = PrimaryDrawerItem().withIdentifier(3).withName(R.string.more_apps).withSelectable(false).withIcon(R.drawable.ic_work_white_48dp).withIconTintingEnabled(true)

    fun getCrossfadeDrawer() {

//        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
//            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?) {
//                Picasso.get().load(uri).placeholder(placeholder!!).into(imageView)
//                super.set(imageView, uri, placeholder)
//            }
//
//            override fun cancel(imageView: ImageView?) {
//                Picasso.get().cancelRequest(imageView!!)
//                super.cancel(imageView)
//            }
//        })

//        mSharedPreferences = mContext.getSharedPreferences("Account", Context.MODE_PRIVATE)
//        val account: Boolean = mSharedPreferences.getBoolean("account", false)
//        val givenName: String = mSharedPreferences.getString("GivenName", "Please Log In")
//        val displayName = mSharedPreferences.getString("DisplayName", "Log In")
//        val email: String = mSharedPreferences.getString("Email", "")
////        mSharedPreferences.getString("Id", account.id)
//        val photoUrl: String = mSharedPreferences.getString("PersonPhotoUrl", "null")
//        Log.d("URL", photoUrl)
//        val profile: ProfileDrawerItem
//        if (account) {
//            profile = ProfileDrawerItem()
//                    .withName(displayName)
//                    .withEmail(email)
//                    .withIcon(Uri.parse(photoUrl))
//                    .withNameShown(true)
//        } else {
//            profile = ProfileDrawerItem()
//                    .withName("Log In")
//                    .withEmail("")
//                    .withIcon(R.drawable.ic_account_circle_white_48dp)
//                    .withNameShown(true)
//        }

//        val headerResult = AccountHeaderBuilder()
//                .withActivity(mActivity)
////                .addProfiles(profile)
//                .withHeaderBackground(R.drawable.backdrop_one)
//                .withOnAccountHeaderListener(AccountHeader.OnAccountHeaderListener { view, profile, currentProfile ->
////                    if (profile.name.toString() == "Log In") {
////                        val intent = Intent(mContext, SignUpActivity::class.java)
////                        mContext.startActivity(intent)
////                    }
//                    true
//                })
//                .build()

        val result = DrawerBuilder()
                .withActivity(mActivity)
                .withToolbar(mToolbar)
                .withDrawerLayout(mCrossfadeDrawerLayout)
                .withHasStableIds(true)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
//                .addDrawerItems(item1, item2, item3)
                .addDrawerItems(item1, item2)
                .withSelectedItem(mSelected)
//the listener which is called when an item inside the drawer or miniDrawer is clicked
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    drawerItem.identifier
                    if (drawerItem.identifier != mSelected) {
                        if (drawerItem.identifier == 1.toLong()) {
                            val intent: Intent
                            intent = Intent(mContext, MainActivity::class.java)
                            mContext.startActivity(intent)
                        } else if (drawerItem.identifier == 2.toLong()) {
                            val intent: Intent
                            intent = Intent(mContext, BrowseActivity::class.java)
                            mContext.startActivity(intent)
                        }  else if (drawerItem.identifier == 3.toLong()) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=AlphaE"))
                            mContext.startActivity(intent)
                        }
//                        else if (drawerItem.identifier == 4.toLong())    intent = Intent(mContext,DiscoverActivity::class.java)
                    }
                    true
                }
                .withSavedInstance(mSavedInstanceState)
//                .withAccountHeader(headerResult)
                .build()

        drawerResult = result

//define maxDrawerWidth (this is the width in the complete opened state)
        mCrossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(mContext))

//add second view (which is the miniDrawer)
        val miniResult: MiniDrawer = result.miniDrawer
//build the view for the MiniDrawer
        val view: View = miniResult.build(mContext)
//set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(mContext,com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background))
//we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        mCrossfadeDrawerLayout.smallView.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

//define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(object : ICrossfader {
            override fun isCrossfaded(): Boolean {
                return mCrossfadeDrawerLayout.isCrossfaded;
            }

            override fun crossfade() {
                mCrossfadeDrawerLayout.crossfade(400)
                //only close the drawer if we were already faded and want to close it now
                if (isCrossfaded) {
                    result.drawerLayout.closeDrawer(GravityCompat.START)
                }

            }
        })
    }

    fun expandDrawer() {
        drawerResult.drawerLayout.openDrawer(GravityCompat.START)
    }
}
