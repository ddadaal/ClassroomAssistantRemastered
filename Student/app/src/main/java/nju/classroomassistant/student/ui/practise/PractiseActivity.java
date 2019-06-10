package nju.classroomassistant.student.ui.practise;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nju.classroomassistant.shared.messages.exercise.type.ChoiceExerciseType;
import nju.classroomassistant.shared.messages.exercise.type.FillBlankExerciseType;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.GlobalVariables;


// 当学生还未提交，老师就关闭问题后，显示一个toast：老师已关闭问题，5s后自动退出

public class PractiseActivity extends AppCompatActivity {

    private GapFillingPractiseViewModel gapFillingPractiseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBar();

        if (GlobalVariables.getExercise() instanceof FillBlankExerciseType) {
            setContentView(R.layout.activity_practise_gap_filiing);



        } else if (GlobalVariables.getExercise() instanceof ChoiceExerciseType) {
            setContentView(R.layout.activity_practise_choice);
        }

    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(R.string.practise_inactive);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}
