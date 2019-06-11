package nju.classroomassistant.student.service;

import android.util.Log;
import nju.classroomassistant.shared.messages.Message;
import nju.classroomassistant.shared.messages.discussion.DiscussionEndMessage;
import nju.classroomassistant.shared.messages.discussion.DiscussionStartMessage;
import nju.classroomassistant.shared.messages.exercise.ExerciseEndMessage;
import nju.classroomassistant.shared.messages.exercise.ExerciseStartMessage;
import nju.classroomassistant.shared.messages.exercise.type.ExerciseType;
import nju.classroomassistant.shared.messages.login.LoginMessage;
import nju.classroomassistant.shared.messages.login.LoginResponseMessage;
import nju.classroomassistant.shared.messages.login.LogoutMessage;
import nju.classroomassistant.shared.messages.raisequestion.NotificationSettingChangeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationBasicService {

    private static volatile CommunicationBasicService instance;

    private Socket socket;

    private ObjectInputStream in;

    private ObjectOutputStream out;

    private Thread listener = new Thread(new Runnable() {
        @Override
        public void run() {

            while (!terminated) {
                try {
                    Message obj = (Message) in.readObject();
                    // do something if success
                    if (obj instanceof LogoutMessage) {
                        // do nothing
                    } else if (obj instanceof ExerciseStartMessage) {
                        ExerciseType exerciseType = ((ExerciseStartMessage) obj).getExerciseType();
                        GlobalVariables.setExercise(exerciseType);
                    } else if (obj instanceof ExerciseEndMessage) {
                        GlobalVariables.setExercise(null);
                        GlobalVariables.setExerciseAnswer(null);
                    } else if (obj instanceof NotificationSettingChangeMessage) {
                        GlobalVariables.setReminderState(((NotificationSettingChangeMessage) obj).
                                isInstantNotificationEnabled());
                    } else if (obj instanceof DiscussionEndMessage) {
                        GlobalVariables.setDiscussionState(false);
                    } else if (obj instanceof DiscussionStartMessage) {
                        GlobalVariables.setDiscussionState(true);
                    } else {
                        // do nothing
                        Log.d(CommunicationBasicService.class.getName(), "" + obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // do something if failure

            }
        }
    });

    private boolean connected = false;

    private boolean terminated = false;

    public void tryConnect() {
        try {
            socket  = new Socket("10.0.2.2", 2333);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            connected = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CommunicationBasicService () {

    }

    public static CommunicationBasicService getInstance() {
        if (instance == null) {
            synchronized (CommunicationBasicService.class) {
                if (instance == null)
                    instance = new CommunicationBasicService();
            }
        }
        return instance;
    }

    public void start() {
        terminated = false;
        listener.start();
    }

    public void close() {
        terminated = true;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean writeToServer(Message message) {
        int failureCount = 0;
        while (failureCount < 5) {
            try {
                out.writeObject(message);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            failureCount++;
        }
        close();
        tryConnect();
        if (isConnected())
            return writeToServer(message);
        else
            return false;
    }

    public boolean isConnected() {
        return connected;
    }

    public LoginResponseMessage.Response login(String username) {
        if (!isConnected())
            tryConnect();

        LoginMessage loginMessage = new LoginMessage(username);
        try {
            out.writeObject(loginMessage);
            return ((LoginResponseMessage) in.readObject()).getResponse();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return LoginResponseMessage.Response.ERROR;

    }

}
