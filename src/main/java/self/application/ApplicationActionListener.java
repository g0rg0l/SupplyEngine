package self.application;

import self.simulation.facilities.FacilityType;

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
            case "add customer command" -> application.applicationAddController.select(FacilityType.CUSTOMER);
            case "add dc command" -> application.applicationAddController.select(FacilityType.DC);
            case "add factory command" -> application.applicationAddController.select(FacilityType.FACTORY);
            case "add supplier command" -> application.applicationAddController.select(FacilityType.SUPPLIER);
        }
    }
}
