package self.map.routing;

import self.map.AManager;



public class MapRouteManager extends AManager<MapRoute> {

    /**
     * Этот метод вызывается при обновлении зум-уровня карты, к которой привязан MapRouteManager.
     * Тут происходит упрощение/усложнение геометрии хранимых маршрутов по новому уровню детализации карты.
     * Также тут происходит расчёт новой дистанции маршрутов, новой скорости, с которой по маршрутам двигаются
     * Shipment'ы и т.д.
     *
     * @param zoom Новый уровень детализации карты
     */
    public void onZoomUpdated(int zoom) {
        // TODO: ДОБАВИТЬ СОБСНА ФУНКЦИОНАЛ
    }
}