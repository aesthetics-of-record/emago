package ai.opensource.emago.screens.home

import ai.opensource.emago.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun ReviewContent(isChecked : Boolean = false){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
            .clickable { /*TODO : Go to Card*/ }
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Child views.
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                // Child views.
                Text(
                    text = "#index",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                        color = Color(0xFF707070),
                    )
                )
                Text(
                    text = "#채팅방 이름",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                        color = Color(0xFF707070),
                    )
                )
            }
            Text(
                text = "#시간 정보",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                    color = Color(0xFF707070),
                )
            )
        }
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Child views.
            if(isChecked) {
                Text(
                    text = "원문 내용",
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                        color = Color(0xFF000000),
                        textDecoration = TextDecoration.LineThrough
                    )
                )
            } else {
                Text(
                    text = "원문 내용",
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                        color = Color(0xFF000000),
                    )
                )
            }
        }
    }

}

@Composable
fun ItemRow(text : String, progress : Float){
    val percentage by remember { mutableFloatStateOf(progress) }
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF456268),
                shape = RoundedCornerShape(size = 5.dp)
            )
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                12.dp,
                Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 12.dp,
                    end = 16.dp,
                    bottom = 12.dp
                )
        ) {
            // Text Fields
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Text : Today's goal
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
                // Percentage
                Text(
                    text = "${(percentage/100*100).toInt()}%",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            // Progress Bar
            LinearProgressIndicator(progress = percentage/100,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF79A3B1),
                trackColor = Color(0xFFFCF8EC)
            )

        }
    }
}

@Composable
fun Calendar(
    selectedDate : LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    firstDate : LocalDate = LocalDate.now()){
    val currentDate = remember { firstDate }
    var isWeekMode by remember { mutableStateOf(true) }

    val currentMonth = remember(selectedDate) { selectedDate.yearMonth }
    val startMonth = remember { currentMonth.minusMonths(12) }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val daysOfWeek = remember { daysOfWeek() }

    val monthState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    val weekState = rememberWeekCalendarState(
        startDate = startMonth.atStartOfMonth(),
        endDate = endMonth.atEndOfMonth(),
        firstVisibleWeekDate = selectedDate,
        firstDayOfWeek = daysOfWeek.first()
    )

    Column(
        modifier = Modifier
    ) {
        CalendarTitle(currentMonth = currentMonth) {
            isWeekMode = !isWeekMode
        }
        CalendarHeader(daysOfWeek = daysOfWeek)
        AnimatedVisibility(visible = !isWeekMode) {
            HorizontalCalendar(
                state = monthState,
                dayContent = { day ->
                    val isSelectable = day.position == DayPosition.MonthDate
                    Day(
                        day.date,
                        isSelected = selectedDate == day.date,
                        isSelectable = isSelectable,
                        isToday = day.date == currentDate,
                    ) { clicked -> onDateSelected(clicked) }
                }
            )
        }
        AnimatedVisibility(visible = isWeekMode) {
            WeekCalendar(
                state = weekState,
                dayContent = { day ->
                    val isSelectable = day.position == WeekDayPosition.RangeDate
                    Day(
                        day.date,
                        isSelected = selectedDate == day.date,
                        isSelectable = isSelectable,
                        isToday = day.date == currentDate,
                    ) { clicked -> onDateSelected(clicked)}
                }
            )
        }
    }
}



@Composable
fun Day(
    day: LocalDate,
    isSelected: Boolean,
    isSelectable: Boolean,
    isToday: Boolean,
    onClick: (LocalDate) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(6.dp)
            .background(
                color = when {
                    isSelected -> Color(0xFF456268)
                    isToday -> Color.Transparent
                    else -> Color.Transparent
                },
                shape = CircleShape
            )
            .border(
                width = if (isToday) 2.dp else 0.dp,
                color = if (isToday) Color(0xFF456268) else Color.Transparent,
                shape = CircleShape,
            )
            .clickable(
                enabled = isSelectable,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when {
            isSelected -> Color.White
            isToday -> Color(0xFF456268)
            isSelectable -> Color.Unspecified
            else -> Color(0xFF456268)
        }
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
        )
    }
}

@Composable
fun CalendarTitle(currentMonth: YearMonth, onTitleClick: () -> Unit) {
    val formattedMonth = remember(currentMonth) {
        "${currentMonth.year}년 ${currentMonth.monthValue}월"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onTitleClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formattedMonth,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                color = Color(0xFF000000)
            )
        )
    }
}

@Composable
fun CalendarHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                text = dayOfWeek.name.take(1),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                    color = Color(0xFF707070)
                ),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}