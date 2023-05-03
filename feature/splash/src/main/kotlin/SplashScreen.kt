import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.arkivanov.decompose.router.stack.replaceAll
import com.example.android.core.model.R
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    LaunchedEffect(Unit){
        val isLogin = token.isNotBlank()
        delay(2000L)
        rootNavigation.replaceAll(if (isLogin) Config.RootConfig.Home else Config.RootConfig.Login)
    }
}