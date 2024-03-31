package self.application;

import self.application.Application;
import self.map.AGISMap;
import self.map.AGISMapMouseAdapter;
import self.utility.SimulationConfiguration;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ApplicationGISMapMouseAdaptor extends AGISMapMouseAdapter {
    private final AGISMap map;
    private final Application application;


    public ApplicationGISMapMouseAdaptor(AGISMap map, Application application) {
        super(map);
        this.map = map;
        this.application = application;
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

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if (e.getButton() == MouseEvent.BUTTON3) {
            application.applicationAddController.add(e.getPoint());
        }
    }
}
