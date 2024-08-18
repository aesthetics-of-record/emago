@file:JvmName("ProfileScreen2Kt")

package ai.opensource.emago.screens.profile

import ai.opensource.emago.EMAGOViewModel
import ai.opensource.emago.R
import ai.opensource.emago.util.CommonImage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController,
                  vm: EMAGOViewModel = hiltViewModel<EMAGOViewModel>()
) {
    val userData = vm.userData.value
    val userName = userData?.name?:""
    val stateMessage = userData?.stateMsg?:""

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFCF8EC))
    ) {
        // Child views.
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(174.dp)
                .background(color = Color(0xFFFCF8EC))
                .padding(start = 26.dp, top = 22.dp, end = 26.dp, bottom = 11.dp)
        ) {
            // Child views.
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                // 프로필 이미지
                val imageUrl = vm.userData.value?.imageUrl
                @Composable
                fun ProfileImage(imageUrl: String?, vm: EMAGOViewModel) {
                    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min))
                    {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(80.dp)
                        ) {
                            CommonImage(data = imageUrl)
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        0.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                ) {
                    ProfileImage(imageUrl = imageUrl, vm = vm)
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Child views.
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            7.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        // 프로필 표시
                        Text(
                            text = userName,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                color = Color(0xFF000000),
                            )
                        )
                        Text(
                            text = vm.getCurrentUserEmail() ?: "",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                fontWeight = FontWeight(400),
                                color = Color(0x8A000000),
                            )
                        )
                        Text(
                            text = stateMessage,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                            )
                        )

                    }
                }
            }
            Button(
                onClick = { navController.navigate("profileSet") },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF79A3B1)),
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .width(256.dp)
                    .height(37.dp)
            ) {
                Text(
                    text = "계정 설정 / 프로필 편집",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(187.dp)
                .background(color = Color(0xFFFCF8EC))
                .padding(start = 43.dp, top = 42.dp, end = 102.dp, bottom = 31.dp)
        ) {
            // 통계 표시
        }
    }
}
