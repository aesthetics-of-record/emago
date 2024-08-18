package ai.opensource.emago.screens.home

import ai.opensource.emago.EMAGOViewModel
import ai.opensource.emago.R
import ai.opensource.emago.data.Message
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReviewContentScreen(messageId: String, vm: EMAGOViewModel = hiltViewModel<EMAGOViewModel>()) {
    LaunchedEffect(key1 = Unit) {
        vm.getMessageById(messageId)
    }

    val message = vm.reviewMessage.value

    // Timestamp를 Date로 변환
    val date: Date = message.timestamp!!.toDate()

    // 날짜 형식화 예시
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN)
    val formattedDate: String? = sdf.format(date)
    

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
                        .padding(16.dp)
                ) {
                    // Review Title
                    Text(
                        text = formattedDate ?: "",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                            fontSize = 24.sp,
                            color = Color(0xFF79A3B1)
                        )
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
                    ReviewCard()
                }
            }
        }
    }
}

@Composable
fun ReviewCard(vm: EMAGOViewModel = hiltViewModel<EMAGOViewModel>()){
    val message = vm.reviewMessage.value

    // Review Card
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 500.dp)
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
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "원문",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                        fontSize = 18.sp,
                        color = Color(0xFF79A3B1)
                    )
                )
                Text(text=message.message ?: "")
                Text(
                    text = "잘못된 문장 일 시 수정된 문장",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                        fontSize = 18.sp,
                        color = Color(0xFF79A3B1)
                    )
                )
                Text(text=message.feedback?.correct_sentence ?: "")
                Text(
                    text = "고급 문장",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                        fontSize = 18.sp,
                        color = Color(0xFF79A3B1)
                    )
                )
                Text(text=message.feedback?.advanced_sentence ?: "")
                Text(
                    text = "코멘트",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                        fontSize = 18.sp,
                        color = Color(0xFF79A3B1)
                    )
                )
                Text(text=message.feedback?.comment ?: "")
            }
        }
    }
}