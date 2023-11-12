package space.tuleuov.bookreader.ui.authorization.data

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false
)