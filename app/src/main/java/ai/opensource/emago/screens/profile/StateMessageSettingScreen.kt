package ai.opensource.emago.screens.profile

import ai.opensource.emago.EMAGOViewModel
import ai.opensource.emago.R
import ai.opensource.emago.util.EmagoInputTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun StateMessageSettingScreen(
    navController: NavController,
    vm: EMAGOViewModel = hiltViewModel<EMAGOViewModel>()
) {
    val userData = vm.userData.value
    var stateMessage = userData?.stateMsg ?: ""
    var state by remember { mutableStateOf("") }

    // View

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = Color(0x4D000000))
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            ){
                Button(
                    onClick = {
                        vm.createOrUpdateProfile(stateMsg = state)
                        navController.navigate("profileSet") },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF79A3B1)),
                    modifier = Modifier
                        .shadow(
                            elevation = 4.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF79A3B1),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(top = 4.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = "변경사항 저장",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
            }
        }
    ) {innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Body
            Column(
                verticalArrangement = Arrangement.spacedBy(13.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                // Child views.

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                ) {
                    // Child views.
                    Text(
                        text = "새로운 상태 메시지를 입력해주세요",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                // Child views.
                EmagoInputTextField(
                    input = state,
                    onInputChange = { state = it },
                    placeholder = "상태 메시지",
                )
            }
        }
    }
}

