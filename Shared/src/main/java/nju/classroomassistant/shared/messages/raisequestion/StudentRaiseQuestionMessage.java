package nju.classroomassistant.shared.messages.raisequestion;

import nju.classroomassistant.shared.messages.Message;

/**
 * 客户端到服务器，学生提问
 */
public class StudentRaiseQuestionMessage extends Message {
    private String question;

    public StudentRaiseQuestionMessage(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
