package space.tuleuov.bookreader.ui.authorization.data

data class RegistrationState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var isError: Boolean? = null
)

