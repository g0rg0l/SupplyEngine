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
import self.simulation.inventories.Inventory;
import self.simulation.products.Product;
import self.simulation.shipments.Shipment;
import self.statistics.Statistics;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Supplier extends Facility implements IUpdatable, ISourceFacility {

    @Getter
    private final Map<Product, Inventory> inventories;

    public Supplier(int id, GeoPosition geoPosition, AGISMap map) {
        super(
                id,
                FacilityFactory.getImageByType(FacilityType.SUPPLIER),
                FacilityFactory.getSelectedImageByType(FacilityType.SUPPLIER),
                geoPosition,
                map
        );

        this.inventories = new HashMap<>();
    }

    public Supplier(Facility that) {
        super(that);

        this.inventories = new HashMap<>();
        for (Map.Entry<Product, Inventory> entry : ((Supplier) that).inventories.entrySet()) {
            this.inventories.put(entry.getKey(), new Inventory(entry.getValue()));
        }
    }

    @Override
    public void draw(Graphics2D g2d, JXMapViewer map) {
        super.draw(g2d, map);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void processOrder(Order order) {
        var inventory = inventories.get(order.getProduct());
        if (inventory == null) return;

        sendShipmentByOrder(order);
    }

    private void sendShipmentByOrder(Order order) {
        Shipment shipment = new Shipment(order);
        order.getRoute().move(shipment, order.getRoute().fromID == id);

        Statistics.addLoss(order.getQuantity() * Double.parseDouble(order.getProduct().getCost()));
    }
}
