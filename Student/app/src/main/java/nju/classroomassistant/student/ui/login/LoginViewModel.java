package nju.classroomassistant.student.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.os.Handler;

import nju.classroomassistant.shared.messages.login.LoginResponseMessage;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.CommunicationBasicService;
import nju.classroomassistant.student.service.GlobalVariables;
import nju.classroomassistant.student.ui.OperationResult;

public class LoginViewModel extends ViewModel {

    private CommunicationBasicService basicService = CommunicationBasicService.getInstance();

    private MutableLiveData<LoginFormError> loginFormError = new MutableLiveData<>();

    private MutableLiveData<OperationResult> loginResult = new MutableLiveData<>();


    MutableLiveData<LoginFormError> getLoginFormError() {
        return loginFormError;
    }

    MutableLiveData<OperationResult> getLoginResult() {
        return loginResult;
    }

    void login(final String username) {
        GlobalVariables.init();

        final Handler handler = new Handler();


        AsyncTask.execute(() -> {
            final LoginResponseMessage.Response response = basicService.login(username);

            handler.post(() -> {

                switch (response) {
                    case OK:
                        loginResult.setValue(new OperationResult(true, null));
                        break;
                    case ERROR:
                        loginResult.setValue(new OperationResult(false, R.string.network_error));
                        break;
                    case NOT_ALLOWED:
                        loginResult.setValue(new OperationResult(false, R.string.invalid_username));
                        break;
                    default:
                        break;
                }
            });
        });


    }


    void loginDataChanged(String username) {
        Integer usernameError = null;
        if (!isUserNameValid(username)) {
            usernameError = R.string.invalid_username;
        }
        loginFormError.setValue(new LoginFormError(usernameError));
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        for (char c: username.toCharArray()) {
            if (c > '9' || c < '0') {
                return false;
            }
        }

        return true;
    }
}
