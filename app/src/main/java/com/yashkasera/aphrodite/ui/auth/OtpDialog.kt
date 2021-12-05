package com.yashkasera.aphrodite.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yashkasera.aphrodite.databinding.DialogOtpBinding
import com.yashkasera.aphrodite.util.checkNotEmpty

/**
 * @author yashkasera
 * Created 04/12/21 at 8:18 PM
 */
private const val TAG = "OtpDialog"

class OtpDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogOtpBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOtpBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bind()
    }
    
    private fun DialogOtpBinding.bind(){
        otp.checkNotEmpty(otp1)
    }


}