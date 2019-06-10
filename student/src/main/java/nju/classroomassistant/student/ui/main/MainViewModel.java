package nju.classroomassistant.student.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Boolean> inDiscussion = new MutableLiveData<>();

    private MutableLiveData<Boolean> inPractise = new MutableLiveData<>();

    public MainViewModel() {
        inDiscussion.setValue(true);
        inPractise.setValue(true);
    }

    MutableLiveData<Boolean> getInDiscussion() {
        return inDiscussion;
    }

    MutableLiveData<Boolean> getInPractise() {
        return inPractise;
    }

    public void setInDiscussionState(boolean state) {
        this.inDiscussion.setValue(state);
    }

    public void setInPractiseState(boolean state) {
        this.inPractise.setValue(state);
    }
}
