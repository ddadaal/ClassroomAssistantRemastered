package nju.classroomassistant.student.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import nju.classroomassistant.student.R;
import nju.classroomassistant.student.ui.OperationResult;

class LoginViewModel extends ViewModel {


    private MutableLiveData<LoginFormError> loginFormError = new MutableLiveData<>();

    private MutableLiveData<OperationResult> loginResult = new MutableLiveData<>();


    MutableLiveData<LoginFormError> getLoginFormError() {
        return loginFormError;
    }

    MutableLiveData<OperationResult> getLoginResult() {
        return loginResult;
    }

    void login(String username) {
        loginResult.setValue(new OperationResult(true, null));
    }

    void loginDataChanged(String username) {
        Integer usernameError = null;
        if (!isUserNameValid(username)) {
            usernameError = R.string.invalid_username;
        }
        loginFormError.setValue(new LoginFormError(usernameError));
    }

    private boolean isUserNameValid(String username) {
        if (username == null || username.length() != 1) {
            return false;
        }
        for (char c: username.toCharArray()) {
            if (c - '0' < 0 || c - '9' > 0) {
                return false;
            }
        }
        return true;
    }
}
