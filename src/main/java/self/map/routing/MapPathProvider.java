package self.map.routing;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import org.jxmapviewer.viewer.GeoPosition;
import self.utility.SimulationConfiguration;

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
        hopper.setOSMFile("C:\\SupplyEngine\\src\\main\\resources\\osm\\norway-latest.osm.pbf");
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

        double userVehicleSpeedInKilometersInHour = SimulationConfiguration.INSTANCE.getVehicleSpeed();
        double vehicleSpeedInMetersInSecond = userVehicleSpeedInKilometersInHour / 3.6;

        return new Path(
                path.getDistance(),
                path.getDistance() / vehicleSpeedInMetersInSecond,
                points
        );
    }

    public Path getPath(GeoPosition a, GeoPosition b) {
        return getPath(a.getLatitude(), a.getLongitude(), b.getLatitude(), b.getLongitude());
    }
}
