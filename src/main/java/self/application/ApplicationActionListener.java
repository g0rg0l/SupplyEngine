package self.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "start simulation command" -> new Application("label");
        }
    }
}
