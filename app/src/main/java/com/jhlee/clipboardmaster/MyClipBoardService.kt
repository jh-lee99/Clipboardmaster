package com.jhlee.clipboardmaster

import android.app.Service
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class MyClipBoardService: Service(), ClipboardManager.OnPrimaryClipChangedListener {

    var mManager: ClipboardManager? = null

    override fun onCreate() {
        super.onCreate()
        mManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        // 리스너 등록
        mManager!!.addPrimaryClipChangedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 리스너 해제
        mManager!!.removePrimaryClipChangedListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onPrimaryClipChanged() {
//        if (mManager != null && mManager!!.primaryClip != null) {
//            val data = mManager!!.primaryClip
//
//            // 한번의 복사로 복수 데이터를 넣었을 수 있으므로, 모든 데이터를 가져온다.
//            val dataCount = data!!.itemCount
//            for (i in 0 until dataCount) {
//                Log.e("Test", "clip data - item : " + data.getItemAt(i).coerceToText(this))
//            }
//        } else {
//            Log.e("Test", "No Manager or No Clip data")
//        }
        var dialog: SampleFragmentDialog = SampleFragmentDialog()

        if(!mManager?.hasPrimaryClip()!!) {

        } else if((mManager?.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN)) == false) {

        } else {
            // 클립보드에 PlainText 가 담겨있어 데이터를 가져올 수 있는 경우
            if(!dialog.isAdded)
                dialog.show(
                    dialog.childFragmentManager, "SampleDialog"
                )
        }
    }
}