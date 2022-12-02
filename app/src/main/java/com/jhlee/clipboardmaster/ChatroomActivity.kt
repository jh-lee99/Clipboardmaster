package com.jhlee.clipboardmaster

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.clipboardmaster.databinding.ActivityChatroomBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class ChatroomActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatroomBinding
    lateinit var btn_exit: ImageButton
    lateinit var btn_submit: Button
    lateinit var txt_title: TextView
    lateinit var edt_message: EditText
    // lateinit var firebaseDatabase: DatabaseReference
    lateinit var recycler_talks: RecyclerView
    // lateinit var chatRoom: ChatRoom
    // lateinit var opponentUser: User
    lateinit var chatRoomKey: String
    lateinit var myUid: String

    lateinit var myChatbt: Button
    lateinit var yourChatbt: Button

    private lateinit var myApp: MyApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatroomBinding.inflate(layoutInflater)

        setContentView(binding.root)
        btn_exit = binding.imgbtnQuit
        btn_submit = binding.btnSubmit
        txt_title = binding.txtTitle
        edt_message = binding.edtMessage
        recycler_talks = binding.recyclerMessages
        myChatbt = binding.myChatbt
        yourChatbt = binding.yourChatbt

        MyApp.currentActivity = this


//        initializeProperty()
//        initializeView()
//        initializeListener()
//        setupChatRooms()
        var messages: ArrayList<Message> = arrayListOf()

        var adapter = chatroomAdapter(this, messages)
        //activity_main.xml에 만들어 놓은 recyclerView의 아이디를 가져온 다음 adapter을 지정하여 줍니다.
        recycler_talks.adapter = adapter
        //마지막으로 recyclerView를 화면에 보여주는 형태를 결정하는 레이아웃 매니저를 연결합니다.
        recycler_talks.layoutManager = LinearLayoutManager(this)
    }


    //데이터를 리스트 형식으로 만들어 주기위한 함수
    fun loadData(type: Int): Message {

        var uid = type
        var title = "0 번째 타이틀 입니다."
        var date = System.currentTimeMillis()

        //리스트 반환
        return Message(uid, title, date)
    }

    data class Message(
        var no:Int ,
        var title:String ,
        var timestamp:Long
    )

    fun initializeProperty() {  //변수 초기화
//        myUid = FirebaseAuth.getInstance().currentUser?.uid!!              //현재 로그인한 유저 id
//        firebaseDatabase = FirebaseDatabase.getInstance().reference!!
//
//        chatRoom = (intent.getSerializableExtra("ChatRoom")) as ChatRoom      //채팅방 정보
//        chatRoomKey = intent.getStringExtra("ChatRoomKey")!!            //채팅방 키
//        opponentUser = (intent.getSerializableExtra("Opponent")) as User    //상대방 유저 정보
    }

    fun initializeView() {    //뷰 초기화
        btn_exit = binding.imgbtnQuit
        edt_message = binding.edtMessage
        recycler_talks = binding.recyclerMessages
        btn_submit = binding.btnSubmit
        txt_title = binding.txtTitle
        // 상대유저정보 데이터베이스에서 받아오기
        //txt_title.text = opponentUser!!.name ?: ""
    }

    fun initializeListener() {   //버튼 클릭 시 리스너 초기화
        btn_exit.setOnClickListener()
        {
            startActivity(Intent(this@ChatroomActivity, MainActivity::class.java))
        }
        btn_submit.setOnClickListener()
        {
            putMessage()
        }
    }

    fun setupChatRooms() {              //채팅방 목록 초기화 및 표시
        if (chatRoomKey.isNullOrBlank())
            setupChatRoomKey()
        else
            setupRecycler()
    }

    fun setupChatRoomKey() {            //chatRoomKey 없을 경우 초기화 후 목록 초기화
//        FirebaseDatabase.getInstance().getReference("ChatRoom")
//            .child("chatRooms").orderByChild("users/${opponentUser.uid}").equalTo(true)    //상대방의 Uid가 포함된 목록이 있는지 확인
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {}
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    for (data in snapshot.children) {
//                        chatRoomKey = data.key!!          //chatRoomKey 초기화
//                        setupRecycler()                  //목록 업데이트
//                        break
//                    }
//                }
//            })
    }

    fun putMessage() {       //메시지 전송
        try {
            //var message = Message(myUid, getDateTimeString(), edt_message.text.toString())    //메시지 정보 초기화
            Log.i("ChatRoomKey", chatRoomKey)
//            FirebaseDatabase.getInstance().getReference("ChatRoom").child("chatRooms")
//                .child(chatRoomKey).child("messages")                   //현재 채팅방에 메시지 추가
//                .push().setValue(message).addOnSuccessListener {
//                    Log.i("putMessage", "메시지 전송에 성공하였습니다.")
//                    edt_message.text.clear()
//                }.addOnCanceledListener {
//                    Log.i("putMessage", "메시지 전송에 실패하였습니다")
//                }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("putMessage", "메시지 전송 중 오류가 발생하였습니다.")
        }
    }

    fun getDateTimeString(): String {          //메시지 보낸 시각 정보 반환
        try {
            var localDateTime = LocalDateTime.now()
            localDateTime.atZone(TimeZone.getDefault().toZoneId())
            var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            return localDateTime.format(dateTimeFormatter).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("getTimeError")
        }
    }

    fun setupRecycler() {            //목록 초기화 및 업데이트
        recycler_talks.layoutManager = LinearLayoutManager(this)
        //recycler_talks.adapter = RecyclerMessagesAdapter(this, chatRoomKey, opponentUser.uid)
    }

    // 안드로이드 보안정책 변경으로 인해 이 안에서만 클립보드 데이터를 읽을 수 있음.
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            var clipboardManager = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 클립보드 값이 변경되었을 경우
            var pasteData: String = ""
            if (!clipboardManager.hasPrimaryClip()) {

            } else if ((clipboardManager.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) == false) {

            } else {
                // 클립보드에 PlainText 가 담겨있어 데이터를 가져올 수 있는 경우
                val item =
                    clipboardManager.primaryClip?.getItemAt(0)?.coerceToText(applicationContext)

                if (!item.isNullOrEmpty()) {
                    pasteData = item.toString()
                    MyApp.clipboardData = pasteData
                    Log.i("ChatroomActivity", pasteData)
                }
            }

        }
    }
}