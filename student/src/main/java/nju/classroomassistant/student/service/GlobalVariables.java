package nju.classroomassistant.student.service;

import android.arch.lifecycle.MutableLiveData;
import nju.classroomassistant.shared.messages.exercise.ExerciseAnswer;
import nju.classroomassistant.shared.messages.exercise.ExerciseType;

/**
 * Created on 2019-06-10.
 * Description:
 *
 * @author iznauy
 */
public class GlobalVariables {

    private GlobalVariables() {

    }

    private static CommunicationBasicService basicService = CommunicationBasicService.getInstance();

    private static MutableLiveData<Boolean> inDiscussion;

    private static MutableLiveData<ExerciseType> exercise;

    private static ExerciseAnswer exerciseAnswer = null;

    private static MutableLiveData<Boolean> reminder;

    public static CommunicationBasicService getBasicService() {
        return basicService;
    }

    public static void init() {
        inDiscussion = new MutableLiveData<>();
        exercise = new MutableLiveData<>();
        reminder = new MutableLiveData<>();
        exerciseAnswer = null;
    }

    public static void clear() {
        inDiscussion = null;
        exercise = null;
        exerciseAnswer = null;
        reminder = null;
    }

    public static MutableLiveData<Boolean> getReminder() {
        return reminder;
    }

    static void setDiscussionState(boolean state) {
        inDiscussion.setValue(state);
    }

    static void setReminderState(boolean state) {
        reminder.setValue(state);
    }

    static void setExercise(ExerciseType exerciseType) {
        exercise.setValue(exerciseType);
    }

    public static void setExerciseAnswer(ExerciseAnswer exerciseAnswer) {
        GlobalVariables.exerciseAnswer = exerciseAnswer;
    }

    public static MutableLiveData<Boolean> getInDiscussion() {
        return inDiscussion;
    }

    public static MutableLiveData<ExerciseType> getExercise() {
        return exercise;
    }

    public static ExerciseAnswer getExerciseAnswer() {
        return exerciseAnswer;
    }
}
