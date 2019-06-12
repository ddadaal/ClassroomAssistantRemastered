package nju.classroomassistant.student.ui.question;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.os.Handler;

import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage;
import nju.classroomassistant.shared.messages.raisequestion.StudentRaiseQuestionMessage;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.CommunicationBasicService;
import nju.classroomassistant.student.ui.OperationResult;

public class QuestionViewModel extends ViewModel {

    private CommunicationBasicService basicService = CommunicationBasicService.getInstance();

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

        final Handler handler = new Handler();
        AsyncTask.execute(() -> {

            OperationResult operationResult;

            if (basicService.writeToServer(new StudentRaiseQuestionMessage(question))) {
                operationResult = new OperationResult(true, null);
            } else {
                operationResult = new OperationResult(false, R.string.network_error);
            }
            handler.post(() -> {
                questionResult.setValue(operationResult);
            });

        });
    }


}
