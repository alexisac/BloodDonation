package blood.blooddonation.schedules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blood.blooddonation.R
import blood.blooddonation.model.ScheduleCenter
import blood.blooddonation.ui.theme.AppColor
import kotlin.math.roundToInt

@Composable
fun SchedulesCard(
    scheduleCenter: ScheduleCenter
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(AppColor.WhiteLevenderBlush)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = scheduleCenter.date,
                color = AppColor.BlackSoot,
                style = TextStyle(
                    fontSize = 20.sp
                )
            )
            Text(
                text = scheduleCenter.centerName,
                color = AppColor.BlackSoot,
                style = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
    }
}