package self.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationActionListener implements ActionListener {
    private final Application application;

    public ApplicationActionListener(Application application) {
        this.application = application;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "start simulation command" -> application.createAndRunSimulation();
        }
    }
}
