package ai.opensource.emago.screens.chat

import ai.opensource.emago.EMAGOViewModel
import ai.opensource.emago.R
import ai.opensource.emago.util.CommonImage
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ChatCreateScreen(
    navController: NavController,
    vm: EMAGOViewModel = hiltViewModel<EMAGOViewModel>()
) {
    var chatRoomName by remember { mutableStateOf("") }
    var chatRoomDescription by remember { mutableStateOf("") }
    var chatImageUrl by remember {
        mutableStateOf("")
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                vm.uploadImage(uri) {
                    chatImageUrl = it.toString()
                }
            }
        }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .fillMaxWidth()
                .background(color = Color(0xFF79A3B1), shape = RoundedCornerShape(size = 10.dp))
                .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {
            // Child views.
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                // Child views.
                Text(
                    text = "채팅방 이미지 등록",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Child views.
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        // Child views.
                        Box(
                            modifier = Modifier
                                .height(intrinsicSize = IntrinsicSize.Min)
                                .clickable {
                                    launcher.launch("image/*")
                                })
                        {
                            Card(
                                shape = CircleShape,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(100.dp)
                            ) {
                                CommonImage(data = chatImageUrl)
                            }
                        }
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp)
            ) {
                OutlinedTextField(
                    value = chatRoomName,
                    onValueChange = { chatRoomName = it },
                    label = { Text("채팅방 이름") })
                OutlinedTextField(
                    value = chatRoomDescription,
                    onValueChange = { chatRoomDescription = it },
                    label = { Text("채팅방 설명") })
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                }
            }


        }
        OutlinedButton(
            onClick = {
                vm.onAddChat(chatRoomName, chatRoomDescription, chatImageUrl)
                navController.navigate("chatList")
            },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0x4D000000)),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "생성하기",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                    color = Color(0xFF000000),
                )
            )
        }
    }
}