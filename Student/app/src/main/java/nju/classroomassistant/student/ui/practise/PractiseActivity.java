package nju.classroomassistant.student.ui.practise;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nju.classroomassistant.shared.messages.exercise.type.ChoiceExerciseType;
import nju.classroomassistant.shared.messages.exercise.type.FillBlankExerciseType;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.GlobalVariables;
import nju.classroomassistant.student.ui.OperationResult;


// 当学生还未提交，老师就关闭问题后，显示一个toast：老师已关闭问题，5s后自动退出

public class PractiseActivity extends AppCompatActivity {

    private GapFillingPractiseViewModel gapFillingPractiseViewModel;

    private ChoiceViewModel choiceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBar();

        if (GlobalVariables.getExercise() instanceof FillBlankExerciseType) {
            setContentView(R.layout.activity_practise_gap_filiing);

            gapFillingPractiseViewModel = ViewModelProviders.of(this)
                    .get(GapFillingPractiseViewModel.class);

            final Button commit = findViewById(R.id.submit_gap_filling);
            final EditText gapFillContent = findViewById(R.id.gap_filling_content);

            gapFillContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    gapFillingPractiseViewModel.updateCanCommit(
                            gapFillContent.getText().toString().length() == 0);

                }
            });

            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gapFillingPractiseViewModel.commitPractise(gapFillContent.getText().toString());
                }
            });

            gapFillingPractiseViewModel.getCanCommit().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean == null || !aBoolean)
                        commit.setEnabled(false);
                    else
                        commit.setEnabled(true);
                }
            });

            gapFillingPractiseViewModel.getCommitResult().observe(this, new Observer<OperationResult>() {
                @Override
                public void onChanged(@Nullable OperationResult operationResult) {
                    if (operationResult == null)
                        return;
                    if (operationResult.getSuccess()) {
                        commit.setEnabled(false);
                        gapFillContent.setEnabled(false);
                        Toast.makeText(PractiseActivity.this, R.string.commit_success, Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(PractiseActivity.this, R.string.network_error, Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });

            GlobalVariables.getInExercise().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    commit.setEnabled(false);
                    gapFillContent.setEnabled(false);
                    Toast.makeText(PractiseActivity.this, R.string.practise_finish, Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
            });



        } else if (GlobalVariables.getExercise() instanceof ChoiceExerciseType) {
            final ChoiceExerciseType exercise =(ChoiceExerciseType) GlobalVariables.getExercise();
            setContentView(R.layout.activity_practise_choice);
            choiceViewModel = ViewModelProviders.of(this).get(ChoiceViewModel.class);
            final Button commit = findViewById(R.id.submit_choice);

            CheckBox cb1 = findViewById(R.id.choice_a);
            CheckBox cb2 = findViewById(R.id.choice_b);
            CheckBox cb3 = findViewById(R.id.choice_c);
            CheckBox cb4 = findViewById(R.id.choice_d);
            CheckBox cb5 = findViewById(R.id.choice_e);
            CheckBox cb6 = findViewById(R.id.choice_f);

            final List<CheckBox> checkBoxList = new ArrayList<>();
            checkBoxList.add(cb1);
            checkBoxList.add(cb2);
            checkBoxList.add(cb3);
            checkBoxList.add(cb4);
            checkBoxList.add(cb5);
            checkBoxList.add(cb6);


            LinearLayout layout = findViewById(R.id.practise_choice_layout);

            for (int i = 5; i >= exercise.getOptionsCount(); i--) {
                CheckBox checkBox = checkBoxList.get(i);
                layout.removeView(checkBox);
            }

            for (int i = 0; i < exercise.getOptionsCount(); i++) {
                final CheckBox checkBox = checkBoxList.get(i);
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkBox.isChecked())
                            choiceViewModel.addChoice(checkBox.getText().toString());
                        else
                            choiceViewModel.removeChoice(checkBox.getText().toString());
                    }
                });
            }

            GlobalVariables.getInExercise().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean == null || aBoolean)
                        return;
                    commit.setEnabled(false);
                    for (int i = 0; i < exercise.getOptionsCount(); i++)
                        checkBoxList.get(i).setEnabled(false);
                    Toast.makeText(PractiseActivity.this, R.string.practise_finish, Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
            });

            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choiceViewModel.commit();
                }
            });

            choiceViewModel.getCanCommit().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean == null || !aBoolean)
                        commit.setEnabled(false);
                    else
                        commit.setEnabled(true);
                }
            });

            choiceViewModel.getOperationResult().observe(this, new Observer<OperationResult>() {
                @Override
                public void onChanged(@Nullable OperationResult operationResult) {
                    if (operationResult == null)
                        return;
                    if (operationResult.getSuccess()) {
                        commit.setEnabled(false);

                        for (int i = 0; i < exercise.getOptionsCount(); i++)
                            checkBoxList.get(i).setEnabled(false);

                        Toast.makeText(PractiseActivity.this, R.string.commit_success, Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(PractiseActivity.this, R.string.network_error, Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
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
