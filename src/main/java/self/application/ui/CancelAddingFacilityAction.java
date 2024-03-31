package self.application.ui;

import self.application.ApplicationAddController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CancelAddingFacilityAction extends AbstractAction {
    private final ApplicationAddController controller;

    public CancelAddingFacilityAction(ApplicationAddController controller) {
        putValue(ACTION_COMMAND_KEY, String.valueOf(KeyEvent.VK_ESCAPE));
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvt) {
        controller.cancel();
    }
}
