package ai.opensource.emago.screens.chat

import ai.opensource.emago.util.CommonDivider
import ai.opensource.emago.EMAGOViewModel
import ai.opensource.emago.data.Message
import ai.opensource.emago.data.UserData
import ai.opensource.emago.util.CommonImage
import ai.opensource.emago.util.CommonProgressBar
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SingleChatScreen(navController: NavController, vm: EMAGOViewModel, chatId: String) {

    var reply by rememberSaveable {
        mutableStateOf("")
    }

    val onSendReply = {
        vm.onSendReply(chatId, reply)
        reply = ""
    }

    val myUser = vm.userData.value

    val chatMessages = vm.chatMessages

    val showDialog = remember {
        mutableStateOf(false)
    }
    val comment = rememberSaveable {
        mutableStateOf("")
    }
    val advanced_sentence = rememberSaveable {
        mutableStateOf("")
    }
    val error_sentence = rememberSaveable {
        mutableStateOf("")
    }
    val correct_sentence = rememberSaveable {
        mutableStateOf("")
    }
    val onMessageClick = fun(
        message_comment: String?,
        message_advanced_sentence: String?,
        message_error_sentence: String?,
        message_correct_sentence: String?
    ) {
        comment.value = message_comment?:""
        advanced_sentence.value = message_advanced_sentence?:""
        error_sentence.value = message_error_sentence?:""
        correct_sentence.value = message_correct_sentence?:""

        showDialog.value = true
    }
    val onDismiss: () -> Unit = { showDialog.value = false }



    LaunchedEffect(key1 = Unit) {
        vm.populateMessages(chatId)
    }
    BackHandler {
        vm.depopulateMessage()
    }
    FeedbackDialog(showDialog.value, onDismiss, comment.value, advanced_sentence.value, error_sentence.value, correct_sentence.value)

    Column {
        ChatHeader {
            navController.popBackStack()
        }
        MessageBox(
            modifier = Modifier.weight(1f),
            chatMessages = chatMessages.value ?: listOf(),
            onMessageClick,
            myUser!!
        )
        ReplyBox(reply = reply, onReplyChange = { reply = it }, onSendReply = onSendReply)
    }

}

@Composable
fun ReplyBox(reply: String, onReplyChange: (String) -> Unit, onSendReply: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(value = reply, onValueChange = onReplyChange, maxLines = 5)
            Button(onClick = onSendReply) {
                Text(text = "보내기")
            }
        }
    }
}

@Composable
fun MessageBox(
    modifier: Modifier,
    chatMessages: List<Message>,
    onMessageClick: (String?, String?, String?, String?) -> Unit,
    myUser: UserData,
) {
    LazyColumn(modifier = modifier) {
        items(chatMessages) { msg ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                Column {
                    ProfileImage( myUser.imageUrl )
                    Text(text = msg.message ?: "", modifier = Modifier.clickable {
                        onMessageClick(
                            msg.feedback?.comment,
                            msg.feedback?.advanced_sentence,
                            msg.feedback?.error_sentence,
                            msg.feedback?.correct_sentence
                        )
                    })
                }
            }

        }

    }
}

@Composable
fun ProfileImage(imageUrl: String?) {
    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min))
    {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(27.dp)
            ) {
                CommonImage(data = imageUrl)
            }
        }
    }
}

@Composable
fun ChatHeader(onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Rounded.ArrowBack, contentDescription = null, modifier = Modifier
                .clickable {
                    onBackClicked.invoke()
                }
                .padding(
                    8.dp
                )
        )
        Text(text = "채팅방")

    }
}

@Composable
fun FeedbackDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    comment: String,
    advanced_sentence: String,
    error_sentence: String,
    correct_sentence: String
) {
    val addChatTitle = remember {
        mutableStateOf("")
    }
    val addChatDescription = remember {
        mutableStateOf("")
    }

    if (showDialog) {
        AlertDialog(

            onDismissRequest = {
                onDismiss.invoke()
                addChatTitle.value = ""
                addChatDescription.value = ""
            },
            confirmButton = {

                Button(onClick = { onDismiss() }) {
                    Text(text = "닫기")
                }

            }, title = {
                Text(text = "Add Chat")
            },
            text = {
                Column {
                    Row {
                        Text(text = comment)

                    }
                    Row {
                        Text(text = advanced_sentence)

                    }
                    Row {
                        Text(text = error_sentence)

                    }
                    Row {
                        Text(text = correct_sentence)

                    }
                }
            }
        )
    }
}
