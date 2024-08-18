package ai.opensource.emago.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordSettingScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(812.dp)
            .background(color = Color(0xFFFCF8EC))
    ) {
        // Child views.
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(375.dp)
                .height(25.dp)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            // Child views.
            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(start = 0.dp, end = 0.dp, bottom = 0.dp)
            ) {
                // Child views.
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(293.dp)
                    .fillMaxHeight()
            ) {
                // Child views.
                Text(
                    text = "비밀번호 변경",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        //fontFamily = FontFamily(Font(R.font.nanumsquareround)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(start = 0.dp, end = 0.dp, bottom = 0.dp)
            ) {
                // Child views.
            }
        }
        Row {
            Column(
                verticalArrangement = Arrangement.spacedBy(13.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(663.dp)
                    .background(color = Color(0xFFFCF8EC))
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                // Child views.
                Row {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            10.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .padding(start = 10.dp)
                    ) {
                        // Child views.
                        Text(
                            text = "기존 비밀번호를 입력해주세요",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                //fontFamily = FontFamily(Font(R.font.nanumsquareround)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(343.dp)
                        .height(41.dp)
                        .background(
                            color = Color(0x33000000),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 13.dp)
                ) {
                    // Child views.
                    Text(
                        text = "기존 비밀번호",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 15.sp,
                            //fontFamily = FontFamily(Font(R.font.nanumsquareround)),
                            fontWeight = FontWeight(700),
                            color = Color(0x4D000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Row {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            10.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .padding(start = 10.dp)
                    ) {
                        // Child views.
                        Text(
                            text = "새로운 비밀번호를 입력해주세요",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                //fontFamily = FontFamily(Font(R.font.nanumsquareround)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(343.dp)
                        .height(41.dp)
                        .background(
                            color = Color(0x33000000),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 13.dp)
                ) {
                    // Child views.
                    Text(
                        text = "새로운 비밀번호",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 15.sp,
                            //fontFamily = FontFamily(Font(R.font.nanumsquareround)),
                            fontWeight = FontWeight(700),
                            color = Color(0x4D000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Row {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            10.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .padding(start = 10.dp)
                    ) {
                        // Child views.
                        Text(
                            text = "비밀번호를 다시 입력해주세요",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                //fontFamily = FontFamily(Font(R.font.nanumsquareround)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(343.dp)
                        .height(41.dp)
                        .background(
                            color = Color(0x33000000),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 13.dp)
                ) {
                    // Child views.
                    Text(
                        text = "비밀번호 확인",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 15.sp,
                            //fontFamily = FontFamily(Font(R.font.nanumsquareround)),
                            fontWeight = FontWeight(700),
                            color = Color(0x4D000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
        Row {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(375.dp)
                    .height(59.dp)
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {
                // Child views.
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
                        .width(359.dp)
                        .height(43.dp)
                        .background(color = Color(0xFF79A3B1), shape = RoundedCornerShape(size = 5.dp))
                        .padding(start = 80.dp, top = 13.dp, end = 80.dp, bottom = 13.dp)
                ) {
                    // Child views.
                    Text(
                        text = "변경 완료",
                        style = TextStyle(
                            fontSize = 13.sp,
                            //fontFamily = FontFamily(Font(R.font.nanumsquareround)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordSettingScreenPreview() {
    PasswordSettingScreen()
}