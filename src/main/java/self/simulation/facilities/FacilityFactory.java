package self.simulation.facilities;

import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;
import self.simulation.facilities.objects.Factory;
import self.simulation.facilities.objects.Supplier;
import self.simulation.facilities.properties.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class FacilityFactory {
    public static BufferedImage CUSTOMER_ICON;
    public static BufferedImage DC_ICON;
    public static BufferedImage FACTORY_ICON;
    public static BufferedImage SUPPLIER_ICON;
    public static BufferedImage CUSTOMER_SELECTED_ICON;
    public static BufferedImage DC_SELECTED_ICON;
    public static BufferedImage FACTORY_SELECTED_ICON;
    public static BufferedImage SUPPLIER_SELECTED_ICON;
    public static int count;

    static {
        try {
            CUSTOMER_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/customer.png")));
            DC_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/dc.png")));
            FACTORY_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/factory.png")));
            SUPPLIER_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/supplier.png")));
            CUSTOMER_SELECTED_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/customer_selected.png")));
            DC_SELECTED_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/dc_selected.png")));
            FACTORY_SELECTED_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/factory_selected.png")));
            SUPPLIER_SELECTED_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/supplier_selected.png")));
        }
        catch (IOException e) {
            System.out.println("Error while loading resources: can not load waypoint icons.");
            System.exit(-1);
        }
    }

    /**
     * Метод, создающий новый фасилити. Возвращает новый объект и создаёт привязанную к нему
     * панель пропертей, которая будет отображаться при выборе фасилити на карте в Application'е
     */
    public static Facility create(FacilityType type, GeoPosition position, AGISMap map) {
        Facility facility;

        switch (type) {
            case CUSTOMER -> facility = new Customer(++count, position, map);
            case DC       -> facility = new DC(++count, position, map);
            case FACTORY  -> facility = new Factory(++count, position, map);
            case SUPPLIER -> facility = new Supplier(++count, position, map);

            default -> { return null; }
        }

        facility.setPropertiesPanel(createPropertiesPanel(facility));

        return facility;
    }

    public static BufferedImage getImageByType(FacilityType type) {
        switch (type) {
            case CUSTOMER -> { return CUSTOMER_ICON; }
            case DC ->       { return DC_ICON;       }
            case FACTORY ->  { return FACTORY_ICON;  }
            case SUPPLIER -> { return SUPPLIER_ICON; }
        }

        return null;
    }

    public static BufferedImage getSelectedImageByType(FacilityType type) {
        switch (type) {
            case CUSTOMER -> { return CUSTOMER_SELECTED_ICON; }
            case DC ->       { return DC_SELECTED_ICON;       }
            case FACTORY ->  { return FACTORY_SELECTED_ICON;  }
            case SUPPLIER -> { return SUPPLIER_SELECTED_ICON; }
        }

        return null;
    }

    private static PropertiesPanel createPropertiesPanel(Facility facility) {
        if (facility instanceof Customer) return new CustomerPropertiesPanel(facility);
        if (facility instanceof DC)       return new DCPropertiesPanel(facility);
        if (facility instanceof Factory)  return new FactoryPropertiesPanel(facility);
        if (facility instanceof Supplier) return new SupplierPropertiesPanel(facility);

        return null;
    }
}
