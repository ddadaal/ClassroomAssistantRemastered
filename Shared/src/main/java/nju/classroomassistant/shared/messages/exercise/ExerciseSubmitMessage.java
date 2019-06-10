package nju.classroomassistant.shared.messages.exercise;

import nju.classroomassistant.shared.messages.Message;
import nju.classroomassistant.shared.messages.exercise.answer.ExerciseAnswer;
import nju.classroomassistant.shared.messages.exercise.type.ExerciseType;

/**
 * 学生提交练习答案
 */

public class ExerciseSubmitMessage extends Message {
    private ExerciseAnswer answer;

    public ExerciseSubmitMessage(ExerciseAnswer answer) {
        this.answer = answer;
    }

    public ExerciseAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(ExerciseAnswer answer) {
        this.answer = answer;
    }
}
