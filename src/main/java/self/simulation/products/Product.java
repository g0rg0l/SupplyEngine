package self.simulation.products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String name;
    private String unit;
    private String cost;
    private String costUnit;

    public Product(String name, String unit, String cost, String costUnit) {
        this.name = name;
        this.unit = unit;
        this.cost = cost;
        this.costUnit = costUnit;
    }

    @Override
    public String toString() {
        return name;
    }
}
