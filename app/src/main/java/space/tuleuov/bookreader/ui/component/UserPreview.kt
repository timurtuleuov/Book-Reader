package space.tuleuov.bookreader.ui.component

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import space.tuleuov.bookreader.ui.authorization.UserPreferences
import space.tuleuov.bookreader.ui.theme.SupportText

@Composable
fun UserPreview(app: Application, navController: NavController) {
    val userPreferences =  UserPreferences(app)
    val savedUser = userPreferences.getUser()
    Row(
        modifier = Modifier
            .size(width = 320.dp, height = 60.dp)
            .padding(0.dp)
    ) {
        savedUser?.avatar?.let { UserAvatar(it, savedUser.level) }
        if (savedUser != null) {
            UserName(savedUser.name)
        }
        Spacer(modifier = Modifier.width(100.dp))
        UserStatsButton(navController)
    }

}

@SuppressLint("ResourceType")
@Composable
fun UserAvatar(avatar: String, level: Int) {
    Card(
        modifier = Modifier
            .size(60.dp),
        shape = CircleShape,
        backgroundColor = Color.Black
    ) {
        if (avatar != "") {
            Image(
                painter = rememberImagePainter(avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painterResource(id = R.drawable.ava),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Card(
            modifier = Modifier.padding(30.dp)
        ) {
            Text(text = "$level", fontSize = 20.sp)
        }
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
fun UserStatsButton(navController: NavController){
    Card(
        modifier = Modifier
            .size(50.dp),
        shape = CircleShape,
        elevation = 0.dp

    ) {
        IconButton(onClick = { navController.navigate("userPage") }) {
            Image(
                painterResource(id = R.drawable.setting),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

    }
}