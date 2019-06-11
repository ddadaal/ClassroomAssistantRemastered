package nju.classroomassistant.student.ui.practise;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import nju.classroomassistant.shared.messages.exercise.answer.FillBlankExerciseAnswer;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.CommunicationBasicService;
import nju.classroomassistant.student.ui.OperationResult;

/**
 * Created on 2019-06-10.
 * Description:
 *
 * @author iznauy
 */
public class GapFillingPractiseViewModel extends ViewModel {

    private CommunicationBasicService basicService = CommunicationBasicService.getInstance();

    private MutableLiveData<Boolean> canCommit = new MutableLiveData<>();

    private MutableLiveData<OperationResult> commitResult = new MutableLiveData<>();

    public MutableLiveData<OperationResult> getCommitResult() {
        return commitResult;
    }

    public MutableLiveData<Boolean> getCanCommit() {
        return canCommit;
    }

    void updateCanCommit(boolean state) {
        canCommit.setValue(state);
    }

    void commitPractise(String content) {
        FillBlankExerciseAnswer answer = new FillBlankExerciseAnswer(content);
        if (basicService.writeToServer(answer)) {
            commitResult.setValue(new OperationResult(true, null));
        } else {
            commitResult.setValue(new OperationResult(false, R.string.network_error));
        }
    }

}
