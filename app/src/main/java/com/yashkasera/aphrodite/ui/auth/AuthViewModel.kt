package com.yashkasera.aphrodite.ui.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

/**
 * @author yashkasera
 * Created 04/12/21 at 6:07 AM
 */
private const val TAG = "AuthViewModel"

class AuthViewModel : ViewModel() {
    private val auth by lazy { FirebaseAuth.getInstance() }

    suspend fun login(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val res = auth.signInWithEmailAndPassword(email, password).await()
            when {
                res.user == null -> Result.failure(Error("Could not find User!"))
                res.user!!.isEmailVerified.not() -> Result.failure(Error("User has not verified his email!"))
                else -> Result.success(res.user)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(email: String, password: String): Result<FirebaseUser?> {
        try {
            val res = auth.createUserWithEmailAndPassword(email, password).await()
            if (res.user == null)
                return Result.failure(Error("Unable to create a new user! Please try again!"))
            return Result.success(res.user)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun forgotPassword(email: String): Result<String> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success("Password reset email sent successfully!")
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}