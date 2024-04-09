package self.map;

public class MapUtilities {
    public static final double EARTH_RADIUS = 6371;

    public static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public static double calculateDistanceByApproximation(double latFrom, double lonFrom, double latTo, double lonTo) {
        double lat1Rad = Math.toRadians(latFrom);
        double lat2Rad = Math.toRadians(latTo);
        double lon1Rad = Math.toRadians(lonFrom);
        double lon2Rad = Math.toRadians(lonTo);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);

        return Math.sqrt(x * x + y * y) * EARTH_RADIUS;
    }

    public static double calculateDistanceByHaversine(double latFrom, double lonFrom, double latTo, double lonTo) {
        double dLat = Math.toRadians((latTo - latFrom));
        double dLong = Math.toRadians((lonTo - lonFrom));

        latFrom = Math.toRadians(latFrom);
        latTo = Math.toRadians(latTo);

        double a = haversine(dLat) + Math.cos(latFrom) * Math.cos(latTo) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c * 1000;
    }

    public static int getGapForZoomLevel(int zoom) {
        if (zoom <= 3 ) return 1;
        if (zoom < 6) return 2;
        if (zoom < 8 ) return 3;

        return zoom;
    }
}
