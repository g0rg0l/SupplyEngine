package self.map.routing;

import lombok.Getter;
import lombok.Setter;
import org.jxmapviewer.viewer.GeoPosition;

import java.util.List;

@Getter
@Setter
public class Path {
    private double distance;
    private double time;
    private List<GeoPosition> geoPositions;

    public Path(double distance, double time, List<GeoPosition> geoPositions) {
        this.distance = distance;
        this.time = time;
        this.geoPositions = geoPositions;
    }
}
