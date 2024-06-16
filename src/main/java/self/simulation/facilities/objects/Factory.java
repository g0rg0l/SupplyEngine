package self.simulation.facilities.objects;

import lombok.Getter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.simulation.demand.Order;
import self.simulation.facilities.Facility;
import self.simulation.facilities.FacilityFactory;
import self.simulation.facilities.FacilityType;
import self.simulation.facilities.IUpdatable;
import self.simulation.products.Product;
import self.simulation.shipments.Shipment;
import self.simulation.sourcing.SourcingManager;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Factory extends Facility implements IUpdatable, ISourceFacility, IDestinationFacility {
    private final Map<Order, Order> backorderToWaitingOrder;
    private final List<Order> fakes;

    @Getter
    private final Map<Product, Set<Product>> convertibles;

    @Getter
    private final Map<Product, Map<Product, Double>> coefficients;

    public Factory(int id, GeoPosition geoPosition, AGISMap map) {
        super(
                id,
                FacilityFactory.getImageByType(FacilityType.FACTORY),
                FacilityFactory.getSelectedImageByType(FacilityType.FACTORY),
                geoPosition,
                map
        );
        this.backorderToWaitingOrder = new HashMap<>();
        this.fakes = new ArrayList<>();
        this.coefficients = new HashMap<>();
        this.convertibles = new HashMap<>();
    }

    public Factory(Facility that) {
        super(that);
        this.backorderToWaitingOrder = new HashMap<>();
        this.convertibles = ((Factory) that).convertibles;
        this.coefficients = ((Factory) that).coefficients;
        this.fakes = ((Factory) that).fakes;
    }

    @Override
    public void draw(Graphics2D g2d, JXMapViewer map) {
        super.draw(g2d, map);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void processShipment(Shipment shipment) {
        if (!fakes.contains(shipment.getOrder())) {
            var orderToComplete = backorderToWaitingOrder.get(shipment.getOrder());
            sendShipmentByOrder(orderToComplete);
            backorderToWaitingOrder.remove(shipment.getOrder());
        } else fakes.remove(shipment.getOrder());
    }

    @Override
    public void processOrder(Order order) {
        Order incomingOrder = new Order();
        incomingOrder.setDestination(this);

        var component = getComponent(order.getProduct());
        if (component != null) {
            incomingOrder.setProduct(component);
            incomingOrder.setQuantity(order.getQuantity() * coefficients.get(component).get(order.getProduct()));

            var source = SourcingManager.INSTANCE.getSource(incomingOrder);
            if (source != null) {
                incomingOrder.setSource(source);
                SourcingManager.INSTANCE.initPath(incomingOrder);

                backorderToWaitingOrder.put(incomingOrder, order);
                source.processOrder(incomingOrder);
            }
        }
    }

    public boolean canCreate(Product product) {
        return convertibles.values()
                .stream()
                .anyMatch(s -> s.contains(product));
    }

    private void sendShipmentByOrder(Order order) {
        Shipment shipment = new Shipment(order);
        order.getRoute().move(shipment, order.getRoute().fromID == id);
    }

    private Product getComponent(Product product) {
        return convertibles.keySet()
                .stream()
                .filter(p -> convertibles.get(p).contains(product))
                .findFirst().orElse(null);
    }
}
