package self.simulation.facilities.properties;

import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.DC;

import java.awt.*;
import java.awt.event.ActionEvent;

public class DCPropertiesPanel extends PropertiesPanel {
    private final DC facility;

    public DCPropertiesPanel(Facility facility) {
        super();
        this.facility = (DC) facility;
        setup();
    }

    private void setup() {
        setBackground(Color.RED);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
