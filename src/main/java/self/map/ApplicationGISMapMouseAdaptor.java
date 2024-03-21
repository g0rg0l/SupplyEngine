package self.map;

import self.utility.SimulationConfiguration;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ApplicationGISMapMouseAdaptor extends AGISMapMouseAdapter {
    private final GISMap map;


    public ApplicationGISMapMouseAdaptor(GISMap map) {
        super(map);
        this.map = map;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        SimulationConfiguration.INSTANCE.setMapCenterPoint(map.getCenter());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);
        SimulationConfiguration.INSTANCE.setMapZoomLevel(map.getZoom());
        SimulationConfiguration.INSTANCE.setMapCenterPoint(map.getCenter());
    }
}
