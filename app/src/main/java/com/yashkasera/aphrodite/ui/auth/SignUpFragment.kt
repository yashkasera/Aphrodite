package com.yashkasera.aphrodite.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.yashkasera.aphrodite.databinding.FragmentSignupBinding
import com.yashkasera.aphrodite.model.User
import com.yashkasera.aphrodite.service.RetrofitService
import com.yashkasera.aphrodite.ui.dialogs.DialogData
import com.yashkasera.aphrodite.util.FCM_TOKEN
import com.yashkasera.aphrodite.util.SharedPrefUtil.getPrefs
import com.yashkasera.aphrodite.util.checkNotEmpty
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * @author yashkasera
 * Created 04/12/21 at 6:06 AM
 */
private const val TAG = "SignUpFragment"

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bind()
    }

    private fun FragmentSignupBinding.bind() {
        signIn.setOnClickListener {
            findNavController().popBackStack()
        }
        name.checkNotEmpty(name1)
        email.checkNotEmpty(email1)
        phone.checkNotEmpty(phone1)
        password.checkNotEmpty(password1)
        signUp.setOnClickListener {
            lifecycleScope.launch {
                viewModel.signUp(email.text.toString(), password = password.text.toString())
                    .onSuccess {
                        if (it != null) {
                            val user = User(
                                name = name.text.toString(),
                                phoneNumber = phone.text.toString(),
                                firebaseId = it.uid,
                                email = email.text.toString(),
                                fcmToken = getPrefs(requireContext()).getString(FCM_TOKEN, null),
                                badges = null
                            )
                            RetrofitService.create().signUp(user)
                            it.sendEmailVerification().await()
                            findNavController().navigate(
                                SignUpFragmentDirections.actionSignUpFragmentToBottomMessageDialog(
                                    DialogData(
                                        "Please verify your email to proceed",
                                        "Open Emails"
                                    ) {
                                        Intent(Intent.ACTION_MAIN).also { intent ->
                                            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                                            startActivity(intent)
                                        }
                                    }
                                )
                            )
                        }
                    }.onFailure {
                        Log.e(TAG, "bind: Sign Up Failed", it)
                        when (it) {
                            is FirebaseAuthUserCollisionException -> email1.error =
                                "Email Already Exists!"
                            is FirebaseAuthEmailException -> email1.error = "Invalid Email Address!"
                            is FirebaseAuthWeakPasswordException -> password1.error =
                                "Weak Password!"
                            else ->
                                Toast.makeText(
                                    requireContext(),
                                    "${it.localizedMessage}", Toast.LENGTH_SHORT
                                ).show()
                        }

                    }
            }

        }
    }
}