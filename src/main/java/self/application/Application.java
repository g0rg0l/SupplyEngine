package self.application;

import self.application.ui.ApplicationGUI;
import self.application.ui.CancelAddingFacilityAction;
import self.engine.TimeUnit;
import self.simulation.Simulation;
import self.utility.SimulationConfiguration;

import static self.utility.Preferences.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


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
            SimulationConfiguration.INSTANCE.setFacilityManager(map.getFacilityManager());
            SimulationConfiguration.INSTANCE.setRouteManager(map.getRouteManager());

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

        applicationAddController.setMap(map);
        pane.add(map, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new Application("Supply Engine");
    }
}
