package self.simulation;

import self.engine.Engine;
import self.utility.SimulationConfiguration;

import static self.utility.Preferences.*;

import javax.swing.*;
import java.awt.*;

public class Simulation extends JFrame {
    private final Engine engine;
    private SimulationGISMap map;
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
        map = new SimulationGISMap();
        map.setMouseAdapter(new SimulationGISMouseAdapter(map));
        map.setBackground(GIS_MAP_DEFAULT_BACKGROUND_COLOR);
        map.setPreferredSize(SIMULATION_DEFAULT_SIZE);
        map.setMinimumSize(SIMULATION_MINIMUM_SIZE);

        map.setZoom(SimulationConfiguration.INSTANCE.getMapZoomLevel());
        map.setCenter(SimulationConfiguration.INSTANCE.getMapCenterPoint());


        map.setRoutes(SimulationConfiguration.INSTANCE.getRoutes());
        map.setFacilities(SimulationConfiguration.INSTANCE.getFacilities());

        map.getRouteManager().getObjects().get(0).move(new RouteMovable());
        map.getRouteManager().getObjects().get(5).move(new RouteMovable());
        map.getRouteManager().getObjects().get(9).move(new RouteMovable());
        map.getRouteManager().getObjects().get(12).move(new RouteMovable());
        map.getRouteManager().getObjects().get(14).move(new RouteMovable());

        pane.add(map);
    }

    private void setup() {

    }

    public void update(float dt) {
        map.getFacilityManager().getCustomers().forEach(o -> o.update(dt));
        map.getFacilityManager().getDcs().forEach(o -> o.update(dt));
        map.getFacilityManager().getFactories().forEach(o -> o.update(dt));
        map.getFacilityManager().getSuppliers().forEach(o -> o.update(dt));

        map.getRouteManager().getObjects().forEach(r -> r.update(dt));
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
