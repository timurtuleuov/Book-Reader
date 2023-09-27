package space.tuleuov.bookreader.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import space.tuleuov.bookreader.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import space.tuleuov.bookreader.ui.theme.SupportText

@Composable
fun UserPreview() {
    Row(
        modifier = Modifier
            .size(width = 320.dp, height = 60.dp)
            .padding(0.dp)
    ) {
        UserAvatar()
        UserName("Timur Tuleuov")
        Spacer(modifier = Modifier.width(100.dp))
        UserStatsButton()
    }

}

@Composable
fun UserAvatar() {
    Card(
        modifier = Modifier
            .size(60.dp),
            shape = CircleShape,
        backgroundColor = Color.Black
    ) {
        Image(
            painterResource(id = R.drawable.ava),
            contentDescription = "",
            contentScale = ContentScale.Crop
            )
    }
}

@Composable
fun UserName(name: String) {
    Card(
        modifier = Modifier
            .padding(5.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text="Stay trending", fontSize = 12.sp, fontWeight = FontWeight.Normal, color = SupportText)
            Text(text=name, fontSize = 16.sp, fontWeight = FontWeight.Normal)
        }
    }
}

@Composable
fun UserStatsButton(){
    Card(
        modifier = Modifier
            .size(50.dp),
        shape = CircleShape,
        elevation = 0.dp

    ) {
        Image(
            painterResource(id = R.drawable.setting),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}