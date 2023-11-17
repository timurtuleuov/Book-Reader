package space.tuleuov.bookreader.authorization

import androidx.compose.runtime.State

import androidx.lifecycle.ViewModel
import space.tuleuov.bookreader.ui.authorization.data.LoginState
import space.tuleuov.bookreader.ui.authorization.data.TextFieldState
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import space.tuleuov.bookreader.db.entity.User
import space.tuleuov.bookreader.db.repositiry.UserRepository
import space.tuleuov.bookreader.ui.authorization.data.RegistrationState
import javax.inject.Inject


//class AuthViewModel constructor(private val userRepository: UserRepository) : ViewModel() {
class AuthViewModel constructor() : ViewModel() {

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

    fun registerUser() {
        // Логика регистрации пользователя
    }
    fun loginUser(email: String, password: String) {
        // Логика аутентификации
//        suspend fun loginUser(email: String, password: String): User? {
//            return userRepository.loginUser(email, password)
//        }
    }
}
