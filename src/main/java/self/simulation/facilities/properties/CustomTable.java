package self.simulation.facilities.properties;

import self.utility.SimulationConfiguration;

import javax.swing.*;

public class CustomTable extends JTable {
    private final PropertiesPanel owner;


    public CustomTable(Object[][] rowData, Object[] columnNames, PropertiesPanel owner) {
        super(rowData, columnNames);
        this.owner = owner;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        var product = SimulationConfiguration.INSTANCE.getProducts().get(row);

        switch (col) {
            case 0 -> product.setName((String) value);
            case 1 -> product.setUnit((String) value);
            case 2 -> product.setCost((String) value);
            case 3 -> product.setCostUnit((String) value);
        }

        owner.open();
    }
}
