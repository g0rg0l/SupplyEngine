package self.simulation.sourcing;

public enum SourcingType {
    CLOSEST,
    CHEAPEST,
    FASTEST;

    public static SourcingType getByString(String string) {
        switch (string) {
            case "closest" -> { return CLOSEST; }
            case "cheapest" -> { return CHEAPEST; }
            case "fastest" -> { return FASTEST; }

            default -> { return null; }
        }
    }
}
