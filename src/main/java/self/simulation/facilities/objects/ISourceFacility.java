package self.simulation.facilities.objects;

import self.simulation.demand.Order;

public interface ISourceFacility {
    void processOrder(Order order);
}
