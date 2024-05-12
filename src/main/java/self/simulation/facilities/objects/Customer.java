package self.simulation.facilities.objects;

import lombok.Getter;
import lombok.Setter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.simulation.demand.DemandGenerator;
import self.simulation.demand.Order;
import self.simulation.facilities.Facility;
import self.simulation.facilities.FacilityFactory;
import self.simulation.facilities.FacilityType;
import self.simulation.facilities.IUpdatable;
import self.simulation.sourcing.SourcingManager;
import self.simulation.sourcing.SourcingType;

import java.awt.*;

public class Customer extends Facility implements IUpdatable {
    private final DemandGenerator demandGenerator;

    @Getter
    @Setter
    private SourcingType sourcingType;

    public Customer(int id, GeoPosition geoPosition, AGISMap map) {
        super(
                id,
                FacilityFactory.getImageByType(FacilityType.CUSTOMER),
                FacilityFactory.getSelectedImageByType(FacilityType.CUSTOMER),
                geoPosition,
                map
        );

        this.demandGenerator = new DemandGenerator(24 * 60 * 60);
        this.sourcingType = SourcingType.CLOSEST;
    }

    public Customer(Facility that) {
        super(that);
        this.demandGenerator = ((Customer) that).demandGenerator;
        this.sourcingType = ((Customer) that).sourcingType;
    }

    @Override
    public void update(float dt) {
        demandGenerator.update(dt);

        var order = demandGenerator.getOrder();
        if (order != null) {
            order.setDestination(this);

            DC source = SourcingManager.INSTANCE.getSource(order);
            if (source != null) {
                order.setSource(source);
                SourcingManager.INSTANCE.initPath(order);

                System.out.println("new order from facility#" + id + " to facility#" + source.id);
                source.processOrder(order);
            }
        }
    }

    public void setDemandParameter(double orderCreationTime) {
        demandGenerator.setup(orderCreationTime);
    }

    public double getDemandParameter() {
        return demandGenerator.getOrderCreationTime();
    }
}
