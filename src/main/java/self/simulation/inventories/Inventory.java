package self.simulation.inventories;

import lombok.Getter;
import self.simulation.products.Product;

@Getter
public class Inventory {

    private final Product product;

    private double availableAmount;

    private double backorderAmount;

    private double reserveAmount;

    public Inventory(Product product, double initialStock) {
        this.product = product;
        this.availableAmount = initialStock;
        this.backorderAmount = 0;
        this.reserveAmount = 0;
    }

    public Inventory(Inventory that) {
        this.product = that.product;
        this.availableAmount = that.availableAmount;
        this.backorderAmount = 0;
        this.reserveAmount = 0;
    }

    public void load(double quantity) {
        availableAmount += quantity;
        backorderAmount = Math.max(backorderAmount - quantity, 0);
    }

    public void unload(double quantity) {
        availableAmount -= quantity;
        reserveAmount = Math.max(reserveAmount - quantity, 0);
    }

    public void reserve(double quantity) {
        reserveAmount += quantity;
    }

    public void backorder(double quantity) {
        backorderAmount += quantity;
    }

    public double getNeededReplenishment(double quantity) {
        double nowAndWillAmount = availableAmount + backorderAmount - reserveAmount;

        if (nowAndWillAmount >= quantity) return 0;
        else return quantity - nowAndWillAmount;
    }

    @Override
    public String toString() {
        return "Inventory [" + product.toString() + "]: " +
                "available: " + availableAmount + ", " +
                "backorder: " + backorderAmount + ", " +
                "reserve: "   + reserveAmount;
    }
}
