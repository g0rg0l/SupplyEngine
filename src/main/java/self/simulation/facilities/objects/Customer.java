package self.simulation.facilities.objects;

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
    public void draw(Graphics2D g2d, JXMapViewer map) {
        super.draw(g2d, map);
    }

    @Override
    public void update(float dt) {
        demandGenerator.update(dt);

        if (demandGenerator.isReadyToCreate()) {
            Order order = demandGenerator.getOrder();
            DC source = SourcingManager.INSTANCE.getSource(this);
            System.out.println("dasd");
        }
    }

    public void setDemandParameter(double orderCreationTime) {
        demandGenerator.setOrderCreationTime(orderCreationTime);
    }

    public double getDemandParameter() {
        return demandGenerator.getOrderCreationTime();
    }

    public SourcingType getSourcingType() {
        return sourcingType;
    }

    public void setSourcingType(SourcingType sourcingType) {
        this.sourcingType = sourcingType;
    }
}
