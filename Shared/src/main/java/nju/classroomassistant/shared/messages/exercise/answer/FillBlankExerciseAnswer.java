package nju.classroomassistant.shared.messages.exercise.answer;

public class FillBlankExerciseAnswer extends ExerciseAnswer {
    private String answer;

    public FillBlankExerciseAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
