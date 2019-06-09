package nju.classroomassistant.student.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import com.google.android.material.snackbar.Snackbar

inline fun <reified T: Activity> Activity.jumpTo() {
    val intent = Intent(this.baseContext, T::class.java)
    this.startActivity(intent)
}

fun Activity.snackbar(message: String) {
    val bar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
    bar.show()
}

fun Activity.dialog(build: (AlertDialog.Builder) -> Unit) {
    val builder = AlertDialog.Builder(this)
    build(builder)
    builder.create().show()
}
