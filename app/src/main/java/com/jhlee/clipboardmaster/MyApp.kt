package com.jhlee.clipboardmaster;

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner


// 포그라운드 백그라운드 상태 확인하는 클래스
// 백그라운드에서 클립보드 내용 변경 일어나면 확인하기 위해 만듦
// 백그라운드에서 클립 변경이 있으면 앱에서 접근도 못 하는 것 같아서 primary~ 리스너로는 확인 못 함
// isForeground 변수 값 변했을 때 클립보드 값 직접 읽어서 값이 변했다면 fragmentDialog 띄우도록 구현해야함
class MyApp(): Application(), LifecycleObserver {

    private val dialog = SampleFragmentDialog()
    private var preClipData: String = ""
    companion object{
        // 백그라운드 포그라운드 추적
        var isForeground: Boolean = false
        // 현재 액티비티 추적하기 위해서 선언한 변수
        var currentActivity: AppCompatActivity? = null
        // 백그라운드로 변경하기 전 클립보드 텍스트 읽어 저장하기
        // 백그라운드시 포그라운드 시 데이터 값을 비교하여 다르다면 프래그먼트를 띄우는 것이 최종 목표
        var clipboardData: String = ""
    }

//    var fragmentManager: FragmentManager = activity.fragmentManager
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        preClipData = clipboardData
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        Log.i("FOREGROUND", "is foreground")
        isForeground = true
        if (currentActivity != null) {

            if(!dialog.isAdded) {
                if (preClipData != clipboardData) {
                    dialog.show(
                        currentActivity!!.supportFragmentManager, "SampleDialog"
                    )
                    Log.i("FOREGROUND", "dialog is create")
                    preClipData = clipboardData
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        Log.i("FOREGROUND", "is backGround")
        isForeground = false
    }
}
