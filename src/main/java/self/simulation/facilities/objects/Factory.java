package self.simulation.facilities.objects;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.simulation.facilities.Facility;
import self.simulation.facilities.IUpdatable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Factory extends Facility implements IUpdatable {
    public Factory(BufferedImage image, GeoPosition geoPosition, AGISMap map) {
        super(image, geoPosition, map);
    }

    public Factory(Facility that) {
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