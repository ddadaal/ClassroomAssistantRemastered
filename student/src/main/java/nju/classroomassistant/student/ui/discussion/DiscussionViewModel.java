package nju.classroomassistant.student.ui.discussion;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.CommunicationBasicService;
import nju.classroomassistant.student.ui.OperationResult;

public class DiscussionViewModel extends ViewModel {

    private CommunicationBasicService basicService = CommunicationBasicService.getInstance();

    private MutableLiveData<OperationResult> sendDiscussionResult = new MutableLiveData<>();

    private MutableLiveData<Boolean> canCommit = new MutableLiveData<>();

    public void setDiscussion(String content) {
        StudentSendDiscussionMessage message = new StudentSendDiscussionMessage(content);
        if (basicService.writeToServer(message)) {
            sendDiscussionResult.setValue(new OperationResult(true, null));
        } else {
            sendDiscussionResult.setValue(new OperationResult(false, R.string.network_error));
        }
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
