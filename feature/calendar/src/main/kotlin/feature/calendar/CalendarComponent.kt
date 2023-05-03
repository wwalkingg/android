package feature.calendar

import ModelState
import com.arkivanov.decompose.ComponentContext

class CalendarComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CalendarModelState()
}

internal class CalendarModelState : ModelState() {

}