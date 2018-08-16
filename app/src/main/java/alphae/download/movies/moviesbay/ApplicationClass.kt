package alphae.download.movies.moviesbay

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.onesignal.OneSignal

/**
 * Created by rishi on 14/8/18.
 */
class ApplicationClass: Application() {

    override fun onCreate() {
        super.onCreate()

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}