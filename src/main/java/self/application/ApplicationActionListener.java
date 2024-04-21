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
            case "add customer command" -> application.applicationAddController.selectFacility(FacilityType.CUSTOMER);
            case "add dc command" -> application.applicationAddController.selectFacility(FacilityType.DC);
            case "add factory command" -> application.applicationAddController.selectFacility(FacilityType.FACTORY);
            case "add route command" -> application.applicationAddController.selectRoute();
            case "add supplier command" -> application.applicationAddController.selectFacility(FacilityType.SUPPLIER);
            case "show routes command" -> application.applicationGUI.showRoutes();
            case "hide routes command" -> application.applicationGUI.hideRoutes();
        }
    }
}
