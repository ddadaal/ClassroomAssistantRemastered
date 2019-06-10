package nju.classroomassistant.shared.messages.exercise;

import nju.classroomassistant.shared.messages.Message;
import nju.classroomassistant.shared.messages.exercise.type.ExerciseType;

/**
 * 服务器到客户端，练习开始
 */
public class ExerciseStartMessage extends Message {

    private ExerciseType exerciseType;

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public ExerciseStartMessage(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }
}
