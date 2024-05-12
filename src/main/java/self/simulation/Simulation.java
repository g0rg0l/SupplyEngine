package self.simulation;

import self.engine.Engine;
import self.engine.TimeManager;
import self.simulation.sourcing.SourcingManager;
import self.utility.SimulationConfiguration;

import static self.utility.Preferences.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Simulation extends JFrame implements ActionListener {
    private final Engine engine;
    private SimulationGISMap map;
    private final SimulationGUI gui;
    private final TimeManager timeManager;
    private boolean isRunning;

    public Simulation() {
        super("Simulation");

        this.engine = new Engine();
        this.gui = new SimulationGUI(this);
        this.timeManager = new TimeManager();
        this.isRunning = false;

        createAndShowGUI();
    }

    public void staticUpdate() {
        gui.updateDate(timeManager.format(timeManager.date()));
    }

    public void update(float dt) {
        timeManager.update(dt);

        map.getRouteManager().getObjects().forEach(r -> r.update(dt));

        map.getFacilityManager().getCustomers().forEach(o -> o.update(dt));
        map.getFacilityManager().getDcs().forEach(o -> o.update(dt));
        map.getFacilityManager().getFactories().forEach(o -> o.update(dt));
        map.getFacilityManager().getSuppliers().forEach(o -> o.update(dt));
    }

    public void start() {
        if (!isRunning) {
            engine.run(this);
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void createAndShowGUI() {
        Container pane = getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        createMap(pane);
        gui.createFooter(pane);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(APPLICATION_MINIMUM_SIZE);
        addWindowListener(new SimulationWindowListener(this));
        setVisible(true);
        pack();
    }

    private void createMap(Container pane) {
        map = new SimulationGISMap(
                SimulationConfiguration.INSTANCE.getFacilityManager(),
                SimulationConfiguration.INSTANCE.getRouteManager()
        );
        map.setMouseAdapter(new SimulationGISMouseAdapter(map));
        map.setBackground(GIS_MAP_DEFAULT_BACKGROUND_COLOR);
        map.setPreferredSize(SIMULATION_DEFAULT_SIZE);
        map.setMinimumSize(SIMULATION_MINIMUM_SIZE);
        map.setZoom(SimulationConfiguration.INSTANCE.getMapZoomLevel());
        map.setCenter(SimulationConfiguration.INSTANCE.getMapCenterPoint());

        map.isInitialized = true;

        SourcingManager.INSTANCE.init(map.getRouteManager(), map.getFacilityManager());

        pane.add(map, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var command = e.getActionCommand();

        if ("next simulation speed command".equals(command)) {
            timeManager.nextSpeed();
            engine.setTimeCoefficient(timeManager.getTimeCoefficient());
            gui.updateSpeed(timeManager.getTimeCoefficientString());
        }
        else if ("previous simulation speed command".equals(command)) {
            timeManager.previousSpeed();
            engine.setTimeCoefficient(timeManager.getTimeCoefficient());
            gui.updateSpeed(timeManager.getTimeCoefficientString());
        }
        else if ("pause/play simulation speed command".equals(command)) {
            timeManager.pausePlay();
            engine.setTimeCoefficient(timeManager.getTimeCoefficient());
            try { gui.updatePauseButton(timeManager.getTimeCoefficient() == 0); }
            catch (IOException ex) { throw new RuntimeException(ex); }
        }
        else if ("close simulation".equals(command)) {
            stop();
            dispose();
        }
    }
}
