package self.simulation.demand;

import lombok.Getter;
import lombok.Setter;
import self.map.routing.MapRoute;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;

@Getter
@Setter
public class Order {

    private Customer destination;

    private DC source;

    private MapRoute route;

}
