package self.simulation.demand;

import lombok.Getter;
import lombok.Setter;
import self.simulation.products.Product;

public class DemandGenerator {

    public DemandGenerator(DemandGenerator that) {
        this.orderCreationTime = that.orderCreationTime;
        this.product = that.product;
        this.quantity = that.quantity;
        this.time = 0;
    }

    @Getter
    @Setter
    private double orderCreationTime;

    @Getter
    @Setter
    private Product product;

    @Getter
    @Setter
    private double quantity;

    private double time;

    public DemandGenerator(double orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }

    public void update(float dt) {
        time += dt;
    }

    public Order getOrder() {
        if (time >= orderCreationTime) {
            time -= orderCreationTime;
            return new Order();
        }
        else return null;
    }
}
