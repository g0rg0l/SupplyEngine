package self.simulation.facilities.objects;

import lombok.Getter;
import lombok.Setter;
import org.jxmapviewer.viewer.GeoPosition;
import self.engine.TimeManager;
import self.map.AGISMap;
import self.map.routing.MapRoute;
import self.simulation.demand.DemandGenerator;
import self.simulation.facilities.Facility;
import self.simulation.facilities.FacilityFactory;
import self.simulation.facilities.FacilityType;
import self.simulation.facilities.IUpdatable;
import self.simulation.products.Product;
import self.simulation.shipments.Shipment;
import self.simulation.sourcing.SourcingManager;
import self.simulation.sourcing.SourcingType;
import self.statistics.Statistics;

import java.util.Date;

public class Customer extends Facility implements IUpdatable, IDestinationFacility {
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

        this.demandGenerator = new DemandGenerator();
        this.sourcingType = SourcingType.CLOSEST;
    }

    public Customer(Facility that) {
        super(that);
        this.demandGenerator = new DemandGenerator(((Customer) that).demandGenerator);
        this.sourcingType = ((Customer) that).sourcingType;
    }

    @Override
    public void update(float dt) {
        demandGenerator.update(dt);

        var order = demandGenerator.getOrder();
        if (order != null) {
            Statistics.addCustomerOrdersCountValue(1);
            order.setModelSecondsCreated(TimeManager.timeManager.modelSeconds());
            order.setDestination(this);
            order.setProduct(demandGenerator.getProduct());
            order.setExpectedLeadTime(demandGenerator.getExpectedLeadTime());
            order.setQuantity(demandGenerator.getQuantity());

            var source = SourcingManager.INSTANCE.getSource(order);
            if (source != null) {
                order.setSource(source);
                SourcingManager.INSTANCE.initPath(order);
                source.processOrder(order);
            }
        }
    }

    @Override
    public void processShipment(Shipment shipment) {
        MapRoute route = shipment.getOrder().getRoute();
        double routeTimeInSeconds = route.getOriginalTime();
        double routeTimeInMinutes = routeTimeInSeconds / 60.0;
        Statistics.addDeliverTimeValue(routeTimeInMinutes);

        double deliveredModelSeconds = TimeManager.timeManager.modelSeconds();
        double createdModelSeconds = shipment.getOrder().getModelSecondsCreated();
        double leadTimeInSeconds = deliveredModelSeconds - createdModelSeconds;
        Statistics.addLeadTimeValue(leadTimeInSeconds);

        Statistics.addServiceLevelByOrdersValue(shipment.getOrder(), leadTimeInSeconds);
        Statistics.addProfit(shipment.getOrder().getQuantity() * Double.parseDouble(shipment.getOrder().getProduct().getCost()));
    }

    public void setDemandPeriodParameter(double orderCreationTime) {
        demandGenerator.setOrderCreationTime(orderCreationTime);
    }

    public double getDemandPeriodParameter() {
        return demandGenerator.getOrderCreationTime();
    }

    public void setDemandProductParameter(Product product) {
        demandGenerator.setProduct(product);
    }

    public Product getDemandProductParameter() {
        return demandGenerator.getProduct();
    }

    public void setDemandQuantityParameter(double quantity) {
        demandGenerator.setQuantity(quantity);
    }

    public double getDemandQuantityParameter() {
        return demandGenerator.getQuantity();
    }

    public void setExpectedLeadTimeParameter(double expectedLeadTime) {
        demandGenerator.setExpectedLeadTime(expectedLeadTime);
    }

    public double getExpectedLeadTimeParameter() {
        return demandGenerator.getExpectedLeadTime();
    }
}
