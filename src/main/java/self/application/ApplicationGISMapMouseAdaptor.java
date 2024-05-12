package self.application;

import self.map.AGISMap;
import self.map.AGISMapMouseAdapter;
import self.simulation.facilities.Facility;
import self.utility.SimulationConfiguration;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

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

        if (application.applicationAddController.isActive()) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                application.applicationAddController.addFacility(e.getPoint());
            }
            if (e.getButton() == MouseEvent.BUTTON1) {
                application.applicationAddController.addRoute(e.getPoint());
            }
        } else {
            var rects = map.getFacilityManager().getHitBoxes(map.getZoom());
            var viewportBounds = map.getViewportBounds();
            var fixedMousePoint = new Point(
                    viewportBounds.x + e.getPoint().x,
                    viewportBounds.y + e.getPoint().y
            );

            Facility facility = null;
            for (Rectangle2D rect : rects) {
                if (rect.contains(fixedMousePoint)) {
                    facility = map.getFacilityManager().getObjects().get(rects.indexOf(rect));
                    break;
                }
            }

            if (facility != null) {
                var propertiesPanel = application.applicationGUI.getPropertiesPanel();
                propertiesPanel.removeAll();

                var facilityPropertiesPanel = facility.getPropertiesPanel();
                facilityPropertiesPanel.open();
                propertiesPanel.add(facilityPropertiesPanel);
                propertiesPanel.revalidate();
                propertiesPanel.repaint();
            }
            else {
                var propertiesPanel = application.applicationGUI.getPropertiesPanel();
                propertiesPanel.removeAll();
                propertiesPanel.revalidate();
                propertiesPanel.repaint();
            }
        }
    }
}
