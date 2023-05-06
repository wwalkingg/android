package feature.calendar

import LoadUIState
import ModelState
import com.arkivanov.decompose.ComponentContext
import core.model.Course
import core.network.utils.ResponseWrapper
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CalendarModelState()
}

internal class CalendarModelState : ModelState() {
    private val _loadCalendarCourseUIStateFlow = MutableStateFlow<LoadUIState<Course>>(LoadUIState.Loading)
    val loadCalendarCourseUIStateFlow = _loadCalendarCourseUIStateFlow.asStateFlow()
    fun loadCalendarCourse(date: LocalDate) {
        coroutineScope.launch {
            getCalendarCourse(date)
                .onStart { _loadCalendarCourseUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadCalendarCourseUIStateFlow.emit(LoadUIState.Error(it))
                }
                .collect {
                    _loadCalendarCourseUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }
}

fun getCalendarCourse(date: LocalDate) = callbackFlow {
    httpClient.get("/recommend_video") {
        parameter("date", date.toString())
    }.apply {
        if (status.isSuccess()) {
            val resp = body<ResponseWrapper<Course>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}