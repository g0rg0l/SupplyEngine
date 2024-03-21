package self.application;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import self.map.ApplicationGISMapMouseAdaptor;
import self.simulation.Simulation;
import self.map.GISMap;
import self.utility.SimulationConfiguration;

import static self.utility.Preferences.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Application extends JFrame {
    private final ApplicationActionListener actionListener;
    private Simulation simulation;

    public Application(String label) {
        super(label);

        this.actionListener = new ApplicationActionListener(this);

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

    private void createMenu(Container pane) {
        JPanel menu = new JPanel();
        menu.setPreferredSize(APPLICATION_MENU_DEFAULT_SIZE);
        menu.setBackground(APPLICATION_MENU_DEFAULT_COLOR);
        menu.setLayout(new BorderLayout());

        menu.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 4),
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
        GISMap map = new GISMap();
        map.setMouseAdapter(new ApplicationGISMapMouseAdaptor(map));
        map.setPreferredSize(APPLICATION_MAP_DEFAULT_SIZE);
        map.setBackground(GIS_MAP_DEFAULT_BACKGROUND_COLOR);

        map.setZoom(GIS_MAP_DEFAULT_ZOOM);
        map.setCenter(map.getTileFactory().getInfo().getMapCenterInPixelsAtZoom(map.getZoom()));
        SimulationConfiguration.INSTANCE.setMapZoomLevel(map.getZoom());
        SimulationConfiguration.INSTANCE.setMapCenterPoint(map.getCenter());

        map.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(0, 0, 0, -4),
                        BorderFactory.createLineBorder(Color.WHITE, 4)
                )
        );

        GeoPosition frankfurt = new GeoPosition(50,  7, 0, 8, 41, 0);
        GeoPosition wiesbaden = new GeoPosition(50,  5, 0, 8, 14, 0);
        GeoPosition mainz     = new GeoPosition(50,  0, 0, 8, 16, 0);
        GeoPosition darmstadt = new GeoPosition(49, 52, 0, 8, 39, 0);
        GeoPosition offenbach = new GeoPosition(50,  6, 0, 8, 46, 0);
        List<GeoPosition> track = Arrays.asList(frankfurt, wiesbaden, mainz, darmstadt, offenbach);
        Set<Waypoint> waypoints = new HashSet<>(Arrays.asList(
                new DefaultWaypoint(frankfurt),
                new DefaultWaypoint(wiesbaden),
                new DefaultWaypoint(mainz),
                new DefaultWaypoint(darmstadt),
                new DefaultWaypoint(offenbach)
        ));
        map.initMapLocations(waypoints);

        pane.add(map, BorderLayout.CENTER);
    }

    private void createHeaderPanel(Container pane) {
        JPanel header = new JPanel();
        header.setPreferredSize(APPLICATION_HEADER_DEFAULT_SIZE);
        header.setBackground(APPLICATION_HEADER_DEFAULT_COLOR);
        pane.add(header, BorderLayout.PAGE_START);
    }

    public static void main(String[] args) {
        new Application("Supply Engine");
    }
}
