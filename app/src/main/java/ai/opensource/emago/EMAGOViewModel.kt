package ai.opensource.emago

import ai.opensource.emago.data.CHATS
import ai.opensource.emago.data.ChatData
import ai.opensource.emago.data.ChatUser
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import ai.opensource.emago.data.Event
import ai.opensource.emago.data.MESSAGES
import ai.opensource.emago.data.Message
import ai.opensource.emago.data.USER_NODE
import ai.opensource.emago.data.UserData
import ai.opensource.emago.util.sendPostRequest
import android.icu.util.Calendar
import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class EMAGOViewModel @Inject constructor(
    val auth: FirebaseAuth,
    var db: FirebaseFirestore,
    val storage: FirebaseStorage,
) : ViewModel() {

    var inProcess = mutableStateOf(false)
    var inProcessChats = mutableStateOf(false)
    val eventMutableState = mutableStateOf<Event<String?>>(Event(null))
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val chats = mutableStateOf<List<ChatData>?>(listOf())

    val chatMessages = mutableStateOf<List<Message>?>(listOf())
    val inProgressChatMessage = mutableStateOf(false)
    var currentChatMessageListner: ListenerRegistration? = null

    val myChatMessages = mutableStateOf<List<Message>?>(listOf())
    val reviewMessage = mutableStateOf<Message>(Message(timestamp = Timestamp.now()))

    val singleChatRoom = mutableStateOf<ChatData>(ChatData())

    init {
        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }

    fun populateMessages(chatId: String) {
        inProgressChatMessage.value = true
        currentChatMessageListner =
            db.collection(MESSAGES).whereEqualTo("chatId", chatId)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        handleException(error)
                    }
                    if (value != null) {
                        chatMessages.value = value.documents.mapNotNull {
                            it.toObject<Message>()
                        }.sortedBy { it.timestamp }
                        inProgressChatMessage.value = false
                    }
                }
    }

    fun depopulateMessage() {
        chatMessages.value = listOf()
        currentChatMessageListner = null
    }


    fun signUp(name: String, number: String, email: String, password: String) {
        inProcess.value = true

        if (name.isEmpty() or number.isEmpty() or email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = " Please Fill All fields ")
            return
        }

        inProcess.value = true

        db.collection(USER_NODE).whereEqualTo("number", number).get().addOnSuccessListener {

            if (it.isEmpty) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() {


                    if (it.isSuccessful) {
                        signIn.value = true
                        createOrUpdateProfile(name, number)
                        Log.d("TAG", "signUp: User Logged IN")
                    } else {
                        handleException(it.exception, customMessage = "SignUp: User Logged IN")
                    }
                }
            } else {
                handleException(customMessage = "user Already exists")
                inProcess.value = false
            }
        }


    }

    fun loginIn(email: String, password: String) {

        if (email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = " Please Fill the all Fields")
            return
        } else {
            inProcess.value = true
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        signIn.value = true
                        inProcess.value = false
                        auth.currentUser?.uid?.let {
                            getUserData(it)
                        }
                    } else {
                        inProcess.value = false
                    }
                }
        }
    }

    fun uploadProfileImage(uri: Uri) {
        uploadImage(uri) {
            createOrUpdateProfile(imageurl = it.toString())
        }
    }

    fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {
        inProcess.value = true

        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("images/$uuid")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {

            val result = it.metadata?.reference?.downloadUrl

            result?.addOnSuccessListener(onSuccess)
            inProcess.value = false


        }.addOnFailureListener {
            handleException(it)
        }

    }

    fun createOrUpdateProfile(
        name: String? = null,
        number: String? = null,
        imageurl: String? = null,
        stateMsg: String? = null
    ) {
        var uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name, // 이름이 없을 때는 기존이름으로 같은 느낌?
            number = number ?: userData.value?.number,
            imageUrl = imageurl ?: userData.value?.imageUrl,
            stateMsg = stateMsg ?: userData.value?.stateMsg,
        )

        uid?.let {
            inProcess.value = true
            db.collection(USER_NODE).document(uid).get().addOnCompleteListener {

                if (it.result.exists()) {
                    // Update user data
                    val updates = hashMapOf<String, Any?>()
                    if (name != null) updates["name"] = name
                    if (number != null) updates["number"] = number
                    if (imageurl != null) updates["imageUrl"] = imageurl
                    if (stateMsg != null) updates["stateMsg"] = stateMsg

                    db.collection(USER_NODE).document(uid).update(updates)
                        .addOnSuccessListener {
                            inProcess.value = false
                            getUserData(uid)
                        }
                        .addOnFailureListener { e ->
                            handleException(e, "Cannot Update User")
                            inProcess.value = false
                        }
                } else {

                    db.collection(USER_NODE).document(uid).set(userData)
                    inProcess.value = false
                    getUserData(uid)
                }
            }
                .addOnFailureListener {
                    handleException(it, " Cannot Retrieve User")
                }
        }
    }

    private fun getUserData(uid: String) {
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error, " Cannot Retrieve User")
            }

            if (value != null) {
                var user = value.toObject<UserData>()
                userData.value = user
                inProcess.value = false
            }
        }
    }

    fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.d("LiveChatApp", "live chat exception: ", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isNullOrEmpty()) errorMsg else customMessage

        eventMutableState.value = Event(message)
        inProcess.value = false
    }

    fun logout() {
        auth.signOut()
        signIn.value = false
        userData.value = null
        eventMutableState.value = Event("Logged Out")
    }

    fun sendResetPassword() {
        // 현재 로그인된 사용자의 이메일 주소 가져오기
        val user = auth.currentUser
        user?.let {
            val emailAddress = user.email

            // 이메일 주소가 null이 아닌 경우 비밀번호 재설정 이메일 보내기
            if (emailAddress != null) {
                auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // 이메일 전송 성공
                            println("Reset password email sent to $emailAddress")
                        } else {
                            // 이메일 전송 실패
                            println("Failed to send reset password email: ${task.exception?.message}")
                        }
                    }
            } else {
                // 이메일 주소가 없는 경우 처리
                println("No email address associated with this account.")
            }
        } ?: run {
            // 로그인된 사용자가 없는 경우 처리
            println("No user is currently logged in.")
        }
    }

    fun onReviewComplete() {

    }

    fun countReview() {}

    fun updateChatRoom() {

    }

    fun getAllUserMessagesForDate(selectedDate: LocalDate) {
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            // 선택한 날짜를 Date 객체로 변환
            val startOfDay = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            val endOfDay = Date.from(selectedDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusSeconds(1).toInstant())

            Log.d("test", "Start of Day Timestamp: ${Timestamp(startOfDay)}")
            Log.d("test", "End of Day Timestamp: ${Timestamp(endOfDay)}")

            // Firestore에서 쿼리 수행
            db.collection(MESSAGES)
                .whereEqualTo("user.userId", currentUserId)
                .whereGreaterThanOrEqualTo("timestamp", Timestamp(startOfDay))
                .whereLessThanOrEqualTo("timestamp", Timestamp(endOfDay))
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        Log.d("test", "No messages found for the specified date range.")
                    } else {
                        for (document in result) {
                            Log.d("test", "Message Document: ${document.id} => ${document.data}")
                        }
                    }

                    val userMessages = result.documents.mapNotNull { document ->
                        document.toObject<Message>()?.apply {
                            id = document.id // 문서 ID를 Message 객체에 설정
                        }
                    }
                    myChatMessages.value = userMessages
                }
                .addOnFailureListener { exception ->
                    Log.e("test", "Error getting documents: ", exception)
                    handleException(exception)
                }
        } else {
            handleException(customMessage = "User not signed in")
        }
    }





    fun getAllChatData() {
        db.collection(CHATS).get()
            .addOnSuccessListener { result ->
                // 모든 문서를 Chat 객체로 변환하여 리스트로 만듭니다.
                val chatList = result.documents.mapNotNull { document ->
                    document.toObject<ChatData>()?.apply {
                        id = document.id // 문서 ID를 Chat 객체의 id 필드에 할당
                    }
                }
                // chats LiveData에 리스트를 할당합니다.
                chats.value = chatList
            }
            .addOnFailureListener { exception ->
                // 에러 처리
                handleException(exception)
            }
    }

    fun onAddChat(title: String, description: String, imageUrl: String? = "") {
        if (title.isEmpty()) {
            handleException(customMessage = "Number must be contain digits only!")
        } else {
            var uid = auth.currentUser?.uid
            db.collection(CHATS).document()
                .set(ChatData(userId = uid, title = title, description = description, imageUrl = imageUrl))
                .addOnSuccessListener {

                    // 채팅방 목록 불러오기
                    getAllChatData()


                }
        }
    }

    fun onSendReply(chatId: String, message: String) {
        val time = Timestamp.now()
        val chatUser: ChatUser = ChatUser(
            userData.value?.userId,
            userData.value?.name,
            userData.value?.imageUrl,
            userData.value?.number
        )
        val msg: Message = Message("", chatId, chatUser, time, message)

        // Firestore에 문서를 추가하고 문서 ID를 가져옴
        db.collection(MESSAGES).add(msg).addOnSuccessListener { documentReference ->
            val documentId = documentReference.id

            // 문서 ID를 포함한 JSON 본문 생성
            val jsonBody = """{"messageId": "$documentId", "message": "$message"}"""


            Log.d("test", "onSendReply: $jsonBody")

            // POST 요청을 보낼 URL
            val url = "http://huseong.iptime.org:8000/api/emago"

            // 네트워크 요청을 비동기적으로 수행
            GlobalScope.launch(Dispatchers.IO) {
                val response = sendPostRequest(url, jsonBody)
                println(response)
            }
        }.addOnFailureListener { e ->
            e.printStackTrace()
        }
    }

    fun getMessageById(messageId: String) {
        db.collection(MESSAGES).document(messageId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val message = document.toObject<Message>()
                    message?.id = document.id
                    Log.d("test", "Message Document: ${document.id} => ${document.data}")
                    // 원하는 작업 수행
                    reviewMessage.value = message!!
                } else {
                    Log.d("test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("test", "Error getting document: ", exception)
                handleException(exception)
            }
    }

    fun getChatRoomById(chatId: String) {
        db.collection(CHATS).document(chatId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val chatRoom = document.toObject<ChatData>()
                    chatRoom?.id = document.id
                    Log.d("test", "Message Document: ${document.id} => ${document.data}")
                    // 원하는 작업 수행
                    singleChatRoom.value = chatRoom!!
                } else {
                    Log.d("test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("test", "Error getting document: ", exception)
                handleException(exception)
            }
    }

    fun getCurrentUserEmail(): String? {
        val user = auth.currentUser
        return user?.email
    }
}

