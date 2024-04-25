package self.simulation.facilities.objects;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.simulation.facilities.Facility;
import self.simulation.facilities.FacilityFactory;
import self.simulation.facilities.FacilityType;
import self.simulation.facilities.IUpdatable;

import java.awt.*;

public class Customer extends Facility implements IUpdatable {
    public Customer(int id, GeoPosition geoPosition, AGISMap map) {
        super(
                id,
                FacilityFactory.getImageByType(FacilityType.CUSTOMER),
                FacilityFactory.getSelectedImageByType(FacilityType.CUSTOMER),
                geoPosition,
                map
        );
    }

    public Customer(Facility that) {
        super(that);
    }

    @Override
    public void draw(Graphics2D g2d, JXMapViewer map) {
        super.draw(g2d, map);
    }

    @Override
    public void update(float dt) {

    }
}
