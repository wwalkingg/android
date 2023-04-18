import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.arkivanov.decompose.router.stack.pop
import com.example.core.design_system.Lotties
import core.common.navigation
import core.component_base.LoadUIState

@Composable
fun <T> SmallLoadUIStateScaffold(
    loadUIState: LoadUIState<T>,
    modifier: Modifier = Modifier,
    onReload: (() -> Unit)? = null,
    successContent: @Composable (T) -> Unit
) {
    Box(modifier) {
        when (loadUIState) {
            is LoadUIState.Error -> {
                Row(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.errorContainer),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onErrorContainer) {
                        if (onReload != null) {
                            Button(onClick = onReload) {
                                Text("重新加载")
                            }
                        }
                        TextButton(onClick = { navigation.pop() }) {
                            Text("返回")
                        }
                    }
                }
            }

            LoadUIState.Loading -> {
                Dialog(onDismissRequest = { }) {
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .fillMaxWidth(.6f).aspectRatio(1f)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
            }

            is LoadUIState.Loaded -> {
                val data = loadUIState.data
                successContent(data)
            }
        }
    }
}