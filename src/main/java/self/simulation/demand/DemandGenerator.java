package self.simulation.demand;

import lombok.Getter;

public class DemandGenerator {

    @Getter
    private double orderCreationTime;

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

    public void setup(double orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
        time = 0;
    }
}
