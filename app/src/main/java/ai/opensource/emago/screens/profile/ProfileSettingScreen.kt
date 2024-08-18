package ai.opensource.emago.screens.profile

import ai.opensource.emago.EMAGOViewModel
import ai.opensource.emago.R
import ai.opensource.emago.util.CommonImage
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ProfileSettingScreen(
    navController: NavController,
    vm: EMAGOViewModel = hiltViewModel<EMAGOViewModel>()
) {
    val userData = vm.userData.value
    val userName = userData?.name ?: ""
    val stateMessage = userData?.stateMsg ?:""
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                vm.uploadProfileImage(uri)
            }
        }
    // User View
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Body
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Profile Image
                val imageUrl = vm.userData.value?.imageUrl


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
                Row {
                    //프로필 사진 변경
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .background(
                                color = Color(0xFF79A3B1),
                                shape = RoundedCornerShape(size = 5.dp)
                            )
                            .padding(start = 10.dp, top = 3.dp, end = 10.dp, bottom = 3.dp)
                    ) {
                        Text(
                            text = "프로필 사진 변경",
                            modifier = Modifier
                                .clickable {
                                    launcher.launch("image/*")
                                },
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                    }
                }
            }
            // 개인정보 박스
            Row {
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0x4D000000),
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .fillMaxWidth()
                ) {
                    // Child views.
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 17.dp, end = 10.dp, bottom = 17.dp)
                    ) {
                        // Child views.
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(308.dp)
                                .height(17.dp)
                                .padding(start = 8.dp, end = 8.dp)
                                .clickable { navController.navigate("nickSet") }
                        ) {
                            // Child views.
                            Text(
                                text = "닉네임",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                    color = Color(0xFF000000),
                                )
                            )
                            Text(
                                text = userName,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                    color = Color(0xFF999999),
                                )
                            )
                        }
                    }
                    Divider(
                        color = Color(0x4D000000)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 17.dp, end = 10.dp, bottom = 17.dp)
                    ) {
                        // Child views.
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(308.dp)
                                .height(17.dp)
                                .padding(start = 8.dp, end = 8.dp)
                                .clickable { navController.navigate("stateSet") }
                        ) {
                            // Child views.
                            Text(
                                text = "상태메시지",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                    color = Color(0xFF000000),
                                )
                            )
                            Text(
                                text = stateMessage,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                    color = Color(0xFF999999),
                                )
                            )
                        }
                    }
                    Divider(
                        color = Color(0x4D000000)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 17.dp, end = 10.dp, bottom = 17.dp)
                    ) {
                        // Child views.
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                150.dp,
                                Alignment.Start
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(308.dp)
                                .height(17.dp)
                                .padding(start = 8.dp, end = 8.dp)
                        ) {
                            // Child views.
                            Text(
                                text = "전화번호",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                    color = Color(0xFF000000),
                                )
                            )
                            Text(
                                text = userData?.number ?: "",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                color = Color(0xFF999999),
                            )
                        }
                    }
                    Divider(
                        color = Color(0x4D000000)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 17.dp, end = 10.dp, bottom = 17.dp)
                    ) {
                        // Child views.
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                185.dp,
                                Alignment.Start
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(308.dp)
                                .height(17.dp)
                                .padding(start = 8.dp, end = 8.dp)
                                .clickable {
                                    vm.sendResetPassword()
                                }
                        ) {
                            // Child views.
                            Text(
                                text = "비밀번호 변경 메일 보내기",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                    color = Color(0xFF000000),
                                )
                            )
                        }
                    }
                }
            }
            OutlinedButton(
                onClick = { vm.logout()
                          navController.navigate("login")
                          },
                shape = RoundedCornerShape(16.dp),
                border =  BorderStroke(1.dp, Color(0x4D000000)),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "로그아웃",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                        color = Color(0xFF000000),
                    )
                )
            }
        }
    }
}

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