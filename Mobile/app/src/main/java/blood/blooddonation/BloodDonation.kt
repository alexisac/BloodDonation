package blood.blooddonation
import android.app.Application;
import blood.blooddonation.AppContainer

class BloodDonation : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
