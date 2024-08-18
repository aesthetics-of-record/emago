package ai.opensource.emago.screens.sign


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ai.opensource.emago.util.CheckSignedIn
import ai.opensource.emago.util.CommonProgressBar

import ai.opensource.emago.EMAGOViewModel

import ai.opensource.emago.R
import ai.opensource.emago.util.EmagoInputTextField
import ai.opensource.emago.util.OutlinedTextFieldBackground
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel

@Composable

fun SignUpScreen(navController: NavController, vm : EMAGOViewModel = hiltViewModel()) {
    Scaffold { innerPadding ->
        CheckSignedIn(vm, navController)
        //User View
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFCF8EC))
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            // Body
            Column(
                verticalArrangement = Arrangement.spacedBy(29.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 30.dp, top = 16.dp, end = 30.dp, bottom = 16.dp)
                    .fillMaxSize()
            ) {
                // Text
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "Welcome to E-mago",
                        style = TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.nanumsquareroundeb)),
                            color = Color(0xFF456268),
                            textAlign = TextAlign.Center,
                        )
                    )

                }
                // Input Field
                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Bottom),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                ) {
                    var emailState by remember { mutableStateOf("") }
                    var nameState by remember { mutableStateOf("") }
                    var phoneState by remember { mutableStateOf("") }
                    var pwState by remember { mutableStateOf("") }
                    var pwckState by remember { mutableStateOf("") }

                    val focus = LocalFocusManager.current

                    // Text
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                    ) {
                        Text(
                            text = "회원가입",
                            style = TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundeb)),
                                color = Color(0xFF456268),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                    EmagoInputTextField(
                        input = emailState,
                        onInputChange ={emailState = it},
                        placeholder = "Email",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    EmagoInputTextField(
                        input = nameState,
                        onInputChange ={nameState = it},
                        placeholder = "닉네임",
                    )


                    EmagoInputTextField(
                        input = phoneState,
                        onInputChange ={phoneState = it},
                        placeholder = "전화번호",
                    )

                    EmagoInputTextField(
                        input = pwState,
                        onInputChange ={pwState = it},
                        placeholder = "비밀번호",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation()

                    )

                    // Sign Up Button
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            vm.signUp(
                                email = emailState,
                                name = nameState,
                                number = phoneState,
                                password = pwState
                            )
                        },
                        shape = RoundedCornerShape(size = 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF79A3B1),
                        )
                    ) {
                        Text(
                            text = "회원가입",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                                color = Color(0xFFFFFFFF)
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    // Back to Sign In
                    TextButton(onClick = { navController.navigate("login"){
                        popUpTo("login"){
                            inclusive = true
                        }
                    } }) {
                        Text(
                            text = "계정이 있나요? 로그인 하러 가기",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.nanumsquareroundr)),
                                color = Color(0xFF456268),
                            )
                        )
                    }
                }
            }
        }
        if (vm.inProcess.value) {
            CommonProgressBar()
        }
    }
}