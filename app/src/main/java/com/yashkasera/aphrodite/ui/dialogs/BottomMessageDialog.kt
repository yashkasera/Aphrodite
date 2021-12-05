package com.yashkasera.aphrodite.ui.dialogs

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yashkasera.aphrodite.databinding.DialogMessageBinding
import java.io.Serializable

/**
 * @author yashkasera
 * Created 05/12/21 at 3:33 AM
 */
private const val TAG = "BottomMessageDialog"

class BottomMessageDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogMessageBinding
    private val args: BottomMessageDialogArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            button.setOnClickListener(args.data.clickListener)
            button.text = args.data.buttonText
            message.text = args.data.message
        }
    }
}

class DialogData(
    val message: String,
    val buttonText:String,
    val clickListener: View.OnClickListener
) : Serializable