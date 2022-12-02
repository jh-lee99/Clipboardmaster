package com.jhlee.clipboardmaster

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.clipboardmaster.databinding.ActivityDeviceListBinding
import java.util.*

// 화면 1-1을 담당한다.
// 기기목록을 나타냄
class DeviceListActivity : AppCompatActivity() {
    //
    lateinit var deviceListAdapter: DeviceListAdapter
    private lateinit var bindingD: ActivityDeviceListBinding
    val datas = mutableListOf<DeviceListData>()
    private lateinit var rv_DeviceList: RecyclerView
    private lateinit var myApp: MyApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingD = ActivityDeviceListBinding.inflate(layoutInflater)

        MyApp.currentActivity = this

        setContentView(bindingD.root)
        rv_DeviceList = bindingD.rvDeviceList
        rv_DeviceList.addItemDecoration(VerticalItemDecorator(20))
        rv_DeviceList.addItemDecoration(HorizontalItemDecorator(10))
        initRecycler()
    }

    private fun initRecycler() {
        deviceListAdapter = DeviceListAdapter(this)

        rv_DeviceList.adapter = deviceListAdapter

        datas.apply {
            add(DeviceListData(deviceName = "primary device 1", lastMessageTime = Date()))
            add(DeviceListData(deviceName = "primary device 2", lastMessageTime = Date()))
            add(DeviceListData(deviceName = "primary device 3", lastMessageTime = Date()))
            add(DeviceListData(deviceName = "primary device 4", lastMessageTime = Date()))
            add(DeviceListData(deviceName = "primary device 5", lastMessageTime = Date()))

            deviceListAdapter.datas = datas
            deviceListAdapter.notifyDataSetChanged()

        }
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
                    Log.i("DeviceListActivity", pasteData)
                }
            }

        }
    }
}