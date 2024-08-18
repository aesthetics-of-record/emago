package ai.opensource.emago.screens.chat


import ai.opensource.emago.EMAGOViewModel
import ai.opensource.emago.R
import ai.opensource.emago.data.ChatData
import ai.opensource.emago.util.CommonProgressBar
import ai.opensource.emago.util.TitleText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter


@Composable

fun ChatListScreen(navController: NavController, vm: EMAGOViewModel = hiltViewModel<EMAGOViewModel>()) {

    LaunchedEffect(key1 = Unit) {
        vm.getAllChatData()
    }

    val inProgress = vm.inProcessChats
    if (inProgress.value) {
        CommonProgressBar()
    } else {
        val chats = vm.chats.value
        val userData = vm.userData.value
        val showDialog = remember {
            mutableStateOf(false)
        }
        val onFabClick: () -> Unit={showDialog.value=true}
        val onDismiss: () -> Unit={showDialog.value=false}
        val onAddChat = fun (title: String, description: String) {
            vm.onAddChat(title, description)
            showDialog.value=false
        }

        Scaffold (

            content = {
                Column (
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp)
                ) {

                    if (chats!!.isEmpty()) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            Text(text = "채팅방이 존재하지 않습니다.")
                        }
                    } else {
                        ChatListBox(navController, modifier = Modifier.fillMaxWidth(), chatList = chats)
                    }
                }
            }
        )

        }
    }


@Composable
fun ChatListBox(navController: NavController, modifier: Modifier, chatList: List<ChatData> ){

    LazyColumn(modifier = modifier) {
        items(chatList) { item ->

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .height(62.dp)
                        .clickable {
                            val route = item.id
                            navController.navigate("chat/$route")
                        }
                ) {
                    // Child views.
                    Image(
                        painter = rememberImagePainter(data = item.imageUrl),
                        contentDescription = "image description",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(52.dp)
                            .height(52.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Child views.
                        Text(
                            text = item.title!!,
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                                color = Color(0xFF000E08),
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = item.description!!,
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                                color = Color(0xFF797C7B),
                            )
                        )
                }
            }
        }

    }
}
