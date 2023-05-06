import androidx.compose.runtime.Stable

@Stable
sealed interface LoadUIState<out T> {
    object Loading : LoadUIState<Nothing>
    data class Success<out T>(val data: T) : LoadUIState<T>
    data class Error(val error: Throwable) : LoadUIState<Nothing>
}

@Stable
sealed interface PostUIState {
    object None:PostUIState
    object Loading : PostUIState
    object Success : PostUIState
    data class Error(val error: Throwable) : PostUIState
}