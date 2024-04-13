package self.simulation.facilities;

import self.map.AManager;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;
import self.simulation.facilities.objects.Factory;
import self.simulation.facilities.objects.Supplier;

import java.util.ArrayList;
import java.util.List;

public final class FacilityManager extends AManager<Facility> {
    private List<Customer> customers;
    private List<Supplier> suppliers;
    private List<DC> dcs;
    private List<Factory> factories;

    public FacilityManager() {
        super();

        this.customers = new ArrayList<>();
        this.suppliers = new ArrayList<>();
        this.dcs = new ArrayList<>();
        this.factories = new ArrayList<>();
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

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public void setDcs(List<DC> dcs) {
        this.dcs = dcs;
    }

    public void setFactories(List<Factory> factories) {
        this.factories = factories;
    }
}
