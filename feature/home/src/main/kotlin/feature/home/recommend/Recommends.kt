package feature.home.recommend

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.design_system.Icons
import core.model.Recommend
import core.ui.component.CourseCard

@Composable
internal fun Recommends(modifier: Modifier = Modifier, recommend: Recommend) {
    Column(modifier) {
        // title
        CompositionLocalProvider(LocalContentColor provides Color(0xffe72d22)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(Icons.bookmarksDuotone),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(5.dp))
                Text("热门推荐", style = MaterialTheme.typography.titleMedium)
            }
        }
        // recommend course
        Spacer(Modifier.height(10.dp))
        recommend.courses.forEach { course ->
            CourseCard(
                modifier = Modifier.fillMaxWidth(),
                course
            ) { rootNavigation.push(Config.RootConfig.CourseDetail(it)) }
            Spacer(Modifier.height(10.dp))
        }


    }
}