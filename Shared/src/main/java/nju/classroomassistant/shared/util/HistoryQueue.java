package nju.classroomassistant.shared.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HistoryQueue implements Serializable {

    private ArrayList<String> history = new ArrayList<>();

    public String serialize() {
        return String.join(",", history);
    }

    public HistoryQueue() {
    }

    public HistoryQueue(String serialized) {
        history.addAll(Arrays.asList(serialized.split(",")));
    }

    public void add(String entry) {

        int index = history.indexOf(entry);
        if (index == -1) {
            history.add(entry);
            if (history.size() > 5) {
                pop();
            }
        } else {
            history.remove(index);
            history.add(entry);
        }



    }

    public void pop() {
        history.remove(0);
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    public int size() {
        return history.size();
    }

    public String latest() {
        if (size() == 0) {
            throw new RuntimeException("History in empty");
        }
        return history.get(size()-1);
    }

    public ArrayList<String> getHistory() {
        return history;
    }

}
