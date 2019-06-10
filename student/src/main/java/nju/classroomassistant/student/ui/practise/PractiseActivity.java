package nju.classroomassistant.student.ui.practise;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import nju.classroomassistant.student.R;

public class PractiseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise);
        setActionBar();
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




}
