package nju.classroomassistant.student.ui.main;

import android.arch.lifecycle.ViewModel;
import nju.classroomassistant.student.service.CommunicationBasicService;
import nju.classroomassistant.student.service.GlobalVariables;

public class MainViewModel extends ViewModel {

    private CommunicationBasicService basicService = CommunicationBasicService.getInstance();

    public void logout() {
        GlobalVariables.clear();
        if (basicService.isConnected()) {
            basicService.close();
        }
    }

}
