package com.jhlee.clipboardmaster

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.clipboardmaster.databinding.ListTalkItemMineBinding
import com.jhlee.clipboardmaster.databinding.ListTalkItemOthersBinding
import java.util.*
import kotlin.collections.ArrayList

class chatroomAdapter(
    val context: Context,
    var message: ArrayList<ChatroomActivity.Message>
    //var chatRoomKey: String?,
    //val opponentUid: String?
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var messages: ArrayList<ChatroomActivity.Message> = arrayListOf()     //메시지 목록
    var messageKeys: ArrayList<String> = arrayListOf()   //메시지 키 목록
    init {
        setupMessages()
    }

    fun setupMessages() {
        getMessages()
    }

    fun getMessages() {
        // database 접근해서 채팅 목록 가져오기

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.list_talk_item_mine, parent, false)
                MyMessageViewHolder(ListTalkItemMineBinding.bind(view))
            }
            else -> {      //메시지가 상대 메시지인 경우
                val view =
                    LayoutInflater.from(context)
                        .inflate(R.layout.list_talk_item_others, parent, false)  //상대 메시지 레이아웃으로 초기화
                OtherMessageViewHolder(ListTalkItemOthersBinding.bind(view))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }





    inner class OtherMessageViewHolder(itemView: ListTalkItemOthersBinding) :         //상대 메시지 뷰홀더
        RecyclerView.ViewHolder(itemView.root) {
        var background = itemView.background
        var txtMessage = itemView.txtMessage
        var txtDate = itemView.txtDate
        var txtIsShown = itemView.txtIsShown

        fun bind(position: Int) {           //메시지 UI 항목 초기화
            var message = messages[position]
//            var sendDate = message.sended_date

//            txtMessage.text = message.content

            var sendDate = Date().toString()
            txtDate.text = getDateText(sendDate)



//            if (message.confirmed.equals(true))           //확인 여부 표시
//                txtIsShown.visibility = View.GONE
//            else
//                txtIsShown.visibility = View.VISIBLE

            txtIsShown.visibility = View.GONE

            //setShown(position)             //해당 메시지 확인하여 서버로 전송
        }

        fun getDateText(sendDate: String): String {    //메시지 전송 시각 생성

            var dateText = ""
            var timeString = ""
            if (sendDate.isNotBlank()) {
                timeString = sendDate.substring(8, 12)
                var hour = timeString.substring(0, 2)
                var minute = timeString.substring(2, 4)

                var timeformat = "%02d:%02d"

                if (hour.toInt() > 11) {
                    dateText += "오후 "
                    dateText += timeformat.format(hour.toInt() - 12, minute.toInt())
                } else {
                    dateText += "오전 "
                    dateText += timeformat.format(hour.toInt(), minute.toInt())
                }
            }
            return dateText
        }

//        fun setShown(position: Int) {          //메시지 확인하여 서버로 전송
//            FirebaseDatabase.getInstance().getReference("ChatRoom")
//                .child("chatRooms").child(chatRoomKey!!).child("messages")
//                .child(messageKeys[position]).child("confirmed").setValue(true)
//                .addOnSuccessListener {
//                    Log.i("checkShown", "성공")
//                }
//        }
    }

    inner class MyMessageViewHolder(itemView: ListTalkItemMineBinding) :       // 내 메시지용 ViewHolder
        RecyclerView.ViewHolder(itemView.root) {
        var background = itemView.background
        var txtMessage = itemView.txtMessage
        var txtDate = itemView.txtDate
        var txtIsShown = itemView.txtIsShown

        fun bind(position: Int) {            //메시지 UI 레이아웃 초기화
            var message = messages[position]
//            var sendDate = message.sended_date
//            txtMessage.text = message.content

            var sendDate = Date().toString()
            txtDate.text = getDateText(sendDate)

            txtIsShown.visibility = View.GONE
//            if (message.confirmed.equals(true))
//                txtIsShown.visibility = View.GONE
//            else
//                txtIsShown.visibility = View.VISIBLE
        }

        fun getDateText(sendDate: String): String {        //메시지 전송 시각 생성
            var dateText = ""
            var timeString = ""
            if (sendDate.isNotBlank()) {
                timeString = sendDate.substring(8, 12)
                var hour = timeString.substring(0, 2)
                var minute = timeString.substring(2, 4)

                var timeformat = "%02d:%02d"

                if (hour.toInt() > 11) {
                    dateText += "오후 "
                    dateText += timeformat.format(hour.toInt() - 12, minute.toInt())
                } else {
                    dateText += "오전 "
                    dateText += timeformat.format(hour.toInt(), minute.toInt())
                }
            }
            return dateText
        }
    }
}