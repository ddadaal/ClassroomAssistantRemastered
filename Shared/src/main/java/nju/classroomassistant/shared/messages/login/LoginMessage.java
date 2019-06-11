package nju.classroomassistant.shared.messages.login;

import nju.classroomassistant.shared.messages.Message;

public class LoginMessage extends Message {
    private String studentId;

    public LoginMessage(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
