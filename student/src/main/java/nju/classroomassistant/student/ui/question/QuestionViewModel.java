package nju.classroomassistant.student.ui.question;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import nju.classroomassistant.student.ui.OperationResult;

public class QuestionViewModel extends ViewModel {

    private MutableLiveData<OperationResult> questionResult = new MutableLiveData<>();

    private MutableLiveData<Boolean> canCommit = new MutableLiveData<>();


    MutableLiveData<OperationResult> getQuestionResult() {
        return questionResult;
    }

    MutableLiveData<Boolean> getCanCommit() {
        return canCommit;
    }

    void updateCanCommit(boolean state) {
        canCommit.setValue(state);
    }

    void commitQuestion(String question) {
        questionResult.setValue(new OperationResult(true, null));
    }


}
