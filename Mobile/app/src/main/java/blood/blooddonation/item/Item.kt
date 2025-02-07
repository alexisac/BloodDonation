package blood.blooddonation.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(@PrimaryKey val _id: String = "", val code: Int = 0, val name: String = "", val cantitate: Int = 0)