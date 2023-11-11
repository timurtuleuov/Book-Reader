package space.tuleuov.bookreader.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean>
        get() = _isUserLoggedIn

    init {
        // Здесь вы можете выполнить проверку состояния авторизации, например, из SharedPreferences
        // Предположим, что true означает, что пользователь авторизован
        _isUserLoggedIn.value = checkUserAuthenticationStatus()
    }

    fun loginUser() {
        // Логика входа пользователя
        // После успешного входа обновите состояние
        _isUserLoggedIn.value = true
    }

    fun logoutUser() {
        // Логика выхода пользователя
        // После успешного выхода обновите состояние
        _isUserLoggedIn.value = false
    }

    private fun checkUserAuthenticationStatus(): Boolean {
        // Логика проверки состояния авторизации, например, из SharedPreferences
        // Возвращайте true, если пользователь авторизован, иначе false
        return false
    }
}
