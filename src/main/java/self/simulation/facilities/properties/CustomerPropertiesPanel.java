package self.simulation.facilities.properties;

import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CustomerPropertiesPanel extends PropertiesPanel {
    private final Customer facility;

    public CustomerPropertiesPanel(Facility facility) {
        super();
        this.facility = (Customer) facility;
        setup();
    }

    private void setup() {
        setBackground(Color.GREEN);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
