package ai.opensource.emago.util

import ai.opensource.emago.EMAGOViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.Timestamp
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun CommonProgressBar() {
    Row(modifier = Modifier
        .alpha(0.5f)
        .background(Color.LightGray)
        .clickable(enabled = false) {}
        .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CommonDivider() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding(top = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun CheckSignedIn(vm: EMAGOViewModel, navController: NavController) {
    val alreadySignIn = remember { mutableStateOf(false) }

    val signIn = vm.signIn.value
    if (signIn && !alreadySignIn.value) {
        alreadySignIn.value = true
        navController.navigate("home") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
}

@Composable
fun CommonImage(
    data: String?,
    contentScale: ContentScale = ContentScale.Crop
) {
    val painter = rememberImagePainter(data = data)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.wrapContentSize(),
        contentScale = contentScale
    )
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp,
        modifier = Modifier.padding(8.dp)
    )
}

fun sendPostRequest(url: String, jsonBody: String): String? {
    val client = OkHttpClient()

    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body: RequestBody = jsonBody.toRequestBody(mediaType)

    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    return try {
        val response: Response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        response.body?.string()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun previewNavController(): NavController {
    val context = LocalContext.current
    return TestNavHostController(context)
}

fun LocalDate.toFormattedString():String{
    return this.format(DateTimeFormatter.ISO_LOCAL_DATE)
}

fun String.toLocalDate():LocalDate{
    return LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
}

fun extractTimeFromTimestampString(timestampString: String): String {
    // "Sat Jun 01 21:27:27 GMT+09:00 2024" 형식의 문자열에서 시간 부분을 슬라이싱
    val timeStartIndex = 11
    val timeEndIndex = 19
    return timestampString.substring(timeStartIndex, timeEndIndex)
}