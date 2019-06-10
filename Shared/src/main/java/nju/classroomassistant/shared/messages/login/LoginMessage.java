package nju.classroomassistant.shared.messages.login;

public class LoginMessage {
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
