package self.simulation.facilities.properties;

import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.Factory;
import self.simulation.products.Product;
import self.utility.SimulationConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FactoryPropertiesPanel extends PropertiesPanel {
    private final Factory facility;
    private JPanel panel;
    private JPanel row;
    private JButton saveButton;

    public FactoryPropertiesPanel(Facility facility) {
        super();
        this.facility = (Factory) facility;
        setup();
    }

    private void setup() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        row = new JPanel();
        row.setLayout(new FlowLayout());

        row.add(new JLabel("Component"));
        row.add(new JLabel("Result"));
        row.add(new JLabel("Coefficient"));
        panel.add(row);

        row = new JPanel();
        row.setLayout(new FlowLayout());

        var addButton = new JButton("add");
        addButton.addActionListener(this);
        addButton.setActionCommand("add");
        var removeButton = new JButton("remove");
        removeButton.addActionListener(this);
        removeButton.setActionCommand("remove");

        row.add(addButton);
        row.add(removeButton);

        panel.add(row);
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            row = new JPanel();
            row.setLayout(new FlowLayout());
            saveButton = new JButton("save");
            saveButton.addActionListener(this);
            saveButton.setActionCommand("save");

            var products = SimulationConfiguration.INSTANCE.getProducts().toArray(new Product[]{ });
            var choiceFrom = new JComboBox<>(products);
            var choiceTo = new JComboBox<>(products);
            var coefficientLabel = new JTextField(5);

            row.add(choiceFrom);
            row.add(choiceTo);
            row.add(coefficientLabel);
            row.add(saveButton);

            panel.add(row);
            panel.revalidate();
            panel.repaint();
        }
        else if (e.getActionCommand().equals("remove")) {
            if (panel.getComponents().length > 2) {
                panel.remove(row);
                panel.revalidate();
                panel.repaint();

                Product fromProduct = getProduct(row.getComponent(0));
                Product toProduct = getProduct(row.getComponent(1));

                if (fromProduct != null && toProduct != null) {
                    var convertiblesFromThisProduct = facility.getConvertibles().get(fromProduct);

                    if (convertiblesFromThisProduct != null && convertiblesFromThisProduct.contains(toProduct)) {
                        facility.getConvertibles().get(fromProduct).remove(toProduct);
                    }
                    if (facility.getCoefficients().containsKey(fromProduct)) {
                        facility.getCoefficients().get(fromProduct).remove(toProduct);
                    }
                }

                row = (JPanel) panel.getComponent(panel.getComponents().length - 1);
            }
        }
        else if (e.getActionCommand().equals("save")) {
            row.remove(saveButton);
            panel.revalidate();
            panel.repaint();

            Product fromProduct = getProduct(row.getComponent(0));
            Product toProduct = getProduct(row.getComponent(1));
            double coefficient = Double.parseDouble(((JTextField) row.getComponent(2)).getText());

            if (fromProduct != null && toProduct != null) {
                facility.getConvertibles().computeIfAbsent(fromProduct, o -> new HashSet<>()).add(toProduct);
                facility.getCoefficients().computeIfAbsent(fromProduct, o -> new HashMap<>()).put(toProduct, coefficient);
            }
        }
    }

    private Product getProduct(Component component) {
        return component instanceof JComboBox<?> from
                ? (Product) from.getItemAt(from.getSelectedIndex())
                : null;
    }

    @Override
    public void open() {

    }
}
