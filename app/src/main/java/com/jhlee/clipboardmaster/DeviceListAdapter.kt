package com.jhlee.clipboardmaster

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.parcel.Parcelize
import java.util.*

// data 와 view 객체를 연결시키는 역할을 함
// viewHolder 를 inner class 로 가짐
class DeviceListAdapter(private val context: Context) : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    var datas = mutableListOf<DeviceListData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.device_list_recycler,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtDeviceName: TextView = itemView.findViewById(R.id.tv_rv_deviceName)
        private val txtLastMessage: TextView = itemView.findViewById(R.id.tv_rv_lastMessageTime)

        fun bind(item: DeviceListData) {
            txtDeviceName.text = item.deviceName
            txtLastMessage.text = item.lastMessageTime.toString()

            itemView.setOnClickListener {
                Intent(context, ChatroomActivity::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this)}
            }
        }
    }


}

// Parcelable 를 이용한 직렬화 역직렬화로 putExtra 로 데이터를 전달할 수 있게 함
@Parcelize
data class DeviceListData (
    val deviceName: String,
    val lastMessageTime: Date
): Parcelable