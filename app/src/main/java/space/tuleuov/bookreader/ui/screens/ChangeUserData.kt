package space.tuleuov.bookreader.ui.screens

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import space.tuleuov.bookreader.BookReaderApp
import space.tuleuov.bookreader.db.entity.User
import space.tuleuov.bookreader.ui.authorization.UserPreferences

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun ChangeUserData(navController: NavController){

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
                title = { Text(text = "Редактировать профиль", color = Color.White) },
                contentColor = LocalContentColor.current
            )
        }
    )
    {
        val app = LocalContext.current.applicationContext as Application
        val userPreferences =  UserPreferences(app)
        var savedUser: User? = userPreferences.getUser()
        val context  = LocalContext.current
        val db = (app as BookReaderApp).database
        var showDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember { mutableStateOf(false) }
        var dialogState by remember { mutableStateOf("") }
        val pickImage = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->

            if (uri != null) {
                val uriString = uri.toString()

                    context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                savedUser?.avatar = uriString
                savedUser?.let { it1 -> db.userDao().update(it1) }
                val user = db.userDao().getUserByEmail(savedUser?.email.toString())
                if (user != null) {
                    userPreferences.saveUser(user)
                }


            }
        }

        Column() {
            Spacer(modifier = Modifier.height(20.dp))
            //profile
            Column() {
                Text(text = "Профиль", fontSize = 18.sp, modifier = Modifier.padding(start = 15.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        savedUser = null
                        navController.navigate("login")
                    }) {
                    Text(text = "Выйти из учетной записи", fontSize = 18.sp, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 15.dp))
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        pickImage.launch("image/*")
                    })) {
                    Text(text = "Изменить фото профиля", fontSize = 18.sp, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 15.dp))
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        showDialog = true;
                        dialogState = "Имя"

                    })) {
                    if (showDialog) {
                        changeName(dialogState, onDismiss = { showDialog = false })
                    }
                    Text(text = "Изменить имя пользователя", fontSize = 18.sp, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 15.dp))
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            //security
            Column() {
                Text(text = "Безопасность", fontSize = 18.sp, modifier = Modifier.padding(start = 15.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        showDialog = true;
                        dialogState = "Email"

                    })) {
                    if (showDialog) {
                        changeName(dialogState, onDismiss = { showDialog = false })
                    }
                    Text(text = "Изменить Email", fontSize = 18.sp, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 15.dp))
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        showDialog = true;
                        dialogState = "Пароль"

                    })) {
                    if (showDialog) {
                        changeName(dialogState, onDismiss = { showDialog = false })
                    }
                    Text(text = "Изменить пароль", fontSize = 18.sp, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 15.dp))
                }

            }
        }

    }
}

@Composable
fun changeName(state: String,onDismiss: () -> Unit){
    val app = LocalContext.current.applicationContext as Application
    val db = (app as BookReaderApp).database
    var changeState by remember {
        mutableStateOf("")
    }
    val userPreferences =  UserPreferences(app)
    var savedUser: User? = userPreferences.getUser()
    var emailState by remember {
        mutableStateOf("")
    }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Изменить значение ${state}") },
        text = {
            Column {
                TextField(
                    value = changeState,
                    onValueChange = { changeState = it },
                    label = { Text(state) }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (state == "Имя"){
                        savedUser?.name = changeState
                        emailState = savedUser?.email.toString()
                    } else if (state == "Email") {
                        savedUser?.email  = changeState
                        emailState = changeState
                    } else if (state == "Пароль") {
                        savedUser?.password = changeState
                        emailState = savedUser?.email.toString()
                    }

                    if (savedUser != null) {
                        db.userDao().update(savedUser)
                        val user = db.userDao().getUserByEmail(emailState)
                        if (user != null) {
                            userPreferences.saveUser(user)
                        }
                    }

                    onDismiss()
                }
            ) {
                Text(text = "Сохранить")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Отмена")
            }
        }
    )
}