package ai.opensource.emago.screens.profile

import ai.opensource.emago.EMAGOViewModel
import ai.opensource.emago.util.CommonDivider
import ai.opensource.emago.util.CommonImage
import ai.opensource.emago.util.CommonProgressBar
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable

fun ProfileScreen(navController: NavController, vm: EMAGOViewModel) {
    val inProgress = vm.inProcess.value
    if (inProgress) {
        CommonProgressBar()
    } else {
        val userData = vm.userData.value
        var name by rememberSaveable {
            mutableStateOf(userData?.name?:"")
        }
        var number by rememberSaveable {
            mutableStateOf(userData?.number?:"")
        }
        Column {
            ProfileContent(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp),
                vm=vm,
                name = name,
                number = number,
                onNameChange = {name=it},
                onNumberChange = {number=it},
                onSave = {
                    vm.createOrUpdateProfile(
                        name, number
                    )
                },
                onBack = {
                    // navigateTo(navController, DestinationScreen.ChatList.route)
                },
                onLogout = {
                    vm.logout()
                    navController.navigate("first"){
                        popUpTo("first"){
                            inclusive = true
                        }
                    }
                },
            )
            Spacer(modifier = Modifier.weight(1f)) // 남은 공간을 채워 BottomNavigationMenu를 아래로 밀어냅니다.
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    modifier: Modifier,
    vm: EMAGOViewModel,
    name: String,
    number: String,
    onNameChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onLogout: () -> Unit,
) {
    val imageUrl = vm.userData.value?.imageUrl

    Column (){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", Modifier.clickable {
                onBack.invoke()
            })
            Text(text = "Save",
                Modifier.clickable {
                    onSave.invoke()
                })
        }


        CommonDivider()
        ProfileImage(imageUrl = imageUrl, vm = vm)

        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))

            TextField(
                value = name, onValueChange = onNameChange,
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black,
                    containerColor = Color.Transparent,
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Number", modifier = Modifier.width(100.dp))
            TextField(
                value = number, onValueChange = onNumberChange,
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black,
                    containerColor = Color.Transparent,
                )
            )
        }
        CommonDivider()
        Row (modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.Center){
            Text(text = "LogOut", modifier = Modifier.clickable {
                onLogout.invoke()
            })

        }
    }
}



@Composable
fun ProfileImage(imageUrl: String?, vm: EMAGOViewModel) {

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                vm.uploadProfileImage(uri)
            }
        }

    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min))
    {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                CommonImage(data = imageUrl)

                }
            }
            Text(text = "Change Profile Picture")
        }

        if (vm.inProcess.value) {
            CommonProgressBar()
        }
    }
