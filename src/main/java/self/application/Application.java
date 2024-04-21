package self.application;

import org.jxmapviewer.viewer.GeoPosition;
import self.application.ui.ApplicationGUI;
import self.application.ui.CancelAddingFacilityAction;
import self.engine.TimeUnit;
import self.map.routing.MapRouteFactory;
import self.simulation.facilities.FacilityFactory;
import self.simulation.Simulation;
import self.simulation.facilities.FacilityType;
import self.utility.SimulationConfiguration;

import static self.utility.Preferences.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;


public class Application extends JFrame {
    public final ApplicationActionListener actionListener;
    public final ApplicationAddController applicationAddController;
    public final ApplicationGUI applicationGUI;
    private Simulation simulation;
    private ApplicationGISMap map;
    public TimeUnit timeUnit;

    public Application(String label) {
        super(label);

        this.actionListener = new ApplicationActionListener(this);
        this.applicationAddController = new ApplicationAddController(this);
        this.applicationGUI = new ApplicationGUI(this);
        this.timeUnit = TimeUnit.SECOND;

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

        createMap(pane);
        applicationGUI.createHeaderPanel(pane);
        applicationGUI.createMenu(pane);
    }

    private void setKeyBindings(Container pane) {
        ActionMap actionMap = ((JPanel) pane).getActionMap();
        InputMap inputMap = ((JPanel) pane).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), KeyEvent.VK_ESCAPE);
        actionMap.put(KeyEvent.VK_ESCAPE, new CancelAddingFacilityAction(applicationAddController));
    }


    private void createMap(Container pane) {
        map = new ApplicationGISMap();
        map.setMouseAdapter(new ApplicationGISMapMouseAdaptor(map, this));
        map.setPreferredSize(APPLICATION_MAP_DEFAULT_SIZE);
        map.setBackground(GIS_MAP_DEFAULT_BACKGROUND_COLOR);

        map.setZoom(GIS_MAP_DEFAULT_ZOOM);
        map.setCenter(map.getTileFactory().getInfo().getMapCenterInPixelsAtZoom(map.getZoom()));
        map.isInitialized = true;

        SimulationConfiguration.INSTANCE.setMapZoomLevel(map.getZoom());
        SimulationConfiguration.INSTANCE.setMapCenterPoint(map.getCenter());

        map.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(),
                        BorderFactory.createLineBorder(APPLICATION_DEFAULT_BORDER_COLOR, APPLICATION_DEFAULT_BORDER_SIZE, true)
                )
        );

        List<GeoPosition> locations = new ArrayList<>(List.of(
                new GeoPosition(59.91512708056822, 10.753235784960516),
                new GeoPosition(59.73967700265938, 10.207592844407198),
                new GeoPosition(62.11752476624052, 10.61408081697316),
                new GeoPosition(62.5124046981561, 7.657804652857066),
                new GeoPosition(61.5286327683711, 5.810132050284508),
                new GeoPosition(59.01976360435244, 5.763940235220194)
        ));

        for (GeoPosition loc : locations)
            map.addFacility(FacilityFactory.create(FacilityType.CUSTOMER, loc, map));

        map.addFacility(FacilityFactory.create(FacilityType.DC, new GeoPosition(60.55131768755436, 8.1998383709765), map));
        map.addFacility(FacilityFactory.create(FacilityType.SUPPLIER, new GeoPosition(67.04538454612698, 15.37853199184066), map));
        map.addFacility(FacilityFactory.create(FacilityType.FACTORY, new GeoPosition(65.80890971070806, 13.200569026235772), map));

        for (GeoPosition customerLocation : locations) {
            map.addRoute(MapRouteFactory.createRoute(new GeoPosition(60.55131768755436, 8.1998383709765), customerLocation, map));
        }

        applicationAddController.setMap(map);
        pane.add(map, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new Application("Supply Engine");
    }
}
