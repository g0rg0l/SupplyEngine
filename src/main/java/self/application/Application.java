package self.application;

import org.jxmapviewer.viewer.GeoPosition;
import self.application.ui.ApplicationButton;
import self.application.ui.CancelAddingFacilityAction;
import self.map.waypoints.MapWaypoint;
import self.map.waypoints.MapWaypointFactory;
import self.simulation.Simulation;
import self.map.AGISMap;
import self.simulation.facilities.FacilityType;
import self.utility.SimulationConfiguration;

import static self.utility.Preferences.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class Application extends JFrame {
    private final ApplicationActionListener actionListener;
    public final ApplicationAddController applicationAddController;
    private Simulation simulation;

    public Application(String label) {
        super(label);

        this.actionListener = new ApplicationActionListener(this);
        this.applicationAddController = new ApplicationAddController(this);

        createAndShowGUI();
    }

    public void createAndRunSimulation() {
        if (simulation == null || !simulation.isRunning()) {
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
        JPanel applicationPanel = new JPanel();

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
                    BorderFactory.createLineBorder(Color.BLACK, 4),
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
        ApplicationGISMap map = new ApplicationGISMap();
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
                        BorderFactory.createLineBorder(Color.BLACK, 4)
                )
        );

        GeoPosition frankfurt = new GeoPosition(50,  7, 0, 8, 41, 0);
        map.addWaypoint(MapWaypointFactory.INSTANCE.create(FacilityType.CUSTOMER, frankfurt));

        GeoPosition wiesbaden = new GeoPosition(50,  5, 0, 8, 14, 0);
        map.addWaypoint(MapWaypointFactory.INSTANCE.create(FacilityType.DC, wiesbaden));

//        GeoPosition frankfurt = new GeoPosition(50,  7, 0, 8, 41, 0);
//        GeoPosition wiesbaden = new GeoPosition(50,  5, 0, 8, 14, 0);
//        GeoPosition mainz     = new GeoPosition(50,  0, 0, 8, 16, 0);
//        GeoPosition darmstadt = new GeoPosition(49, 52, 0, 8, 39, 0);
//        GeoPosition offenbach = new GeoPosition(50,  6, 0, 8, 46, 0);
//
//        Set<MapWaypoint> waypoints = new HashSet<>(Arrays.asList(
//                MapWaypointFactory.INSTANCE.create(FacilityType.CUSTOMER, frankfurt),
//                MapWaypointFactory.INSTANCE.create(FacilityType.DC, wiesbaden),
//                MapWaypointFactory.INSTANCE.create(FacilityType.CUSTOMER, mainz),
//                MapWaypointFactory.INSTANCE.create(FacilityType.CUSTOMER, darmstadt),
//                MapWaypointFactory.INSTANCE.create(FacilityType.CUSTOMER, offenbach)
//        ));
//        map.initMapLocations(waypoints);

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