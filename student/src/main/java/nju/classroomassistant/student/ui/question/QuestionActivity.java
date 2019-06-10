package nju.classroomassistant.student.ui.question;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.GlobalVariables;
import nju.classroomassistant.student.ui.OperationResult;

public class QuestionActivity extends AppCompatActivity {

    private QuestionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setActionBar();

        viewModel = ViewModelProviders.of(QuestionActivity.this).get(QuestionViewModel.class);

        final EditText questionText = findViewById(R.id.question_content);
        final Button commit = findViewById(R.id.submit_question);
        final TextView remindText = findViewById(R.id.teacher_remind_state);

        viewModel.getCanCommit().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null)
                    return;
                commit.setEnabled(aBoolean);
            }
        });

        GlobalVariables.getReminder().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null)
                    return;
                if (aBoolean) {
                    remindText.setText(R.string.remind_active);
                    Toast.makeText(QuestionActivity.this, getString(R.string.remind_start),
                            Toast.LENGTH_SHORT).show();
                } else {
                    remindText.setText(R.string.remind_inactive);
                    Toast.makeText(QuestionActivity.this, getString(R.string.remind_stop),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getQuestionResult().observe(this, new Observer<OperationResult>() {
            @Override
            public void onChanged(@Nullable OperationResult operationResult) {
                if (operationResult == null)
                    return;
                if (operationResult.getSuccess()) {
                    Toast.makeText(QuestionActivity.this, getString(R.string.commit_success), Toast.LENGTH_SHORT)
                            .show();
                    questionText.setText("");
                } else {
                    Toast.makeText(QuestionActivity.this, getString(operationResult.getError()), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        questionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (questionText.getText().toString().trim().length() != 0)
                    viewModel.updateCanCommit(true);
                else
                    viewModel.updateCanCommit(false);
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.commitQuestion(questionText.getText().toString().trim());
            }
        });

    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(R.string.question);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
