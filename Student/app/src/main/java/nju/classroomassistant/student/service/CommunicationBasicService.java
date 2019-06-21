package nju.classroomassistant.student.service;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import nju.classroomassistant.shared.Config;
import nju.classroomassistant.shared.messages.Message;
import nju.classroomassistant.shared.messages.discussion.DiscussionEndMessage;
import nju.classroomassistant.shared.messages.discussion.DiscussionStartMessage;
import nju.classroomassistant.shared.messages.exercise.ExerciseEndMessage;
import nju.classroomassistant.shared.messages.exercise.ExerciseStartMessage;
import nju.classroomassistant.shared.messages.exercise.type.ExerciseType;
import nju.classroomassistant.shared.messages.login.IsServerResponseMessage;
import nju.classroomassistant.shared.messages.login.LoginMessage;
import nju.classroomassistant.shared.messages.login.LoginResponseMessage;
import nju.classroomassistant.shared.messages.login.LogoutMessage;
import nju.classroomassistant.shared.messages.raisequestion.NotificationSettingChangeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CommunicationBasicService {

    private static volatile CommunicationBasicService instance;

    private Socket socket;

    private ObjectInputStream in;

    private ObjectOutputStream out;

    private final Handler handler = new Handler();

    private String ip = "172.19.188.130";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Message readMessage() throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }

    private Thread listener = new Thread(new Runnable() {
        @Override
        public void run() {

            while (!terminated) {
                try {
                    Log.i(CommunicationBasicService.class.getName(), "wait for message from server...");
                    Message obj = readMessage();
                    Log.i(CommunicationBasicService.class.getName(), obj.toString() + " " + "o98k");
                    // do something if success
                    handler.post(() -> {
                        if (obj instanceof LogoutMessage) {
                            // do nothing
                        } else if (obj instanceof ExerciseStartMessage) {
                            ExerciseType exerciseType = ((ExerciseStartMessage) obj).getExerciseType();
                            GlobalVariables.setExercise(exerciseType);
                            GlobalVariables.setInExercise(true);
                        } else if (obj instanceof ExerciseEndMessage) {
                            GlobalVariables.setExercise(null);
                            GlobalVariables.setExerciseAnswer(null);
                            GlobalVariables.setInExercise(false);
                        } else if (obj instanceof NotificationSettingChangeMessage) {
                            GlobalVariables.setReminderState(((NotificationSettingChangeMessage) obj).
                                    isInstantNotificationEnabled());
                        } else if (obj instanceof DiscussionEndMessage) {
                            GlobalVariables.setDiscussionState(false);
                        } else if (obj instanceof DiscussionStartMessage) {
                            GlobalVariables.setDiscussionState(true);
                        } else {
                            Log.d(CommunicationBasicService.class.getName(), "" + obj);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(100);
                }
                // do something if failure

            }
        }
    });

    private boolean connected = false;

    private boolean terminated = false;

    public void tryConnect() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, Config.PORT), 9000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
    }

    private CommunicationBasicService() {

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

    private void start() {
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
        if (!isConnected())
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

        if (!isConnected())
            return LoginResponseMessage.Response.ERROR;

        try {
            LoginMessage loginMessage = new LoginMessage(username);
            out.writeObject(loginMessage);
            LoginResponseMessage.Response response = ((LoginResponseMessage) in.readObject()).getResponse();
            start();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LoginResponseMessage.Response.ERROR;

    }

}
