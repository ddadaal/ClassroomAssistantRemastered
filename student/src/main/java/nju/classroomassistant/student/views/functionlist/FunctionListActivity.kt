package nju.classroomassistant.student.views.functionlist

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

import kotlinx.android.synthetic.main.activity_function_list.*
import nju.classroomassistant.student.R
import nju.classroomassistant.student.extensions.jumpTo
import nju.classroomassistant.student.systemstate.DiscussionState
import nju.classroomassistant.student.systemstate.SystemState
import nju.classroomassistant.student.views.discussion.DiscussionActivity
import java.util.*


class FunctionListActivity : AppCompatActivity(), Observer {
    override fun update(observable: Observable?, params: Any?) {

        // 这个方法是在Socket监听线程里调用到的，但是要操作UI，所以得runOnUiThread
        // 为了简单，直接不管什么变化 都全部同步一遍

        runOnUiThread {
            syncToSystemState()
        }
    }

    private fun syncToSystemState() {

    }


    private fun setEnabled(card: MaterialCardView, enabled: Boolean) {
        card.isEnabled = enabled

        card.setCardBackgroundColor(
            ContextCompat.getColor(
                baseContext,
                if (enabled) {
                    R.color.colorCardEnabled
                } else {
                    R.color.colorCardDisabled
                }
            )
        )

        card.cardElevation = if (enabled)  { 100f } else { 0f }

        card.isClickable = enabled

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_list)

        btn_discussion.setOnClickListener { jumpTo<DiscussionActivity>() }

        syncToSystemState()

//        setEnabled(btn_raise_question, true)

        // 注册变化
        SystemState.raiseQuestion.addObserver(this)
        SystemState.exercise.addObserver(this)
        SystemState.discussion.addObserver(this)

    }

    override fun onStop() {
        super.onStop()

        // 取消注册变化
        SystemState.raiseQuestion.deleteObserver(this)
        SystemState.exercise.deleteObserver(this)
        SystemState.discussion.deleteObserver(this)
    }

}
