package nju.classroomassistant.student.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import nju.classroomassistant.shared.messages.exercise.ExerciseType;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.GlobalVariables;
import nju.classroomassistant.student.ui.discussion.DiscussionActivity;
import nju.classroomassistant.student.ui.practise.PractiseActivity;
import nju.classroomassistant.student.ui.question.QuestionActivity;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        final Button questionButton = findViewById(R.id.question);
        final Button discussionButton = findViewById(R.id.discussion);
        final Button practiseButton = findViewById(R.id.practise);

        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });

        discussionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        DiscussionActivity.class);
                startActivity(intent);
            }
        });

        practiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PractiseActivity.class);
                startActivity(intent);
            }
        });

        GlobalVariables.getInDiscussion().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null)
                    return;
                if (aBoolean) {
                    discussionButton.setEnabled(true);
                    discussionButton.setText(R.string.discussion_active);
                } else {
                    discussionButton.setEnabled(false);
                    discussionButton.setText(R.string.discussion_inactive);
                }
            }
        });

        GlobalVariables.getInExercise().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null || !aBoolean) {
                    practiseButton.setEnabled(false);
                    practiseButton.setText(R.string.practise_inactive);
                } else {
                    practiseButton.setEnabled(true);
                    practiseButton.setText(R.string.practise_active);
                }

            }
        });

    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quit_login:
                viewModel.logout();
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
