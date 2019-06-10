package nju.classroomassistant.shared.messages.login;

import nju.classroomassistant.shared.messages.Message;

public class LoginResponseMessage extends Message {
    public enum Response {
        OK,
        NOT_ALLOWED, //没有选这个课
        ERROR // 出错了
    }

    private Response response;

    public LoginResponseMessage(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
