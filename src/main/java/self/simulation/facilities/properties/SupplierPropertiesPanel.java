package self.simulation.facilities.properties;

import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.Supplier;
import self.simulation.inventories.Inventory;
import self.simulation.products.Product;
import self.utility.SimulationConfiguration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class SupplierPropertiesPanel extends PropertiesPanel {
    private final Supplier facility;
    private Map<String, JCheckBox> checkBoxes;
    private Map<JCheckBox, Product> boxToProduct;
    private JPanel panel;

    public SupplierPropertiesPanel(Facility facility) {
        super();
        this.facility = (Supplier) facility;
        this.checkBoxes = new HashMap<>();
        this.boxToProduct = new HashMap<>();
        setup();
    }

    private void setup() {
        var products = SimulationConfiguration.INSTANCE.getProducts();

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Supplying Products:"));

        for (Product product : products) {
            JCheckBox box = new JCheckBox(product.getName());
            box.addActionListener(this);
            box.setActionCommand("update sourcing policy");
            checkBoxes.put(product.getName(), box);
            boxToProduct.put(box, product);
            panel.add(box);
        }

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("update sourcing policy")) {
            var box = (JCheckBox) e.getSource();
            var product = boxToProduct.get(box);

            if (box.isSelected()) {
                facility.getInventories().put(product, new Inventory(product, Double.MAX_VALUE));
            }
            else facility.getInventories().remove(product);
        }
    }

    @Override
    public void open() {
        var products = SimulationConfiguration.INSTANCE.getProducts();

        var toAdd = products.stream()
            .filter(p -> !checkBoxes.containsKey(p.getName()))
            .toList();

        var toRemove = checkBoxes.keySet().stream()
            .filter(pn -> !products.stream().map(Product::getName).toList().contains(pn))
            .toList();

        toRemove.forEach(
                pn -> {
                    var box = checkBoxes.get(pn);
                    if (box.isSelected()) facility.getInventories().remove(boxToProduct.get(box));
                    panel.remove(box);
                    checkBoxes.remove(pn);
                }
        );

        toAdd.forEach(p -> {
            JCheckBox box = new JCheckBox(p.getName());
            box.addActionListener(this);
            box.setActionCommand("update sourcing policy");
            checkBoxes.put(p.getName(), box);
            boxToProduct.put(box, p);
            panel.add(box);
        });
    }
}
