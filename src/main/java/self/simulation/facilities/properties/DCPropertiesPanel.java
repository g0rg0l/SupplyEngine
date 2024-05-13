package self.simulation.facilities.properties;

import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.DC;
import self.simulation.inventories.Inventory;
import self.simulation.products.Product;
import self.utility.SimulationConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;


public class DCPropertiesPanel extends PropertiesPanel {
    private final DC facility;
    private Map<Product, JPanel> rows;
    private Map<JCheckBox, JTextField> help;
    private JPanel panel;

    public DCPropertiesPanel(Facility facility) {
        super();
        this.facility = (DC) facility;
        setup();
    }

    private void setup() {
        var products = SimulationConfiguration.INSTANCE.getProducts();
        rows = new HashMap<>();
        help = new HashMap<>();

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        var tempPanel = new JPanel();
        tempPanel.setLayout(new FlowLayout());
        tempPanel.add(new JLabel("Product              "));
        tempPanel.add(new JLabel("Initial Stock"));
        panel.add(tempPanel);

        for (Product product : products) {
            tempPanel = new JPanel();
            tempPanel.setLayout(new FlowLayout());

            var box = new JCheckBox(product.toString());
            box.addActionListener(this);
            box.setActionCommand("update inventory");
            var textField = new JTextField(10);
            textField.setText("0.0");

            tempPanel.add(box);
            tempPanel.add(textField);
            help.put(box, textField);

            panel.add(tempPanel);
            rows.put(product, tempPanel);
        }

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("update inventory")) {
            var checkBox = ((JCheckBox) e.getSource());

            Product product = null;
            for (Product p : SimulationConfiguration.INSTANCE.getProducts()) {
                if (p.getName().equals(checkBox.getText())) {
                    product = p;
                    break;
                }
            }

            if (checkBox.isSelected()) {
                double initialStock = Double.parseDouble(help.get(checkBox).getText());
                facility.getInventories().put(product, new Inventory(product, initialStock));
            }
            else facility.getInventories().remove(product);
        }
    }

    @Override
    public void open() {
        var products = SimulationConfiguration.INSTANCE.getProducts();
        var productsToAdd = products
                .stream()
                .filter(p -> !rows.containsKey(p))
                .toList();
        var productsToRemove = rows.keySet().stream().toList()
                .stream()
                .filter(p -> !products.contains(p))
                .toList();

        for (Product p : productsToAdd) {
            var tempPanel = new JPanel();
            tempPanel.setLayout(new FlowLayout());
            JCheckBox box = new JCheckBox(p.toString());
            box.addActionListener(this);
            box.setActionCommand("update inventory");
            JTextField textField = new JTextField(10);
            textField.setText("0.0");
            tempPanel.add(box);
            tempPanel.add(textField);

            help.put(box, textField);
            panel.add(tempPanel);
            rows.put(p, tempPanel);
        }

        for (Product p : productsToRemove) {
            panel.remove(rows.get(p));
            rows.remove(p);
        }
    }
}
