package space.tuleuov.bookreader.ui.authorization

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Visibility
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import kotlinx.coroutines.flow.collectLatest
import space.tuleuov.bookreader.authorization.AuthViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()) {
    val context  = LocalContext.current
    val emailState by viewModel.emailState
    val passwordState by viewModel.passwordState
    val loginState by viewModel.loginState
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ваши элементы UI
                if (loginState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Book Reader",
                    fontSize = 26.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Добро пожаловать",
                    fontSize = 19.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                // Пример использования OutlinedTextField для email
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = emailState.text,
                    onValueChange = { viewModel.setEmail(it) },
                    label = { Text("Email") },
                    isError = emailState.error != null
                )

                // Пример использования TextField для password
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwordState.text,
                    onValueChange = {
                        viewModel.setPassword(it)
                    },
                    label = { Text("Пароль") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = passwordState.error != null,
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.AccountBox
                        else Icons.Filled.Lock
                        // Localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        // Toggle button to hide or display password
                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(imageVector  = image, description)
                        }
                    }
                )


                // Пример обработки состояния входа
                when {
                    loginState.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    loginState.isSuccess -> {
                        Text("Login successful!", color = Color.Green)
                    }
                    loginState.isError -> {
                        Text("Login failed. Check your credentials.", color = Color.Red)
                    }
                }
                TextButton(
                    onClick = {
                        navController.popBackStack()
                        navController.navigate("registration")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(color = Color.Black)
                            ) {
                                append("Нет аккаунта?")
                            }
                            append(" ")
                            withStyle(
                                style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)
                            ) {
                                append("Зарегистрируйся")
                            }
                        },
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center
                    )
                }
                // Кнопка для входа
                Button(
                    onClick = {
                        val user = viewModel.loginUser(emailState.text, passwordState.text)
                        if (user == null) {
                            Toast.makeText(context, "Такого пользователя нет", Toast.LENGTH_LONG).show()
                        } else {
                            navController.navigate("mainPage")
                        }},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Войти")
                }
            }
        }
    )
}
