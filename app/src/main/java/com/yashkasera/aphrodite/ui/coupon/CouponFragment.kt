package com.yashkasera.aphrodite.ui.coupon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yashkasera.aphrodite.databinding.FragmentCouponBinding
import com.yashkasera.aphrodite.databinding.FragmentHistoryBinding

/**
 * @author yashkasera
 * Created 05/12/21 at 12:13 PM
 */
private const val TAG = "HistoryFragment"

class CouponFragment : Fragment() {
    private lateinit var binding: FragmentCouponBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCouponBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}