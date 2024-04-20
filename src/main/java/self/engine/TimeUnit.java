package self.engine;

public enum TimeUnit {
    SECOND(1),
    MINUTE(60),
    HOUR(3600);

    public final long coefficient;

    TimeUnit(long coefficient) {
        this.coefficient = coefficient;
    }

    public static TimeUnit getFromString(String timeUnit) {
        switch (timeUnit) {
            case "seconds" -> { return SECOND; }
            case "minutes" -> { return MINUTE; }
            case "hours" -> { return HOUR; }
            default -> { return null; }
        }
    }
}
