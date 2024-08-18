package ai.opensource.emago.screens.home

import ai.opensource.emago.EMAGOViewModel
import ai.opensource.emago.R
import ai.opensource.emago.util.extractTimeFromTimestampString
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Composable
fun ReviewScreen(selectedDate: LocalDate?, navController: NavController, vm: EMAGOViewModel = hiltViewModel<EMAGOViewModel>()) {
    var currentDate by remember { mutableStateOf<LocalDate>(selectedDate ?: LocalDate.now()) }

    LaunchedEffect(key1 = Unit) {
        vm.getAllUserMessagesForDate(currentDate)
    }



    // Review Column
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(64.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0x8079A3B1),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    Calendar(
                        currentDate,
                        onDateSelected = { newDate ->
                            currentDate = newDate
                            vm.getAllUserMessagesForDate(currentDate)
                        }
                    )
                }
                // Review Box
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(animationSpec = tween(durationMillis = 300))
                        .shadow(
                            elevation = 4.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .background(
                            color = Color(0xFF79A3B1),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFFCF8EC),
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .padding(
                                start = 8.dp,
                                top = 16.dp,
                                end = 8.dp,
                                bottom = 16.dp
                            )

                    ) {
                        Text(
                            text = "Review",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                                fontSize = 24.sp,
                                color = Color(0xFF79A3B1)
                            )
                        )
                        val messages = vm.myChatMessages.value


                        // Review List
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 400.dp)
                        ) {
                            itemsIndexed(messages!!) {
                                index, item ->

                                // Timestamp를 Date로 변환
                                val date: Date = item.timestamp!!.toDate()

                                // 날짜 형식화 예시
                                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN)
                                val formattedDate: String = sdf.format(date)

                                Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { navController.navigate("reviewContentCard/" + item.id) }
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // Child views.
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            8.dp,
                                            Alignment.Start
                                        ),
                                        verticalAlignment = Alignment.Top,
                                    ) {
                                        // Child views.
                                        Text(
                                            text = "#" + (index + 1),
                                            style = TextStyle(
                                                fontSize = 13.sp,
                                                lineHeight = 20.sp,
                                                fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                                                color = Color(0xFF707070),
                                            )
                                        )
                                    }
                                    Text(
                                        text = "#" + formattedDate,
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

                                    Text(
                                        text = item.message!!,
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


                    }
                }
            }
        }
    }
}

