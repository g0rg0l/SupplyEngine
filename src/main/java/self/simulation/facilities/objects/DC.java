package self.simulation.facilities.objects;

import lombok.Getter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.simulation.RouteMovable;
import self.simulation.demand.Order;
import self.simulation.facilities.Facility;
import self.simulation.facilities.FacilityFactory;
import self.simulation.facilities.FacilityType;
import self.simulation.facilities.IUpdatable;
import self.simulation.inventories.Inventory;
import self.simulation.products.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class DC extends Facility implements IUpdatable {
    private final Queue<Order> incomingOrdersQueue;

    @Getter
    private final Map<Product, Inventory> inventories;

    public DC(int id, GeoPosition geoPosition, AGISMap map) {
        super(
                id,
                FacilityFactory.getImageByType(FacilityType.DC),
                FacilityFactory.getSelectedImageByType(FacilityType.DC),
                geoPosition,
                map
        );

        this.incomingOrdersQueue = new PriorityQueue<>((Order o1, Order o2) -> 0);
        this.inventories = new HashMap<>();
    }

    public DC(Facility that) {
        super(that);
        this.incomingOrdersQueue = ((DC) that).incomingOrdersQueue;
        this.inventories = ((DC) that).inventories;
    }

    public void processOrder(Order order) {
        incomingOrdersQueue.add(order);
    }

    @Override
    public void update(float dt) {
        while (!incomingOrdersQueue.isEmpty()) {
            var order = incomingOrdersQueue.poll();
            sendShipmentByOrder(order);
        }
    }

    private void sendShipmentByOrder(Order order) {
        order.getRoute().move(new RouteMovable(), order.getRoute().fromID == id);
    }
}
