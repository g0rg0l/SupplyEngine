package self.simulation.facilities.properties;

import self.simulation.products.Product;
import self.utility.SimulationConfiguration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductsPropertiesPanel extends PropertiesPanel {
    private final Object[] HEADER = { "Name", "Unit", "CPU", "Cost Unit" };

    public ProductsPropertiesPanel() {
        super();
        setup();
    }

    private void setup() {

    }

    @Override
    public void open() {
        var products = SimulationConfiguration.INSTANCE.getProducts();
        Object[][] data = new String[products.size()][];
        for (int i = 0; i < products.size(); i++) {
            data[i] = new String[] {
                    products.get(i).getName(),
                    products.get(i).getUnit(),
                    products.get(i).getCost(),
                    products.get(i).getCostUnit()
            };
        }


        removeAll();

        setLayout(new FlowLayout());
        var table = new CustomTable(data, HEADER, this);
        table.setCellEditor(new DefaultCellEditor(new JTextField()));
        table.setFillsViewportHeight(true);
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jpanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        jpanel.add(table, BorderLayout.CENTER);

        add(jpanel);

        JPanel buttonsPanel = new JPanel();
        JButton addRowButton = new JButton("add row");
        addRowButton.addActionListener(this);
        addRowButton.setActionCommand("add row command");
        buttonsPanel.add(addRowButton);
        JButton removeRowButton = new JButton("rem row");
        removeRowButton.addActionListener(this);
        removeRowButton.setActionCommand("remove row command");
        buttonsPanel.add(removeRowButton);
        add(buttonsPanel);

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add row command")) {
            var products = SimulationConfiguration.INSTANCE.getProducts();
            products.add(new Product(
                    "Product #" + (products.size() + 1), "", "", ""
            ));
            open();
        }
        else if (e.getActionCommand().equals("remove row command")) {
            var products = SimulationConfiguration.INSTANCE.getProducts();
            if (!products.isEmpty()) products.remove(products.size() - 1);
            open();
        }
    }
}
