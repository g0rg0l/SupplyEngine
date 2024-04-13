package self.application;

import self.map.AGISMap;
import self.map.routing.MapRoute;
import self.simulation.facilities.Facility;


public final class ApplicationGISMap extends AGISMap {

    /**
     * Метод динамического добавления Facility на карту. Используется из Application'а.
     * Происходит просто добавление абстрактного объекта с конкретным типом в
     * Facility Manager карты
     *
     * @param facility Абстрактный Facility, добавляемый на карту
     */
    public void addFacility(Facility facility) {
        facilityManager.addObject(facility);
        repaint();
    }

    /**
     * Метод динамического добавления Route на карту. Используется из Application'а.
     * Происходит просто добавление абстрактного пути, который только рисуется на карте
     * без какого-либо функционала.
     *
     * @param route Абстрактный MapRoute, добавляемый на карту
     */
    public void addRoute(MapRoute route) {
        routeManager.addObject(route);
        repaint();
    }
}
