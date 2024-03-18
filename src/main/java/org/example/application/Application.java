package org.example.application;

import org.example.application.map.GISPanel;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import java.util.*;

public class Application {
    public static void main(String[] args) {
        GISPanel mapViewer = new GISPanel();
        mapViewer.initApplicationComponents();

        JFrame frame = new JFrame("Supply Engine");
        frame.getContentPane().add(mapViewer);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        GeoPosition frankfurt = new GeoPosition(50,  7, 0, 8, 41, 0);
        GeoPosition wiesbaden = new GeoPosition(50,  5, 0, 8, 14, 0);
        GeoPosition mainz     = new GeoPosition(50,  0, 0, 8, 16, 0);
        GeoPosition darmstadt = new GeoPosition(49, 52, 0, 8, 39, 0);
        GeoPosition offenbach = new GeoPosition(50,  6, 0, 8, 46, 0);

        // Create a track from the geo-positions
        List<GeoPosition> track = Arrays.asList(frankfurt, wiesbaden, mainz, darmstadt, offenbach);

        // Set the focus
        mapViewer.zoomToBestFit(new HashSet<>(track), 0.75);

        // Create waypoints from the geo-positions
        Set<Waypoint> waypoints = new HashSet<>(Arrays.asList(
                new DefaultWaypoint(frankfurt),
                new DefaultWaypoint(wiesbaden),
                new DefaultWaypoint(mainz),
                new DefaultWaypoint(darmstadt),
                new DefaultWaypoint(offenbach)
        ));

        mapViewer.initMapLocations(waypoints);
    }
}
