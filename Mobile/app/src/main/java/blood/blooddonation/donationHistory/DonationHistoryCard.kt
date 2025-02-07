package blood.blooddonation.donationHistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blood.blooddonation.model.DonationHistoryEntry
import blood.blooddonation.ui.theme.AppColor

@Composable
fun DonationHistoryCard(donationHistoryEntry: DonationHistoryEntry) {
    val backgroundColor = when (donationHistoryEntry.donationType.intValue) {
        1 -> AppColor.RedFirebrick
        2 -> AppColor.RedLipstick
        3 -> AppColor.RedCarnelian
        else -> AppColor.RedGrenaline
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(backgroundColor),
        shape = RoundedCornerShape(36.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = donationHistoryEntry.donationType.displayName,
                color = AppColor.WhiteLevenderBlush,
                style = TextStyle(
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = donationHistoryEntry.donationDate,
                color = AppColor.WhiteLevenderBlush,
                style = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
    }
}