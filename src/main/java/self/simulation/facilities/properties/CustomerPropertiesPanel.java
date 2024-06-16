package self.simulation.facilities.properties;

import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.Customer;
import self.simulation.products.Product;
import self.simulation.sourcing.SourcingType;
import self.utility.SimulationConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CustomerPropertiesPanel extends PropertiesPanel {
    private final Customer facility;
    private JTextField demandInput;
    private JTextField quantityInput;
    private JTextField expectedLeadTimeInput;
    private JComboBox<String> sourcingPolicyInput;
    private JPanel productPanel;
    private JComboBox<Product> productCombobox;


    public CustomerPropertiesPanel(Facility facility) {
        super();
        this.facility = (Customer) facility;
        setup();
    }

    private void setup() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        productPanel = new JPanel();
        productPanel.setLayout(new FlowLayout());
        JLabel productLabel = new JLabel("Needed Product: ");
        productPanel.add(productLabel);
        var p = SimulationConfiguration.INSTANCE.getProducts();
        Product[] products = new Product[p.size()];
        products = p.toArray(products);
        productCombobox = new JComboBox<>(products);
        productCombobox.addActionListener(this);
        productCombobox.setActionCommand("update demand product");
        productPanel.add(productCombobox);
        add(productPanel);

        JPanel demandQuantityPanel = new JPanel();
        demandQuantityPanel.setLayout(new FlowLayout());
        JLabel demandQuantityLabel = new JLabel("Request Amount (unit): ");
        demandQuantityPanel.add(demandQuantityLabel);
        quantityInput = new JTextField(10);
        quantityInput.setText(String.valueOf(facility.getDemandQuantityParameter()));
        demandQuantityPanel.add(quantityInput);
        JButton saveQuantityButton = new JButton("save");
        saveQuantityButton.setActionCommand("update quantity command");
        saveQuantityButton.addActionListener(this);
        demandQuantityPanel.add(saveQuantityButton);
        add(demandQuantityPanel);

        JPanel demandGenerationPanel = new JPanel();
        demandGenerationPanel.setLayout(new FlowLayout());
        JLabel demandLabel = new JLabel("Demand Period (sec.): ");
        demandGenerationPanel.add(demandLabel);
        demandInput = new JTextField(10);
        demandInput.setText(String.valueOf(facility.getDemandPeriodParameter()));
        demandGenerationPanel.add(demandInput);
        JButton saveDemandButton = new JButton("save");
        saveDemandButton.setActionCommand("update demand command");
        saveDemandButton.addActionListener(this);
        demandGenerationPanel.add(saveDemandButton);
        add(demandGenerationPanel);

        JPanel expectedLeadTimePanel = new JPanel();
        expectedLeadTimePanel.setLayout(new FlowLayout());
        JLabel expectedLeadTimeLabel = new JLabel("Expected Lead Time (sec): ");
        expectedLeadTimePanel.add(expectedLeadTimeLabel);
        expectedLeadTimeInput = new JTextField(10);
        expectedLeadTimeInput.setText(String.valueOf(facility.getExpectedLeadTimeParameter()));
        expectedLeadTimePanel.add(expectedLeadTimeInput);
        JButton saveExpectedLeadTimeButton = new JButton("save");
        saveExpectedLeadTimeButton.setActionCommand("update expected lead time command");
        saveExpectedLeadTimeButton.addActionListener(this);
        expectedLeadTimePanel.add(saveExpectedLeadTimeButton);
        add(expectedLeadTimePanel);

        JPanel sourcingPolicyPanel = new JPanel();
        sourcingPolicyPanel.setLayout(new FlowLayout());
        JLabel sourcingLabel = new JLabel("Sourcing policy:");
        sourcingPolicyPanel.add(sourcingLabel);
        var policies = new String[] {"closest", "cheapest", "fastest"};
        sourcingPolicyInput = new JComboBox<>(policies);
        sourcingPolicyInput.addActionListener(this);
        sourcingPolicyInput.setActionCommand("update sourcing policy command");
        sourcingPolicyPanel.add(sourcingPolicyInput);
        add(sourcingPolicyPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("update demand command")) {
            facility.setDemandPeriodParameter(Double.parseDouble(demandInput.getText()));
        }
        if (e.getActionCommand().equals("update sourcing policy command")) {
            facility.setSourcingType(SourcingType.getByString(
                    sourcingPolicyInput.getItemAt(sourcingPolicyInput.getSelectedIndex())
            ));
        }
        if (e.getActionCommand().equals("update demand product")) {
            facility.setDemandProductParameter(
                    productCombobox.getItemAt(productCombobox.getSelectedIndex())
            );
        }
        if (e.getActionCommand().equals("update quantity command")) {
            facility.setDemandQuantityParameter(Double.parseDouble(quantityInput.getText()));
        }
        if (e.getActionCommand().equals("update expected lead time command")) {
            facility.setExpectedLeadTimeParameter(Double.parseDouble(expectedLeadTimeInput.getText()));
        }
    }

    @Override
    public void open() {
        demandInput.setText(String.valueOf(facility.getDemandPeriodParameter()));
        quantityInput.setText(String.valueOf(facility.getDemandQuantityParameter()));
        expectedLeadTimeInput.setText(String.valueOf(facility.getExpectedLeadTimeParameter()));

        remove(productPanel);
        productPanel = new JPanel();
        productPanel.setLayout(new FlowLayout());
        JLabel productLabel = new JLabel("Demand Products: ");
        productPanel.add(productLabel);
        var p = SimulationConfiguration.INSTANCE.getProducts();
        Product[] products = new Product[p.size()];
        products = p.toArray(products);
        productCombobox = new JComboBox<>(products);
        productCombobox.addActionListener(this);
        productCombobox.setActionCommand("update demand product");
        productPanel.add(productCombobox);
        add(productPanel, 0);
        repaint();
    }
}
