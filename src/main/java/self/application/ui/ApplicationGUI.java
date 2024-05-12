package self.application.ui;

import lombok.Getter;
import self.application.Application;
import self.simulation.facilities.properties.VehiclesPropertiesPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static self.utility.Preferences.*;
import static self.utility.Preferences.APPLICATION_MENU_DEFAULT_COLOR;

public class ApplicationGUI {
    private final Application application;
    private ApplicationButton showRoutesButton;
    private ApplicationButton hideRoutesButton;

    @Getter
    private JPanel propertiesPanel;

    private VehiclesPropertiesPanel vehiclePropertiesPanel;

    public ApplicationGUI(Application application) {
        this.application = application;
    }

    public void createHeaderPanel(Container pane) {
        JPanel header = new JPanel();
        header.setPreferredSize(APPLICATION_HEADER_DEFAULT_SIZE);
        header.setBackground(APPLICATION_HEADER_DEFAULT_COLOR);
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 10),
                BorderFactory.createEmptyBorder()
        ));

        JPanel headerLeft = new JPanel();
        headerLeft.setLayout(new BorderLayout());

        JPanel addButtonsPanel = new JPanel();
        addButtonsPanel.setBackground(header.getBackground());
        addButtonsPanel.setLayout(new GridLayout(1, 6, 10, 0));

        try {
            addButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/add_route_btn.png"))),
                    application.actionListener, "add route command"));

            addButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/add_customer_btn.png"))),
                    application.actionListener, "add customer command"));

            addButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/add_dc_btn.png"))),
                    application.actionListener, "add dc command"));

            addButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/add_factory_btn.png"))),
                    application.actionListener, "add factory command"));

            addButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/add_supplier_btn.png"))),
                    application.actionListener, "add supplier command"));
        }
        catch (Exception exception) {
            System.out.println("Error while loading resources: can not load button icons.");
            System.exit(-1);
        }
        headerLeft.add(addButtonsPanel, BorderLayout.LINE_START);

        header.add(headerLeft, BorderLayout.LINE_START);

        JPanel headerCenter = new JPanel();
        headerCenter.setLayout(new BorderLayout());
        headerCenter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 10),
                BorderFactory.createEmptyBorder()
        ));

        JPanel routeButtons = new JPanel();
        routeButtons.setLayout(new GridLayout(1,  2, 10, 0));
        try {
            showRoutesButton = new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/button_show_routes.png"))),
                    application.actionListener, "show routes command");
            showRoutesButton.setEnabled(false);
            routeButtons.add(showRoutesButton);

            hideRoutesButton = new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/button_hide_routes.png"))),
                    application.actionListener, "hide routes command");
            hideRoutesButton.setEnabled(true);
            routeButtons.add(hideRoutesButton);
        }
        catch (Exception exception) {
            System.out.println("Error while loading resources: can not load button icons.");
            System.exit(-1);
        }
        headerCenter.add(routeButtons, BorderLayout.LINE_END);

        header.add(headerCenter, BorderLayout.CENTER);

        JPanel headerRight = new JPanel();
        headerRight.setPreferredSize(new Dimension(390, 50));
        headerRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
        try {
            headerRight.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/vehicle_settings.png"))),
                    application.actionListener, "show vehicle settings command"));
        } catch (Exception exception) {
            System.out.println("Error while loading resources: can not load button icons.");
            System.exit(-1);
        }
        header.add(headerRight, BorderLayout.LINE_END);

        vehiclePropertiesPanel = new VehiclesPropertiesPanel(application.map.getRouteManager());

        pane.add(header, BorderLayout.PAGE_START);
    }

    public void createMenu(Container pane) {
        JPanel menu = new JPanel();
        menu.setPreferredSize(APPLICATION_MENU_DEFAULT_SIZE);
        menu.setBackground(Color.RED);
        menu.setLayout(new BorderLayout());

        JPanel menuFooter = new JPanel();
        menuFooter.setBackground(Color.RED);
        menuFooter.setLayout(new BorderLayout());

        JPanel startSimulationPanel = new JPanel();
        try {
            startSimulationPanel.add(
                    new ApplicationButton(
                            ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/launch_simulation.png"))),
                            application.actionListener, "start simulation command"));
        }
        catch (Exception exception) {
            System.out.println("Error while loading resources: can not load button icons.");
            System.exit(-1);
        }
        menuFooter.add(startSimulationPanel, BorderLayout.CENTER);
        menu.add(menuFooter, BorderLayout.PAGE_END);


        propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new BorderLayout());
        propertiesPanel.setBackground(APPLICATION_MENU_DEFAULT_COLOR);
        menu.add(propertiesPanel, BorderLayout.CENTER);


        pane.add(menu, BorderLayout.LINE_END);
    }

    public void showRoutes() {
        showRoutesButton.setEnabled(false);
        hideRoutesButton.setEnabled(true);
    }

    public void hideRoutes() {
        showRoutesButton.setEnabled(true);
        hideRoutesButton.setEnabled(false);
    }

    public void showVehicleSettings() {
        propertiesPanel.removeAll();

        vehiclePropertiesPanel.open();
        propertiesPanel.add(vehiclePropertiesPanel);
        propertiesPanel.revalidate();
        propertiesPanel.repaint();
    }

}
