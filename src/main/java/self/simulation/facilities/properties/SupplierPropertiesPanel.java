package self.simulation.facilities.properties;

import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.Supplier;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SupplierPropertiesPanel extends PropertiesPanel {
    private final Supplier facility;

    public SupplierPropertiesPanel(Facility facility) {
        super();
        this.facility = (Supplier) facility;
        setup();
    }

    private void setup() {
        setBackground(Color.MAGENTA);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void open() {

    }
}
