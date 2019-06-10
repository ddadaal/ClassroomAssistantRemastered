package nju.classroomassistant.shared.messages.exercise.type;

public class ChoiceExerciseType extends ExerciseType {
    private int optionsCount;

    public ChoiceExerciseType(int optionsCount) {
        this.optionsCount = optionsCount;
    }

    public int getOptionsCount() {
        return optionsCount;
    }

    public void setOptionsCount(int optionsCount) {
        this.optionsCount = optionsCount;
    }
}
