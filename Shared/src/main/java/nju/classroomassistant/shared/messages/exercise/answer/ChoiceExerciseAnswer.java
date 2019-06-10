package nju.classroomassistant.shared.messages.exercise.answer;

import java.util.List;

public class ChoiceExerciseAnswer extends ExerciseAnswer {
    private List<String> answers;

    public ChoiceExerciseAnswer(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
