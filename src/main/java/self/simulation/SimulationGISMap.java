package self.simulation;

import self.map.AGISMap;
import self.map.routing.MapRoute;
import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;
import self.simulation.facilities.objects.Factory;
import self.simulation.facilities.objects.Supplier;

import java.util.ArrayList;
import java.util.List;

public final class SimulationGISMap extends AGISMap {
    /**
     * Метод разовой "регистрации" абстрактных Facility в симуляцию.
     * Каждый объект имеет свой тип, но без расширения - просто иконка на карте.
     * Этот метод создаёт из переданных болванчиков нормальные объекты и сохраняет их в
     * своём Facility Manager'е
     *
     * @param facilities Список абстрактных Facility для добавления в симуляцию
     */
    public void setFacilities(List<Facility> facilities) {
        List<Customer> customers = new ArrayList<>();
        List<Supplier> suppliers = new ArrayList<>();
        List<DC>             dcs = new ArrayList<>();
        List<Factory>  factories = new ArrayList<>();

        for (Facility facility : facilities) {
            if (facility instanceof Customer)
                customers.add(new Customer(facility));

            if (facility instanceof DC)
                dcs.add(new DC(facility));

            if (facility instanceof Factory)
                factories.add(new Factory(facility));

            if (facility instanceof Supplier)
                suppliers.add(new Supplier(facility));
        }

        facilityManager.setCustomers(customers);
        facilityManager.setDcs(dcs);
        facilityManager.setSuppliers(suppliers);
        facilityManager.setFactories(factories);

        var objects = facilities.stream().map(Facility::new).toList();
        facilityManager.setObjects(objects);
    }

    /**
     * Метод разовой "регистрации" всех MapRoute'ов из Application'а в симуляцию.
     * Создаёт копии исходных объектов для их дальнейшего отображения, обновления
     * и взаимодействия с ними во время моделирования
     *
     * @param routes Список абстрактных MapRoute'ов для добавления в симуляцию
     */
    public void setRoutes(List<MapRoute> routes) {
        var objects = routes.stream().map(MapRoute::new).toList();
        routeManager.setObjects(objects);
    }
}
