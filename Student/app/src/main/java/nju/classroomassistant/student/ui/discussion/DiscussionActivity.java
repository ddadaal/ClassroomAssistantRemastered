package nju.classroomassistant.student.ui.discussion;

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
import android.widget.Toast;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.GlobalVariables;
import nju.classroomassistant.student.ui.OperationResult;

public class DiscussionActivity extends AppCompatActivity {

    private DiscussionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        setActionBar();

        viewModel = ViewModelProviders.of(this).get(DiscussionViewModel.class);

        final EditText discussionContent = findViewById(R.id.discussion_content);
        final Button commit = findViewById(R.id.submit_discussion);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setDiscussion(discussionContent.getText().toString());
            }
        });

        viewModel.getSendDiscussionResult().observe(this, new Observer<OperationResult>() {
            @Override
            public void onChanged(@Nullable OperationResult operationResult) {
                if (operationResult == null)
                    return;
                if (operationResult.getSuccess()) {
                    Toast.makeText(DiscussionActivity.this, getString(R.string.commit_success), Toast.LENGTH_SHORT)
                            .show();
                    discussionContent.setText("");
                } else {
                    Toast.makeText(DiscussionActivity.this, getString(operationResult.getError()), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        viewModel.getCanCommit().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null)
                    return;
                if (aBoolean) {
                    if (GlobalVariables.getInDiscussion().getValue().equals(Boolean.TRUE))
                        commit.setEnabled(true);
                    else {
                        commit.setEnabled(false);
                    }
                } else {
                    commit.setEnabled(false);
                }
            }
        });

        discussionContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.updateCanCommit(discussionContent.getText().toString());
            }
        });

        GlobalVariables.getInDiscussion().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null || !aBoolean) {
                    commit.setEnabled(false);
                    commit.setText(R.string.discussion_finish);
                } else {
                    commit.setText(R.string.commit);
                    if (viewModel.getCanCommit().getValue() != null && viewModel.getCanCommit().getValue())
                        commit.setEnabled(true);
                }
            }
        });

    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(R.string.discussion_inactive);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
