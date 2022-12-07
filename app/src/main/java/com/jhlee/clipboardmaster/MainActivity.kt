package com.jhlee.clipboardmaster

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.*
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.jhlee.clipboardmaster.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var copyClipBtn: Button
    private lateinit var receiveTextView: TextView
    private lateinit var sendTextViewBtn: Button
    private lateinit var editTextViewBox: EditText

    private lateinit var changeActivityBt: Button

    // 구현해야 할 기능 버튼 두가지
    private lateinit var mainBtn: Button
    private lateinit var subBtn: Button
    var isRegisted: Boolean = false
    var deviceId: String ?= null
    var deviceName: String ?= null

    private lateinit var clipboardManager: ClipboardManager

    private lateinit var dialog: SampleFragmentDialog
    private lateinit var myApp: MyApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 다른 앱의 알림을 읽어오기 위해 권한을 부여받는 코드
        if (!isNotificationPermissionGranted()) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        MyApp.currentActivity = this

        binding = ActivityMainBinding.inflate(layoutInflater)

        // 구현해야 할 기능 버튼 두가지
        mainBtn = binding.mainBtn
        subBtn = binding.subBtn
        //val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID);
        deviceName = Build.MODEL

        // fragment dialog 를 띄우기 위해 임시로 만든 버튼, mainActivity 만든 이후 없에야 함
        copyClipBtn = binding.copyClipBtn
        // 아이템 길게 클릭 시 복사하는 기능 임시구현, 밑의 버튼 클릭 시 내용 변경됨 (후에 채팅에서 사용함)
        receiveTextView = binding.receiveTextView
        // 텍스트박스 내용 읽어서 텍스트뷰에 전송
        sendTextViewBtn = binding.sendTextViewBtn
        // 채팅 시 텍스트박스에 담은 메시지를 전송버튼을 통해 전송
        editTextViewBox = binding.editTextViewBox

        // 임시로 만든 기기목록을 띄우는 버튼, 후에 만들 서브기기 메인기기 등록 버튼의 역할과 같음
        changeActivityBt = binding.changeActivityBt

        // 클립보드 읽는 기능을 사용하기 위해 생성한 클립보드 매니저 객체
        clipboardManager =
            applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        dialog = SampleFragmentDialog()

        setContentView(binding.root)

        // 메인기기 버튼과 서브기기 버튼
        subBtn.setOnClickListener{
            var registerFragmentDialog = RegisterFragmentDialog(this)
            if (!registerFragmentDialog.isAdded) {
                registerFragmentDialog.show(
                    supportFragmentManager, "SampleDialog"
                )
                Log.i("subRegister", "띄움")
            } else {
                Log.e("subRegister", "못띄움")
            }
        }


        // 복사합니다 버튼
        copyClipBtn.setOnClickListener {
            if (!dialog.isAdded) {
                dialog.show(
                    supportFragmentManager, "SampleDialog"
                )
                Log.i("copyClipBtn", "복사됨")
            } else {
                Log.e("copyClipBtn", "복사되지 않음")
            }
        }

        receiveTextView.setOnLongClickListener {
            val text: String = receiveTextView.text.toString()
            createClipData(text)
            true
        }

        // 전송버튼을 누르면 텍스트뷰에 텍스트 전달
        sendTextViewBtn.setOnClickListener {
            sendTextToTextView()
        }

        // 엔터 입력 시 전송버튼을 누른것과 같은 역할을 함
        editTextViewBox.setOnKeyListener { v: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                sendTextToTextView()
            }
            true
        }

        // 다른 액티비티로 날라감
        changeActivityBt.setOnClickListener {
            var myIntent = Intent(this, DeviceListActivity::class.java)
            startActivity(myIntent)
        }

        isRegisted = findRegistedState()
    }

    // 등록된 기기인지 아닌지를 DB 에서 받아오는 함수
    private fun findRegistedState(): Boolean {
        return true
    }

    // 화면을 나갔다가 돌아오는 경우 처리해주려고 했는데 작동하지 않음
    // MyApp 클래스 사용해서 백그라운드에서 포그라운드로 전환했을 때
    // 클립보드의 내용이 변경되었다면 프래그먼트 다이얼로그를 띄우면 될 듯
    override fun onResume() {
        super.onResume()

    }

    // 안드로이드 보안정책 변경으로 인해 이 안에서만 클립보드 데이터를 읽을 수 있음.
    @SuppressLint("SuspiciousIndentation")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            // 클립보드 값이 변경되었을 경우
            var pasteData: String = ""
                if (!clipboardManager.hasPrimaryClip()) {

                } else if ((clipboardManager.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN)) == false) {

                } else {
                    // 클립보드에 PlainText 가 담겨있어 데이터를 가져올 수 있는 경우
                    val item =
                        clipboardManager.primaryClip?.getItemAt(0)?.coerceToText(applicationContext)

                    if (!item.isNullOrEmpty()) {
                        pasteData = item.toString()
                        MyApp.clipboardData = pasteData
                        Log.i("MainActivity", pasteData)
                    }
                }

        }
    }

    private fun createClipData(message: String) {
        // val clipboardManager: ClipboardManager = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clipData: ClipData = ClipData.newPlainText("message", message)

        // 클립보드에 배치
        clipboardManager.setPrimaryClip(clipData)

        val text:String = "복사되었습니다."
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    private fun sendTextToTextView() {
        // editText 내용 읽고 비우기
        val text: String = editTextViewBox.text.toString()
        editTextViewBox.setText("")
        // textView 에 전송
        receiveTextView.text = text
    }


    // 알림 읽어오는 권한 부여하는 함수
    private fun isNotificationPermissionGranted(): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            return notificationManager.isNotificationListenerAccessGranted(ComponentName(application, MyNotificationListenerService::class.java))
        }
        else {
            return NotificationManagerCompat.getEnabledListenerPackages(applicationContext).contains(applicationContext.packageName)
        }
    }

}