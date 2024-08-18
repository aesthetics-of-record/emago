package ai.opensource.emago

import ai.opensource.emago.screens.chat.ChatCreateScreen
import ai.opensource.emago.screens.chat.ChatListScreen
import ai.opensource.emago.screens.chat.ChatScreen
import ai.opensource.emago.screens.home.HomeScreen
import ai.opensource.emago.screens.home.ReviewContentScreen
import ai.opensource.emago.screens.home.ReviewScreen
import ai.opensource.emago.screens.profile.NicknameSettingScreen
import ai.opensource.emago.screens.profile.ProfileScreen
import ai.opensource.emago.screens.profile.ProfileSettingScreen
import ai.opensource.emago.screens.profile.StateMessageSettingScreen
import ai.opensource.emago.screens.sign.FirstScreen
import ai.opensource.emago.screens.sign.SignUpScreen
import ai.opensource.emago.util.toLocalDate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun Emago() {
    val navController = rememberNavController()
    val vm = hiltViewModel<EMAGOViewModel>()
    Scaffold(
        topBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route?.startsWith("chat/") != true){
                MainTopBar(navController)
            } },
    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") { FirstScreen(navController) }
            composable("signUp") { SignUpScreen(navController) }
            composable("home") {
                Layout(navController) {
                    HomeScreen(navController)
                }
            }
            composable("chatList") {
                Layout(navController) {
                    ChatListScreen(navController, vm)
                }
            }
            composable("profile") {
                Layout(navController) {
                    ProfileScreen(navController)
                }
            }
            composable(
                route = "review/{selectedDate}",
                arguments = listOf(navArgument("selectedDate") { type = NavType.StringType })
            ) {backStackEntry ->
                val selectedDateString =
                    backStackEntry.arguments?.getString("selectedDate")
                val selectedDate = selectedDateString?.toLocalDate()
                ReviewScreen(selectedDate, navController)
            }
            composable("nickSet") { NicknameSettingScreen(navController)}
            composable("stateSet") { StateMessageSettingScreen(navController)}
            composable("profileSet") { ProfileSettingScreen(navController) }
            composable("chatCreate") { ChatCreateScreen(navController) }
            composable("reviewContentCard/{messageId}",
                arguments = listOf(navArgument("messageId") { type = NavType.StringType })
            ){
                val messageId = it.arguments?.getString("messageId")

                ReviewContentScreen(messageId = messageId!!)
            }
            composable(
                route = "chat/{chatID}",
                arguments = listOf(navArgument("chatID") { type = NavType.StringType })
            ){
                val chatID = it.arguments?.getString("chatID")
                if (chatID != null) {
                    ChatScreen(navController = navController, chatID = chatID )
                }
            }

        }
    }

}

@Composable
fun Layout(navController: NavController, screen: @Composable () -> Unit) {
    Scaffold(
        bottomBar = { MainBottomBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            screen()
        }
    }
}

@Composable
fun MainBottomBar(navController: NavController) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF79A3B1)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Chat") },
            label = { Text("Chat") },
            selected = navController.currentBackStackEntry?.destination?.route == "chat",
            onClick = { navController.navigate("chatList"){
                launchSingleTop = true
            } }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = navController.currentBackStackEntry?.destination?.route == "home",
            onClick = { navController.navigate("home"){
                launchSingleTop = true
            } }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = navController.currentBackStackEntry?.destination?.route == "profile",
            onClick = { navController.navigate("profile"){
                launchSingleTop = true
            } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = currentBackStackEntry?.destination?.route != "home"
    val canAddChatroom = currentBackStackEntry?.destination?.route == "chatList"
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            actionIconContentColor = Color.Black
        ),
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = getTitle(navController),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nanumsquareroundb)),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        navigationIcon = {
           if(canNavigateBack) {
               IconButton(onClick = {navController.popBackStack()}){
                   Icon(imageVector = Icons.Default.ArrowBack,
                       contentDescription = "Back"
                   )
               }
           } else {
               Spacer(modifier = Modifier.width(48.dp))
           }
        },
        actions = {
            if(canAddChatroom) {
            IconButton(onClick = {
                navController.navigate("chatCreate")
            }){
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add Chatroom"
                )
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
        }
    )
}

@Composable
fun getTitle(navController: NavController): String {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    return when (currentBackStackEntry?.destination?.route) {
        "home" -> "홈"
        "chatList" -> "채팅방 목록"
        "profile" -> "내 정보"
        "review" -> "리뷰"
        "profileSet" -> "프로필 설정"
        "chatCreate" -> "채팅방 생성"
        "setNickname" -> "닉네임 설정"
        "setPW" -> "비밀번호 설정"
        "statistics" -> "통계"
        else -> "Emago"
    }
}

