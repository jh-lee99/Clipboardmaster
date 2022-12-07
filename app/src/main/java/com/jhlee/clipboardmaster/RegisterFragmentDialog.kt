package com.jhlee.clipboardmaster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.jhlee.clipboardmaster.databinding.DeviceRegisterDialogBinding

class RegisterFragmentDialog(private val context: AppCompatActivity): DialogFragment() {
    private lateinit var binding: DeviceRegisterDialogBinding
    private var activity: MainActivity = context as MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DeviceRegisterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // DB 를 통해서 읽어들여온 id값
        // deviceId 값이 있으면 return 없으면 -1
        val deviceIdFromDB = activity.deviceId // 일단 기기의 deviceId로 둠
        Log.i("deviceIdFromDB", activity.deviceId.toString())

        // 디바이스 id를 읽어들임
        binding.sinkBtn.setOnClickListener {
            // 텍스트 뷰에서 읽은 값과 DB 에서 읽어들인 값을 비교하여
            // 값이 일치하면 다음 엑티비티를 띄워야 함
            val deviceId = binding.deviceIdTextView.toString()
            Log.i("deviceId", deviceId)

            // 텍스트뷰의 값과 DB의 값이 일치하면
            if (deviceId == deviceIdFromDB) {
                val text = "등록이 완료되었습니다."
                Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            }
            this.dismiss()
        }

        binding.cancleBtn.setOnClickListener {
            val text = "취소합니다."
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
    }
}