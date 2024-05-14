package self.simulation.facilities.objects;

import self.simulation.shipments.Shipment;

public interface IDestinationFacility {
    void processShipment(Shipment shipment);
}
