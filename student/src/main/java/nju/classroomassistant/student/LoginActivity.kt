package nju.classroomassistant.student

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import androidx.appcompat.app.AppCompatActivity
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter

import android.content.Context

import kotlinx.android.synthetic.main.activity_login.*
import nju.classroomassistant.shared.messages.login.LoginResponse
import nju.classroomassistant.student.extensions.dialog
import nju.classroomassistant.student.extensions.jumpTo
import nju.classroomassistant.student.extensions.snackbar
import nju.classroomassistant.student.functionlist.FunctionListActivity
import nju.classroomassistant.student.network.SocketClient
import nju.classroomassistant.student.util.HistoryStack
import java.io.IOException

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private val STUDENT_ID_HISTORY = "STUDENT_ID_HISTORY"


    // 自动填充并记住最新的输入
    private lateinit var history: HistoryStack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.
        populateAutoComplete()

        input_student_id.setText(history.latest)


        sign_in_button.setOnClickListener { attemptLogin() }
    }

    private fun persistHistory() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        with(sharedPref.edit()) {
            putString(STUDENT_ID_HISTORY, history.serialize())
            apply()
        }
    }

    private fun populateAutoComplete() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        if (sharedPref.contains(STUDENT_ID_HISTORY)) {
            history = HistoryStack.fromSerialized(sharedPref.getString(STUDENT_ID_HISTORY, null)!!)
        } else {
            history = HistoryStack()
            persistHistory()
        }

        val adapter = ArrayAdapter(
            this@LoginActivity,
            android.R.layout.simple_dropdown_item_1line, history
        )

        input_student_id.setAdapter(adapter)
    }

    private fun syncAutoCompleteList() {
        // write to shared preferences
        persistHistory()

        // notify change
        (input_student_id.adapter as ArrayAdapter<String>).notifyDataSetChanged()
    }

    private fun setInputEnabled(enabled: Boolean) {
        input_student_id.isEnabled = enabled
        sign_in_button.isEnabled = enabled
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {

        // Reset errors.
        input_student_id.error = null

        // Store values at the time of the login attempt.
        val emailStr = input_student_id.text.toString()

        var cancel = false
        var focusView: View? = null


        // Check for a valid email address.
        if (!isStudentIdValid(emailStr)) {
            input_student_id.error = getString(R.string.error_invalid_email)
            focusView = input_student_id
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)

            setInputEnabled(false)

            // Add the input to auto complete list
            val input = input_student_id.text.toString()
            history.append(input)
            syncAutoCompleteList()

            AsyncTask.execute {
                try {
                    val client = SocketClient(input_student_id.text.toString())
                    val response = client.login()

                    if (response == LoginResponse.ERROR) {
                        runOnUiThread {
                            snackbar("登录出错，请重试。")
                        }
                    } else {
                        SocketClient.current = client
                        jumpTo<FunctionListActivity>()
                    }
                } catch (e: IOException) {
                    runOnUiThread {
                        dialog {
                            it.setMessage("未能连接上服务器。\n请检查是否已连接上校园网，或者检查老师是否已经打开电脑端程序。")
                            it.setPositiveButton("好", null)
                        }
                    }
                }
                runOnUiThread {
                    setInputEnabled(true)
                    showProgress(false)
                }

            }

        }
    }

    private fun isStudentIdValid(studentId: String): Boolean {

        return studentId.toIntOrNull() != null
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
            .setDuration(shortAnimTime)
            .alpha((if (show) 1 else 0).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    login_progress.visibility = if (show) View.VISIBLE else View.GONE
                }
            })

    }
}
