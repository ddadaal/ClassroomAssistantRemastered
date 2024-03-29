package nju.classroomassistant.student.ui.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import nju.classroomassistant.student.R;
import nju.classroomassistant.student.ui.OperationResult;
import nju.classroomassistant.student.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    private static final String AUTO_COMPLETE_KEY = "STUIDCOMPLETE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        // get auto complete
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);


//        // start finding the server
//        NetworkSniffTask sniffTask = new NetworkSniffTask(getApplicationContext());
//        sniffTask.execute();

        final EditText usernameEditText = findViewById(R.id.username);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        viewModel.getLoggingIn().observe(this, (loggingIn) -> {
            if (loggingIn == true) {
                usernameEditText.setEnabled(false);
                loginButton.setEnabled(false);
            } else {
                usernameEditText.setEnabled(true);
                loginButton.setEnabled(true);
            }
        });

        viewModel.getLoginFormError().observe(this, new Observer<LoginFormError>() {
            @Override
            public void onChanged(@Nullable LoginFormError loginFormError) {
                if (loginFormError == null)
                    return;
                loginButton.setEnabled(loginFormError.isDataValid());
                if (loginFormError.getUsernameError() != null)
                    usernameEditText.setError(getString(loginFormError.getUsernameError()));

            }
        });

        viewModel.getLoginResult().observe(this, new Observer<OperationResult>() {
            @Override
            public void onChanged(@Nullable OperationResult loginResult) {
                if (loginResult == null)
                    return;
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getSuccess()) {
                    // 切换到主界面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, getString(loginResult.getError()),
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String username = usernameEditText.getText().toString();
                viewModel.loginDataChanged(username);
            }
        };

        usernameEditText.addTextChangedListener(textWatcher);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                preferences.edit().putString(AUTO_COMPLETE_KEY, username).apply();
                loadingProgressBar.setVisibility(View.VISIBLE);
                viewModel.login(username);

            }
        });

        String savedUsername = preferences.getString(AUTO_COMPLETE_KEY, null);
        if (savedUsername != null) {
            usernameEditText.setText(savedUsername);
            viewModel.loginDataChanged(savedUsername);
        }



    }
}
