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
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.yashkasera.aphrodite.MainActivity
import com.yashkasera.aphrodite.databinding.FragmentLoginBinding
import com.yashkasera.aphrodite.ui.dialogs.DialogData
import com.yashkasera.aphrodite.util.checkNotEmpty
import kotlinx.coroutines.launch

/**
 * @author yashkasera
 * Created 04/12/21 at 6:07 AM
 */
private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bind()
    }

    private fun FragmentLoginBinding.bind() {
        email.checkNotEmpty(email1)
        password.checkNotEmpty(password1)
        login.setOnClickListener {
            if (email.text.isNullOrEmpty())
                email1.error = "Cannot be empty"
            else if (password.text.isNullOrEmpty())
                password1.error = "Cannot be empty"
            else
                lifecycleScope.launch {
                    viewModel.login(
                        email = email.text.toString(),
                        password = password.text.toString()
                    ).onSuccess {
                        Log.i(TAG, "bind: Login Success!")
                        if (it != null) {
                            if (it.isEmailVerified)
                                Intent(requireActivity(), MainActivity::class.java).also { intent ->
                                    startActivity(intent)
                                    requireActivity().finish()
                                }
                            else
                                findNavController().navigate(
                                    LoginFragmentDirections.actionLoginFragmentToBottomMessageDialog(
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
                        when (it) {
                            is FirebaseAuthEmailException -> email1.error =
                                "Invalid Email Address!"
                            is FirebaseAuthInvalidUserException -> email1.error =
                                "User not found!"
                        }
                    }
                }
        }

        signUp.setOnClickListener {
            Log.i(TAG, "bind: signUp Clicked!")
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        forgotPassword.setOnClickListener {
            lifecycleScope.launch {
                viewModel.forgotPassword(email.text.toString())
                    .onSuccess {
                        LoginFragmentDirections.actionLoginFragmentToBottomMessageDialog(
                            DialogData("Password Reset Email Sent Successfully!", "Open Mail") {
                                Intent(Intent.ACTION_MAIN).also { intent ->
                                    intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                                    startActivity(intent)
                                }
                            }

                        )
                    }.onFailure {
                        Toast.makeText(
                            requireContext(),
                            it.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

}