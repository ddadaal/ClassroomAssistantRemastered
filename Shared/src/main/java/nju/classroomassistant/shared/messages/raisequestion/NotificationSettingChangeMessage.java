package nju.classroomassistant.shared.messages.raisequestion;

import nju.classroomassistant.shared.messages.Message;

/**
 * 服务器到客户端，实时提醒状态改变
 */
public class NotificationSettingChangeMessage extends Message {
    private boolean instantNotificationEnabled;

    public NotificationSettingChangeMessage(boolean instantNotificationEnabled) {
        this.instantNotificationEnabled = instantNotificationEnabled;
    }

    public boolean isInstantNotificationEnabled() {
        return instantNotificationEnabled;
    }

    public void setInstantNotificationEnabled(boolean instantNotificationEnabled) {
        this.instantNotificationEnabled = instantNotificationEnabled;
    }
}
