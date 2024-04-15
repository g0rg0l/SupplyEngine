package self.application;

import org.jxmapviewer.viewer.GeoPosition;
import self.application.ui.ApplicationButton;
import self.application.ui.CancelAddingFacilityAction;
import self.map.routing.MapRouteFactory;
import self.simulation.facilities.FacilityFactory;
import self.simulation.Simulation;
import self.simulation.facilities.FacilityType;
import self.utility.SimulationConfiguration;

import static self.utility.Preferences.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;


public class Application extends JFrame {
    private final ApplicationActionListener actionListener;
    public final ApplicationAddController applicationAddController;
    private Simulation simulation;
    private ApplicationGISMap map;

    public Application(String label) {
        super(label);

        this.actionListener = new ApplicationActionListener(this);
        this.applicationAddController = new ApplicationAddController(this);

        createAndShowGUI();
    }

    public void createAndRunSimulation() {
        if (simulation == null || !simulation.isRunning()) {
            SimulationConfiguration.INSTANCE.setFacilities(map.getFacilityManager().getObjects());
            SimulationConfiguration.INSTANCE.setRoutes(map.getRouteManager().getObjects());

            simulation = new Simulation();
            simulation.start();
        }
    }

    private void createAndShowGUI() {
        createPanels();
        setKeyBindings(getContentPane());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(APPLICATION_MINIMUM_SIZE);
        pack();
        setVisible(true);
    }

    private void createPanels() {
        Container pane = getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        createHeaderPanel(pane);
        createMap(pane);
        createMenu(pane);
    }

    private void setKeyBindings(Container pane) {
        ActionMap actionMap = ((JPanel) pane).getActionMap();
        InputMap inputMap = ((JPanel) pane).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), KeyEvent.VK_ESCAPE);
        actionMap.put(KeyEvent.VK_ESCAPE, new CancelAddingFacilityAction(applicationAddController));
    }

    private void createMenu(Container pane) {
        JPanel menu = new JPanel();
        menu.setPreferredSize(APPLICATION_MENU_DEFAULT_SIZE);
        menu.setBackground(APPLICATION_MENU_DEFAULT_COLOR);
        menu.setLayout(new BorderLayout());

        menu.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(APPLICATION_DEFAULT_BORDER_COLOR, 4),
                    BorderFactory.createEmptyBorder(15, 50, 15, 50)
                )
        );

        JButton startSimulationButton = new JButton("Start");
        startSimulationButton.setPreferredSize(new Dimension(250, 50));
        startSimulationButton.setActionCommand("start simulation command");
        startSimulationButton.addActionListener(actionListener);
        menu.add(startSimulationButton, BorderLayout.SOUTH);

        pane.add(menu, BorderLayout.LINE_END);
    }

    private void createMap(Container pane) {
        map = new ApplicationGISMap();
        map.setMouseAdapter(new ApplicationGISMapMouseAdaptor(map, this));
        map.setPreferredSize(APPLICATION_MAP_DEFAULT_SIZE);
        map.setBackground(GIS_MAP_DEFAULT_BACKGROUND_COLOR);

        map.setZoom(GIS_MAP_DEFAULT_ZOOM);
        map.setCenter(map.getTileFactory().getInfo().getMapCenterInPixelsAtZoom(map.getZoom()));
        SimulationConfiguration.INSTANCE.setMapZoomLevel(map.getZoom());
        SimulationConfiguration.INSTANCE.setMapCenterPoint(map.getCenter());

        map.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(0, 0, 0, -4),
                        BorderFactory.createLineBorder(APPLICATION_DEFAULT_BORDER_COLOR, 4)
                )
        );

        List<GeoPosition> locations = new ArrayList<>(List.of(
                new GeoPosition(54.868672750248, 69.13719973373541),
                new GeoPosition(43.24467981803127, 76.86720021424557),
                new GeoPosition(51.18186939136685, 71.42249140671186),
                new GeoPosition(49.80068897769735, 73.10705353279045),
                new GeoPosition(50.43362440820337, 80.22807986083738),
                new GeoPosition(52.960822199271526, 63.10649848449209)
        ));

        for (GeoPosition loc : locations)
            map.addFacility(FacilityFactory.create(FacilityType.CUSTOMER, loc, map));

        for (int i = 0; i < locations.size() - 1; i++)
            for (int j = i + 1; j < locations.size(); j++) {
                map.addRoute(MapRouteFactory.createRoute(locations.get(i), locations.get(j), map));
            }

        applicationAddController.setMap(map);
        pane.add(map, BorderLayout.CENTER);
    }

    private void createHeaderPanel(Container pane) {
        JPanel header = new JPanel();
        header.setPreferredSize(APPLICATION_HEADER_DEFAULT_SIZE);
        header.setBackground(APPLICATION_HEADER_DEFAULT_COLOR);
        header.setLayout(new BorderLayout());

        JPanel addButtonsPanel = new JPanel();
        addButtonsPanel.setPreferredSize(APPLICATION_HEADER_ADD_BUTTONS_DEFAULT_SIZE);
        addButtonsPanel.setBackground(header.getBackground());
        addButtonsPanel.setLayout(new GridLayout(1, 4, 0, 9));

        try {
            addButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/add_customer_btn.png"))),
                    actionListener, "add customer command"));

            addButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/add_dc_btn.png"))),
                    actionListener, "add dc command"));

            addButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/add_supplier_btn.png"))),
                    actionListener, "add supplier command"));

            addButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/add_factory_btn.png"))),
                    actionListener, "add factory command"));
        }
        catch (Exception exception) {
            System.out.println("Error while loading resources: can not load button icons.");
            System.exit(-1);
        }

        header.add(addButtonsPanel, BorderLayout.LINE_START);

        pane.add(header, BorderLayout.PAGE_START);
    }

    public static void main(String[] args) {
        new Application("Supply Engine");
    }
}
