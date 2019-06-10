package nju.classroomassistant.student.service;

import nju.classroomassistant.shared.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class CommunicationBasicService {

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
                    continue;
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
        return false;
    }

    public boolean isConnected() {
        return connected;
    }
}
