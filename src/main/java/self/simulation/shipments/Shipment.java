package self.simulation.shipments;

import lombok.Getter;
import self.simulation.RouteMovable;
import self.simulation.demand.Order;

public class Shipment extends RouteMovable {

    @Getter
    private final Order order;

    public Shipment(Order order) {
        this.order = order;
    }

    @Override
    public void arrival() {
        order.getDestination().processShipment(this);
    }
}
