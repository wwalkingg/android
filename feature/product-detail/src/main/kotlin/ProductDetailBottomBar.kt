import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun ProductDetailBottomBar(
    modifier: Modifier = Modifier,
    isCollected: Boolean,
    onCollectClick: () -> Unit,
    onBuy: () -> Unit
) {
    BottomAppBar(modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = onCollectClick) {
                val icon = if (isCollected) {
                    painterResource(com.example.core.design_system.Icons.heatFill)
                } else painterResource(com.example.core.design_system.Icons.heat)
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }

            Button(onClick = onBuy) {
                Text("购买")
            }
        }
    }
}