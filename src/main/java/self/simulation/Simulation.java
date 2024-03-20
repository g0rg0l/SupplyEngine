package self.simulation;

import self.engine.Engine;
import self.map.GISPanel;

import javax.swing.*;
import java.awt.*;

public class Simulation extends JFrame {
    public final static Dimension DEFAULT_SIZE = new Dimension(800, 600);
    public final static Dimension MINIMUM_SIZE = new Dimension(800, 600);
    private final Engine engine;
    private boolean isRunning;

    public Simulation() {
        super("Simulation");

        this.engine = new Engine();
        this.isRunning = false;

        createAndShowGUI();
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
        GISPanel map = new GISPanel();
        map.setBackground(Color.LIGHT_GRAY);
        map.setPreferredSize(DEFAULT_SIZE);
        map.setMinimumSize(MINIMUM_SIZE);
        pane.add(map);
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
