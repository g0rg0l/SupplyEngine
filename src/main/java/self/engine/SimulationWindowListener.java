package self.engine;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SimulationWindowListener implements WindowListener {
    private final Simulation simulation;

    public SimulationWindowListener(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        simulation.stop();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
