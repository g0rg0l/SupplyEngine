package self.simulation.facilities;

import self.map.AManager;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;
import self.simulation.facilities.objects.Factory;
import self.simulation.facilities.objects.Supplier;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FacilityManager extends AManager<Facility> {
    private final List<Customer> customers;
    private final List<Supplier> suppliers;
    private final List<DC> dcs;
    private final List<Factory> factories;

    public FacilityManager() {
        super();

        this.customers = new ArrayList<>();
        this.suppliers = new ArrayList<>();
        this.dcs = new ArrayList<>();
        this.factories = new ArrayList<>();
    }

    @Override
    public void addObject(Facility object) {
        if (object instanceof Customer) customers.add((Customer) object);
        if (object instanceof DC) dcs.add((DC) object);
        if (object instanceof Factory) factories.add((Factory) object);
        if (object instanceof Supplier) suppliers.add((Supplier) object);

        super.addObject(object);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public List<DC> getDcs() {
        return dcs;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public List<Rectangle2D> getHitBoxes(int zoom) {
        List<Rectangle2D> hitBoxes = new ArrayList<>();

        for (Facility facility : objects) {
            var point = facility.getPosition(zoom);
            var image = facility.getImage();

            hitBoxes.add(new Rectangle2D.Double(
                    point.getX() - image.getWidth() / 2.0, point.getY() - image.getHeight(),
                    image.getWidth(), image.getHeight()
            ));
        }

        return hitBoxes;
    }
}
