package self.engine;

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
    }

    private void createAndShowGUI() {
        setBackground(Color.RED);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new SimulationWindowListener(this));
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
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
