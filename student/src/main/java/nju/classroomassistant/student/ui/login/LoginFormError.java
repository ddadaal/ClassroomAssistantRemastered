package nju.classroomassistant.student.ui.login;

import android.support.annotation.Nullable;

class LoginFormError {

    @Nullable
    private Integer usernameError;

    LoginFormError(@Nullable Integer usernameError) {
        this.usernameError = usernameError;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    boolean isDataValid() {
        return usernameError == null;
    }
}
