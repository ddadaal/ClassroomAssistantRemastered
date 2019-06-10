package nju.classroomassistant.student.ui;

import android.support.annotation.Nullable;

public class OperationResult {

    private boolean success;

    @Nullable
    private Integer error;

    public OperationResult(boolean success, @Nullable Integer error) {
        this.success = success;
        this.error = error;
    }

    public boolean getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
