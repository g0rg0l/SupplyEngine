package self.simulation.facilities.objects;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.simulation.facilities.Facility;
import self.simulation.facilities.FacilityFactory;
import self.simulation.facilities.FacilityType;
import self.simulation.facilities.IUpdatable;

import java.awt.*;

public class Supplier extends Facility implements IUpdatable {
    public Supplier(int id, GeoPosition geoPosition, AGISMap map) {
        super(
                id,
                FacilityFactory.getImageByType(FacilityType.SUPPLIER),
                FacilityFactory.getSelectedImageByType(FacilityType.SUPPLIER),
                geoPosition,
                map
        );
    }

    public Supplier(Facility that) {
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
