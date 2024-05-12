package self.simulation.facilities.properties;

import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.Customer;
import self.simulation.sourcing.SourcingType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CustomerPropertiesPanel extends PropertiesPanel {
    private final Customer facility;
    private JTextField demandInput;
    private JComboBox<String> sourcingPolicyInput;

    public CustomerPropertiesPanel(Facility facility) {
        super();
        this.facility = (Customer) facility;
        setup();
    }

    private void setup() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel demandGenerationPanel = new JPanel();
        demandGenerationPanel.setLayout(new FlowLayout());
        JLabel demandLabel = new JLabel("demand:");
        demandGenerationPanel.add(demandLabel);
        demandInput = new JTextField(10);
        demandInput.setText(String.valueOf(facility.getDemandParameter()));
        demandGenerationPanel.add(demandInput);
        JButton saveDemandButton = new JButton("save");
        saveDemandButton.addActionListener(this);
        saveDemandButton.setActionCommand("update demand command");
        demandGenerationPanel.add(saveDemandButton);
        add(demandGenerationPanel);

        JPanel sourcingPolicyPanel = new JPanel();
        sourcingPolicyPanel.setLayout(new FlowLayout());
        JLabel sourcingLabel = new JLabel("sourcing policy:");
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
            facility.setDemandParameter(Double.parseDouble(demandInput.getText()));
        }
        if (e.getActionCommand().equals("update sourcing policy command")) {
            facility.setSourcingType(SourcingType.getByString(
                    sourcingPolicyInput.getItemAt(sourcingPolicyInput.getSelectedIndex())
            ));
        }
    }

    @Override
    public void open() {
        demandInput.setText(String.valueOf(facility.getDemandParameter()));
    }
}
