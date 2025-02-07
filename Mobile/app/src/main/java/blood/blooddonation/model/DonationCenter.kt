package blood.blooddonation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donationCenters")
data class DonationCenter (
    @PrimaryKey var id: String,
    var name: String,
    var latitude: String,
    var longitude: String
)