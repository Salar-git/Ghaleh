package ir.iamsalar.ghaleh

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import ir.iamsalar.ghaleh.data.db.DB
import ir.iamsalar.ghaleh.utilities.Prefs

val prefs: Prefs by lazy {
    App.prefss!!
}
class App : Application() {

    val database: DB by lazy {
        DB.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefss = Prefs(applicationContext)

    }
    companion object {
        var prefss: Prefs? = null

        lateinit var instance: App
            private set
    }
}
