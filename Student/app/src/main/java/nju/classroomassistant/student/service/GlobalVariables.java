package nju.classroomassistant.student.service;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import nju.classroomassistant.shared.messages.exercise.answer.ExerciseAnswer;
import nju.classroomassistant.shared.messages.exercise.type.ChoiceExerciseType;
import nju.classroomassistant.shared.messages.exercise.type.ExerciseType;
import nju.classroomassistant.shared.messages.exercise.type.FillBlankExerciseType;

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
        inDiscussion.setValue(false);
  //      exercise = new ChoiceExerciseType(2);
        reminder = new MutableLiveData<>();
        inExercise = new MutableLiveData<>();
  //      inExercise.setValue(true);
        inExercise.setValue(false);
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
        if (exercise == null)
            return;
        if (exercise instanceof FillBlankExerciseType) {
            Log.d(GlobalVariables.class.getName(), "setExercise: fill blank" );
        }
        if (exercise instanceof ChoiceExerciseType) {
            Log.d(GlobalVariables.class.getName(), "setExercise: choice " + ((ChoiceExerciseType) exerciseType).getOptionsCount() );
        }
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

    public static void setInExercise(boolean inExercise) {
        GlobalVariables.inExercise.setValue(inExercise);
    }

    public static MutableLiveData<Boolean> getInExercise() {
        return inExercise;
    }
}
