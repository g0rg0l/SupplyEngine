package self.simulation.demand;

import lombok.Getter;
import lombok.Setter;
import self.map.routing.MapRoute;
import self.simulation.facilities.objects.IDestinationFacility;
import self.simulation.facilities.objects.ISourceFacility;
import self.simulation.products.Product;

import java.util.Date;

@Getter
@Setter
public class Order {

    private IDestinationFacility destination;

    private ISourceFacility source;

    private MapRoute route;

    private Product product;

    private double quantity;

    private double expectedLeadTime;

    private double modelSecondsCreated;
}
