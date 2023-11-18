package space.tuleuov.bookreader.authorization

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State

import androidx.lifecycle.ViewModel
import space.tuleuov.bookreader.ui.authorization.data.LoginState
import space.tuleuov.bookreader.ui.authorization.data.TextFieldState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import space.tuleuov.bookreader.BookReaderApp
import space.tuleuov.bookreader.db.Database
import space.tuleuov.bookreader.db.entity.User
import space.tuleuov.bookreader.ui.authorization.data.RegistrationState
import javax.inject.Inject


//class AuthViewModel constructor(private val userRepository: UserRepository) : ViewModel() {

class AuthViewModel(
    app: Application
) : AndroidViewModel(app) {
    private val db = (app as BookReaderApp).database


    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState(isPassword = true))
    val passwordState: State<TextFieldState> = _passwordState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState
    private val _nameState = mutableStateOf(TextFieldState())
    val nameState: State<TextFieldState> = _nameState


    private val _confirmPasswordState = mutableStateOf(TextFieldState(isPassword = true))
    val confirmPasswordState: State<TextFieldState> = _confirmPasswordState

    private val _registrationState = mutableStateOf(RegistrationState())
    val registrationState: State<RegistrationState> = _registrationState

    // Другие необходимые методы для управления состоянием

    fun setName(name: String) {
        _nameState.value = _nameState.value.copy(text = name, error = null)
    }

    fun setEmail(email: String) {
        _emailState.value = _emailState.value.copy(text = email, error = null)
    }

    fun setPassword(password: String) {
        _passwordState.value = _passwordState.value.copy(text = password, error = null)
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPasswordState.value = _confirmPasswordState.value.copy(text = confirmPassword, error = null)
    }

    fun registerUser(name: String, email: String, password: String) {
        val newUser = User(uid = 1, name = name, email = email, password = password, level=1, avatar = "", status = "")
        db.userDao().insert(newUser)


        // Логика регистрации пользователя
    }
    fun loginUser(email: String, password: String): User? {
        // Логика аутентификации
        val user = db.userDao().getUserByEmailAndPassword(email, password)
        if (user != null){
            println("Все четко")
        }
        return user
    }
}

