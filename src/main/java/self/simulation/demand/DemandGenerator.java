package self.simulation.demand;

import lombok.Getter;
import lombok.Setter;
import self.simulation.products.Product;

public class DemandGenerator {

    public DemandGenerator() {
        this.orderCreationTime = 84600.0;
        this.product = null;
        this.quantity = 0;
        this.expectedLeadTime = 84600.0;
        this.time = 0;
    }


    public DemandGenerator(DemandGenerator that) {
        this.orderCreationTime = that.orderCreationTime;
        this.product = that.product;
        this.quantity = that.quantity;
        this.expectedLeadTime = that.expectedLeadTime;
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

    @Getter
    @Setter
    private double expectedLeadTime;

    private double time;

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
