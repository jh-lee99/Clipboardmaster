package com.jhlee.clipboardmaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.jhlee.clipboardmaster.databinding.FragmentClipboardBinding

class SampleFragmentDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = true
    }

    private lateinit var binding: FragmentClipboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClipboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = "전송할 기기를 선택해주세요."

        //binding.tvSample.text = text

        binding.sendBtn.setOnClickListener {
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            this.dismiss()
        }

        binding.cancleBtn.setOnClickListener {
            val text = "취소합니다."
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
    }

}