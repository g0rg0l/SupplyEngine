package self.simulation.facilities.objects;

import lombok.Getter;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.simulation.demand.Order;
import self.simulation.facilities.Facility;
import self.simulation.facilities.FacilityFactory;
import self.simulation.facilities.FacilityType;
import self.simulation.facilities.IUpdatable;
import self.simulation.inventories.Inventory;
import self.simulation.products.Product;
import self.simulation.shipments.Shipment;
import self.simulation.sourcing.SourcingManager;

import java.util.*;

public class DC extends Facility implements IUpdatable, ISourceFacility, IDestinationFacility {
    private final List<Order> waitingOrders;

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

        this.waitingOrders = new ArrayList<>();
        this.inventories = new HashMap<>();
    }

    public DC(Facility that) {
        super(that);
        this.waitingOrders = new ArrayList<>(((DC) that).waitingOrders);
        this.inventories = new HashMap<>();
        for (Map.Entry<Product, Inventory> entry : ((DC) that).inventories.entrySet()) {
            this.inventories.put(entry.getKey(), new Inventory(entry.getValue()));
        }
    }

    @Override
    public void update(float dt) {

    }

    private void sendShipmentByOrder(Order order) {
        Shipment shipment = new Shipment(order);
        order.getRoute().move(shipment, order.getRoute().fromID == id);
    }

    private void backorderByOrder(Order order) {
        var inventory = inventories.get(order.getProduct());

        if (inventory != null) {
            double toBackorder = inventory.getNeededReplenishment(order.getQuantity());

            var outgointOrder = new Order();
            outgointOrder.setQuantity(toBackorder);
            outgointOrder.setProduct(order.getProduct());
            outgointOrder.setDestination(this);

            var source = SourcingManager.INSTANCE.getSource(outgointOrder);

            if (source != null) {
                outgointOrder.setSource(source);
                SourcingManager.INSTANCE.initPath(outgointOrder);

                inventory.backorder(toBackorder);
                inventory.reserve(order.getQuantity());
                source.processOrder(outgointOrder);
            }
        }
    }

    private void updateWaitingOrders(Product product) {
        var ordersToUpdate = waitingOrders.stream().filter(o -> o.getProduct().equals(product)).toList();
        var inventory = inventories.get(product);

        var toRemove = new ArrayList<Order>();
        for (Order order : ordersToUpdate) {
            if (inventory.getAvailableAmount() >= order.getQuantity()) {
                inventory.unload(order.getQuantity());
                toRemove.add(order);
            }
        }

        for (Order order : toRemove) {
            waitingOrders.remove(order);
            sendShipmentByOrder(order);
        }
    }

    @Override
    public void processOrder(Order order) {
        var inventory = inventories.get(order.getProduct());
        if (inventory == null) return;

        if (inventory.getNeededReplenishment(order.getQuantity()) == 0) {
            inventory.unload(order.getQuantity());
            sendShipmentByOrder(order);
        }
        else {
            backorderByOrder(order);
            waitingOrders.add(order);
        }
    }

    @Override
    public void processShipment(Shipment shipment) {
        var order = shipment.getOrder();
        var inventory = inventories.get(order.getProduct());

        if (inventory != null) {
            inventory.load(order.getQuantity());
            updateWaitingOrders(order.getProduct());
        }
    }
}
