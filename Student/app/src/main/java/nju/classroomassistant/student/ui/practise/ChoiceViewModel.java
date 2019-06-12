package nju.classroomassistant.student.ui.practise;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nju.classroomassistant.shared.messages.exercise.answer.ChoiceExerciseAnswer;
import nju.classroomassistant.shared.messages.exercise.answer.FillBlankExerciseAnswer;
import nju.classroomassistant.student.R;
import nju.classroomassistant.student.service.CommunicationBasicService;
import nju.classroomassistant.student.ui.OperationResult;

public class ChoiceViewModel extends ViewModel {

    public ChoiceViewModel() {

    }

    private CommunicationBasicService basicService = CommunicationBasicService.getInstance();

    private Set<String> answers = new HashSet<>();

    public void addChoice(String choice) {
        answers.add(choice);
        canCommit.setValue(answers.size() != 0);
    }

    public void removeChoice(String choice) {
        answers.remove(choice);
        canCommit.setValue(answers.size() != 0);
    }

    private MutableLiveData<Boolean> canCommit = new MutableLiveData<>();

    public MutableLiveData<Boolean> getCanCommit() {
        return canCommit;
    }

    private MutableLiveData<OperationResult> operationResult = new MutableLiveData<>();

    public void commit() {

        final Handler handler = new Handler();
        AsyncTask.execute(() -> {

            ChoiceExerciseAnswer exerciseAnswer = new ChoiceExerciseAnswer(new ArrayList<String>(answers));

            OperationResult tempOperationResult;

            if (basicService.writeToServer(exerciseAnswer)) {
                tempOperationResult = new OperationResult(true, null);
            } else {
                tempOperationResult = new OperationResult(false, R.string.network_error);
            }
            handler.post(() -> {
                operationResult.setValue(tempOperationResult);
            });

        });
    }

    public MutableLiveData<OperationResult> getOperationResult() {
        return operationResult;
    }
}
