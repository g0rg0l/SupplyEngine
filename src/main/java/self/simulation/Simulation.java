package self.simulation;

import self.engine.Engine;
import self.map.GISMap;
import self.map.SimulationGISMouseAdapter;
import self.utility.SimulationConfiguration;

import static self.utility.Preferences.*;

import javax.swing.*;
import java.awt.*;

public class Simulation extends JFrame {
    private final Engine engine;
    private boolean isRunning;

    public Simulation() {
        super("Simulation");

        this.engine = new Engine();
        this.isRunning = false;

        createAndShowGUI();
        setup();
    }

    private void createAndShowGUI() {
        Container pane = getContentPane();
        createMap(pane);

        setBackground(Color.RED);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new SimulationWindowListener(this));
        setVisible(true);
        pack();
    }

    private void createMap(Container pane) {
        GISMap map = new GISMap();
        map.setMouseAdapter(new SimulationGISMouseAdapter(map));
        map.setBackground(GIS_MAP_DEFAULT_BACKGROUND_COLOR);
        map.setPreferredSize(SIMULATION_DEFAULT_SIZE);
        map.setMinimumSize(SIMULATION_MINIMUM_SIZE);

        map.setZoom(SimulationConfiguration.INSTANCE.getMapZoomLevel());
        map.setCenter(SimulationConfiguration.INSTANCE.getMapCenterPoint());

        pane.add(map);
    }

    private void setup() {

    }

    public void update(float dt) {

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
}
