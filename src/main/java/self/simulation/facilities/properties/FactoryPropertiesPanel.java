package self.simulation.facilities.properties;

import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.Factory;

import java.awt.*;
import java.awt.event.ActionEvent;

public class FactoryPropertiesPanel extends PropertiesPanel {
    private final Factory facility;

    public FactoryPropertiesPanel(Facility facility) {
        super();
        this.facility = (Factory) facility;
        setup();
    }

    private void setup() {
        setBackground(Color.YELLOW);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void open() {

    }
}
