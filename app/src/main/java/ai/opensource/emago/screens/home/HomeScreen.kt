package ai.opensource.emago.screens.home

import ai.opensource.emago.R
import ai.opensource.emago.util.previewNavController
import ai.opensource.emago.util.toFormattedString
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate

@Composable
fun HomeScreen(navController: NavController) {
    val currentDate = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(currentDate) }
    var showExtraItems by remember { mutableStateOf(false) } // 빌드 할때는 false로 바꾸기
    val goalOfToday by remember { mutableFloatStateOf(20f) }
    val progress1 by remember { mutableFloatStateOf(10f) }
    val progress2 by remember { mutableFloatStateOf(20f) }
    val progress3 by remember { mutableFloatStateOf(30f) }

    // User views
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(64.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp)
        ) {
            //Today's Goal Column
            Column(
                verticalArrangement = Arrangement.spacedBy((-10).dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Emago Robot
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Emago Robot",
                    contentScale = ContentScale.None
                )

                // Today's goal Box
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 4.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .background(
                            color = Color(0xFF79A3B1),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(start = 12.dp, top = 12.dp, end = 12.dp)
                    ) {
                        // Progress
                        ItemRow("오늘의 목표", goalOfToday)
                        // Child Progress
                        AnimatedVisibility(visible = showExtraItems) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 12.dp, end = 12.dp)
                            ) {
                                // Progress 1
                                ItemRow("영어로 보낸 메시지 수", progress1)
                                // Progress 2
                                ItemRow("채팅에 참가한 시간", progress2)
                                // Progress 3
                                ItemRow("복습한 채팅 수", progress3)
                                // Go to Daily Statistics
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        8.dp,
                                        Alignment.End
                                    ),
                                    verticalAlignment = Alignment.Top,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // 일자별 통
                                }
                            }
                        }
                        // Show Extra Items Button
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .clickable { showExtraItems = !showExtraItems }
                        ) {
                            Image(
                                painter = painterResource(id = if (showExtraItems) R.drawable.chevron_up else R.drawable.chevron_down),
                                contentDescription = "arrow",
                                contentScale = ContentScale.None,
                            )
                        }
                    }
                }
            }
            // Review Column
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
                    // Todo : Calendar
                    Calendar(
                        selectedDate = selectedDate,
                        onDateSelected = { newDate ->
                            selectedDate = newDate
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
                            .clickable {
                                val selectedDateString = selectedDate.toFormattedString()
                                navController.navigate("review/$selectedDateString")
                            }
                    ) {
                        Text(
                            text = "복습 시작",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                color = Color(0xFF000000),
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = previewNavController()
    HomeScreen(navController = navController)
}

