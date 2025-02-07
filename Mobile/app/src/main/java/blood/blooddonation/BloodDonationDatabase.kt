package blood.blooddonation

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import blood.blooddonation.item.Item
import blood.blooddonation.item.ItemDao

@Database(entities = arrayOf(Item::class), version = 1)
abstract class BloodDonationDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: BloodDonationDatabase? = null

        fun getDatabase(context: Context): BloodDonationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    BloodDonationDatabase::class.java,
                    "bloodDonationDB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}