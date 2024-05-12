package self.simulation.facilities.properties;

import self.map.routing.RouteManager;
import self.utility.SimulationConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import self.map.routing.MapRoute;

public class VehiclesPropertiesPanel extends PropertiesPanel {
    private JTextField vehicleSpeedInput;
    private final RouteManager applicationRouteManager;

    public VehiclesPropertiesPanel(RouteManager applicationRouteManager) {
        super();
        this.applicationRouteManager = applicationRouteManager;
        setup();
    }

    private void setup() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel vehicleSpeedPanel = new JPanel();
        vehicleSpeedPanel.setLayout(new FlowLayout());
        JLabel vehicleSpeedLabel = new JLabel("Vehicle speed (km/h): ");
        vehicleSpeedPanel.add(vehicleSpeedLabel);
        vehicleSpeedInput = new JTextField(10);
        vehicleSpeedInput.setText(String.valueOf(SimulationConfiguration.INSTANCE.getVehicleSpeed()));
        vehicleSpeedPanel.add(vehicleSpeedInput);
        JButton saveVehicleSpeedButton = new JButton("save");
        saveVehicleSpeedButton.addActionListener(this);
        saveVehicleSpeedButton.setActionCommand("update vehicle speed command");
        vehicleSpeedPanel.add(saveVehicleSpeedButton);
        add(vehicleSpeedPanel);
    }

    @Override
    public void open() {
        vehicleSpeedInput.setText(String.valueOf(SimulationConfiguration.INSTANCE.getVehicleSpeed()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("update vehicle speed command")) {
            SimulationConfiguration.INSTANCE.setVehicleSpeed(Double.parseDouble(vehicleSpeedInput.getText()));

            double vehicleSpeedInMetersInSecond = Double.parseDouble(vehicleSpeedInput.getText()) * 3.6;

            applicationRouteManager.getObjects().forEach(o -> o.setOriginalTime(
                    (long) (o.getOriginalDistance() / vehicleSpeedInMetersInSecond)
            ));
        }
    }
}
