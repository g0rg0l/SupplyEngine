package self.map.routing;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import org.jxmapviewer.viewer.GeoPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapPathProvider {
    public static MapPathProvider INSTANCE = new MapPathProvider();
    private final GraphHopper hopper;

    private MapPathProvider() {
        hopper = new GraphHopper();

        initRoutingSettings();
    }

    private void initRoutingSettings() {
        hopper.setGraphHopperLocation("target/routing-graph-cache");
        hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false));
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));
        hopper.setOSMFile("C:\\SupplyEngine\\src\\main\\resources\\osm\\kazakhstan-latest.osm.pbf");
        hopper.importOrLoad();
    }

    public Path getPath(double fromLat, double fromLon, double toLat, double toLon) {
        GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon).setProfile("car").setLocale(Locale.US);
        GHResponse rsp = hopper.route(req);

        if (rsp.hasErrors()) {
            throw new RuntimeException(rsp.getErrors().toString());
        }

        ResponsePath path = rsp.getBest();

        List<GeoPosition> points = new ArrayList<>();
        path.getPoints().forEach(p -> points.add(new GeoPosition(p.getLat(), p.getLon())));

        return new Path(
                path.getDistance(),
                path.getTime(),
                points
        );
    }

    public Path getPath(GeoPosition a, GeoPosition b) {
        return getPath(a.getLatitude(), a.getLongitude(), b.getLatitude(), b.getLongitude());
    }
}
