package space.tuleuov.bookreader.ui.screens

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import space.tuleuov.bookreader.R
import space.tuleuov.bookreader.ui.authorization.UserPreferences
import space.tuleuov.bookreader.ui.theme.SupportText
import java.io.IOException
import java.util.Collections.rotate

@Composable
fun CardWithAnimatedBorder(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
    borderColors: List<Color> = emptyList(),
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush =
        if (borderColors.isNotEmpty()) Brush.sweepGradient(borderColors)
        else Brush.sweepGradient(listOf(Color.Gray, Color.White))

    Surface(modifier = modifier.clickable { onCardClick() }, shape = RoundedCornerShape(20.dp)) {
        Surface(
            modifier =
            Modifier
                .clipToBounds()

                .padding(2.dp)
                .drawWithContent {
                    rotate(angle) {
                        drawCircle(
                            brush = brush,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            shape = RoundedCornerShape(19.dp)
        ) {
            Box(modifier = Modifier.padding(8.dp), ) { content() }
        }
    }
}

@Composable
fun StatsContent(){
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Количество часов за чтением", fontSize = 24.sp, textAlign = TextAlign.Center)
        Text(text = "987 часов", color = Color(0xFFFFC802))
        Text(text = "Количество прочитанных книг", fontSize = 24.sp, textAlign = TextAlign.Center)
        Text(text="496 книги", color = Color(0xFFFFC802))
        Text(text="Количество прочитанных страниц", fontSize = 24.sp, textAlign = TextAlign.Center)
        Text(text="34353253 страниц", color = Color(0xFFFFC802))
        Text(text="Среднее время на чтение 1 книги", fontSize = 24.sp, textAlign = TextAlign.Center)
        Text(text = "4 часов 23 минуты", color = Color(0xFFFFC802))

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserProfile(navController: NavController){
    val app = LocalContext.current.applicationContext as Application
    val userPreferences =  UserPreferences(app)
    val savedUser = userPreferences.getUser()
    val context  = LocalContext.current
    val contentResolver = context.contentResolver

    val bitmap: Bitmap? = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    contentResolver,
                    Uri.parse(savedUser?.avatar)
                )
            )
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(savedUser?.avatar))
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(bitmap) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color.White
                        )

                    }
                },
                title = { Text(text = "Страница пользователя", color = Color.White)},
                contentColor = LocalContentColor.current
            )
        }
    )
    {
        Column(
            modifier = Modifier.padding(20.dp),


        ) {
            Row() {
                Card(
                    modifier = Modifier
                        .size(120.dp),
                    shape = CircleShape,
                    backgroundColor = Color.Black
                ) {
                    if (savedUser?.avatar?.isNotEmpty() == true) {
                        println("$imageBitmap это имаге битмап")
                        Image(
                            painter = rememberAsyncImagePainter(imageBitmap),
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
                }
                Spacer(modifier = Modifier.width(40.dp))
                Card(
                    modifier = Modifier
                        .padding(5.dp),
                    backgroundColor = MaterialTheme.colors.background,
                    elevation = 0.dp

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top=20.dp)
                    ) {
                        Text(text="Stay trending", fontSize = 12.sp, fontWeight = FontWeight.Normal, color = SupportText)
                        savedUser?.name?.let { it1 -> Text(text= it1, fontSize = 24.sp, fontWeight = FontWeight.Normal) }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate("changeUserData")}, modifier = Modifier.fillMaxWidth().padding(5.dp), shape = RoundedCornerShape(20.dp)) {
                Text(text = "Редактировать", textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Статистика", fontSize = 24.sp, textAlign = TextAlign.Center)
            CardWithAnimatedBorder(borderColors =
            listOf(
                Color(0xFFFF595A),
                Color(0xFFFFC766),
                Color(0xFF35A07F),
                Color(0xFF35A07F),
                Color(0xFFFFC766),
                Color(0xFFFF595A)
            ), content = { StatsContent() })


        }
    }


}
