package self.simulation.facilities;

import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;
import self.simulation.facilities.objects.Factory;
import self.simulation.facilities.objects.Supplier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class FacilityFactory {
    private static BufferedImage CUSTOMER_ICON;
    private static BufferedImage DC_ICON;
    private static BufferedImage FACTORY_ICON;
    private static BufferedImage SUPPLIER_ICON;

    static {
        try {
            CUSTOMER_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/customer.png")));
            DC_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/dc.png")));
            FACTORY_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/factory.png")));
            SUPPLIER_ICON = ImageIO.read(Objects.requireNonNull(FacilityFactory.class.getResource("/facilities/supplier.png")));
        }
        catch (IOException e) {
            System.out.println("Error while loading resources: can not load waypoint icons.");
            System.exit(-1);
        }
    }

    public static Facility create(FacilityType type, GeoPosition position, AGISMap map) {
        switch (type) {
            case CUSTOMER -> { return new Customer(CUSTOMER_ICON, position, map); }
            case DC ->       { return new DC(DC_ICON, position, map);             }
            case FACTORY ->  { return new Factory(FACTORY_ICON, position, map);   }
            case SUPPLIER -> { return new Supplier(SUPPLIER_ICON, position, map); }
        }

        return null;
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
}
