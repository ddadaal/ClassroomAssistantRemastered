package nju.classroomassistant.student.ui.discussion;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.os.Handler;

import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.CommunicationBasicService;
import nju.classroomassistant.student.ui.OperationResult;

public class DiscussionViewModel extends ViewModel {

    private CommunicationBasicService basicService = CommunicationBasicService.getInstance();

    private MutableLiveData<OperationResult> sendDiscussionResult = new MutableLiveData<>();

    private MutableLiveData<Boolean> canCommit = new MutableLiveData<>();

    public void setDiscussion(String content) {
        final Handler handler = new Handler();
        AsyncTask.execute(() -> {

            StudentSendDiscussionMessage message = new StudentSendDiscussionMessage(content);

            OperationResult operationResult;

            if (basicService.writeToServer(message)) {
                operationResult = new OperationResult(true, null);
            } else {
                operationResult = new OperationResult(false, R.string.network_error);
            }
            handler.post(() -> {
                sendDiscussionResult.setValue(operationResult);
            });

        });

    }

    public MutableLiveData<OperationResult> getSendDiscussionResult() {
        return sendDiscussionResult;
    }

    public MutableLiveData<Boolean> getCanCommit() {
        return canCommit;
    }

    public void updateCanCommit(String content) {
        canCommit.setValue(content.trim().length() != 0);
    }

}
