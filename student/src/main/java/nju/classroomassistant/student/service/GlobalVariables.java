package nju.classroomassistant.student.service;

import android.arch.lifecycle.MutableLiveData;
import nju.classroomassistant.shared.messages.exercise.ExerciseAnswer;
import nju.classroomassistant.shared.messages.exercise.ExerciseType;
import nju.classroomassistant.shared.messages.exercise.FillBlankExerciseType;

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

    private static ExerciseType exercise;

    private static MutableLiveData<Boolean> inExercise;

    private static ExerciseAnswer exerciseAnswer = null;

    private static MutableLiveData<Boolean> reminder;

    public static CommunicationBasicService getBasicService() {
        return basicService;
    }

    public static void init() {
        inDiscussion = new MutableLiveData<>();
        inDiscussion.setValue(true);
        exercise = new FillBlankExerciseType();
        reminder = new MutableLiveData<>();
        inExercise = new MutableLiveData<>();
        inExercise.setValue(true);
        exerciseAnswer = null;
    }

    public static void clear() {
        inDiscussion = null;
        inExercise = null;
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
        exercise = exerciseType;
    }

    public static void setExerciseAnswer(ExerciseAnswer exerciseAnswer) {
        GlobalVariables.exerciseAnswer = exerciseAnswer;
    }

    public static MutableLiveData<Boolean> getInDiscussion() {
        return inDiscussion;
    }

    public static ExerciseType getExercise() {
        return exercise;
    }

    public static ExerciseAnswer getExerciseAnswer() {
        return exerciseAnswer;
    }

    public static void setInDiscussion(boolean inDiscussion) {
        GlobalVariables.inDiscussion.setValue(inDiscussion);
    }

    public static MutableLiveData<Boolean> getInExercise() {
        return inExercise;
    }
}
