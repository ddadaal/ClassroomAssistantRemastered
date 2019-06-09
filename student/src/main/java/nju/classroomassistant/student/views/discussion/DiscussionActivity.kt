package nju.classroomassistant.student.views.discussion

import android.os.Bundle
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import nju.classroomassistant.student.R

import kotlinx.android.synthetic.main.activity_discussion.*
import nju.classroomassistant.student.systemstate.SystemState
import java.util.*

class DiscussionActivity : AppCompatActivity(), Observer {
    override fun update(p0: Observable?, p1: Any?) {

    }

    private fun syncState() {
        val started = SystemState.discussion.started


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discussion)
        actionBar?.setDisplayHomeAsUpEnabled(true)


    }

}
