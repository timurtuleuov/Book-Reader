package space.tuleuov.bookreader.ui.authorization.data

data class RegistrationState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean? = null
)

