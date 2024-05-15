package self.simulation.shipments;

import lombok.Getter;
import self.simulation.RouteMovable;
import self.simulation.demand.Order;

@Getter
public class Shipment extends RouteMovable {

    private final Order order;

    public Shipment(Order order) {
        this.order = order;
    }

    @Override
    public void arrival() {
        order.getDestination().processShipment(this);
    }
}
