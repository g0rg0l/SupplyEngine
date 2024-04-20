package self.application;

import self.map.AGISMap;
import self.map.AGISMapMouseAdapter;
import self.utility.SimulationConfiguration;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ApplicationGISMapMouseAdaptor extends AGISMapMouseAdapter {
    private final Application application;


    public ApplicationGISMapMouseAdaptor(AGISMap map, Application application) {
        super(map);
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
            application.applicationAddController.addFacility(e.getPoint());
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            application.applicationAddController.addRoute(e.getPoint());
        }
    }
}
