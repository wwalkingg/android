package feature.calendar

import LoadUIState
import android.icu.util.Calendar
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import core.model.Course
import core.ui.video_player.VideoPlayer
import kotlinx.datetime.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(component: CalendarComponent) {
    Scaffold { padding ->
        var dateTime by remember {
            mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
        }
        LaunchedEffect(dateTime) {
            component.modelState.loadCalendarCourse(dateTime.toJavaLocalDate())
        }
        Column(Modifier.padding(padding)) {
            AndroidView(modifier = Modifier.fillMaxWidth(), factory = { context ->
                val view = CalendarView(context)
                view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    dateTime = LocalDate(year, month + 1, dayOfMonth)
                }
                view.firstDayOfWeek = Calendar.MONDAY
                view
            }, update = {})
            val shape = remember {
                RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            }
            Box(
                modifier = Modifier
                    .shadow(10.dp, shape)
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(20.dp, 10.dp)
            ) {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "为您推荐",
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier.alignByBaseline()
                            )
                            Text(text = dateTime.toString(), modifier = Modifier.alignByBaseline())
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        val loadCalendarCourse by component.modelState.loadCalendarCourseUIStateFlow.collectAsState()
                        when (loadCalendarCourse) {
                            is LoadUIState.Error -> {
                                Text(text = "今日无推荐")
                            }

                            LoadUIState.Loading -> {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                    CircularProgressIndicator()
                                }
                            }

                            is LoadUIState.Success -> {
                                VideoPlayer(
                                    modifier = Modifier.clip(MaterialTheme.shapes.small),
                                    url = core.common.baseUrl + (loadCalendarCourse as LoadUIState.Success<Course>).data.video
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}