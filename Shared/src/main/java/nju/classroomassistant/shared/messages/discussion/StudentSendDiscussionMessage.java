package nju.classroomassistant.shared.messages.discussion;

import nju.classroomassistant.shared.messages.Message;

/**
 * 学生端发给服务器的，表示发送了一个讨论
 */
public class StudentSendDiscussionMessage extends Message {
    private String content;

    public StudentSendDiscussionMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
