package self.simulation.inventories;

import lombok.Getter;
import self.simulation.products.Product;

public class Inventory {

    @Getter
    private final Product product;

    @Getter
    private double available; // 100

    @Getter
    private double backordered; // 0

    public Inventory(Product product, double initialStock) {
        this.product = product;
        this.available = initialStock;
        this.backordered = 0;
    }

    public void reserve(double quantity) {
        available -= quantity;
    }

    public void unload(double quantity) {
        backordered -= quantity;
        available += quantity;
    }

    public void backorder(double quantity) {
        backordered += quantity;
    }
}
