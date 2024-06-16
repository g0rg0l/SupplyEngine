package self.engine;

import self.simulation.facilities.IUpdatable;
import self.statistics.Statistics;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeManager implements IUpdatable {
    public static TimeManager timeManager;

    private double seconds;
    private double modelSeconds;
    private LocalDateTime dateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0, 0);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E  dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
    private final Map<String, Float> speeds = new HashMap<>();
    private String speed = "1s";
    private boolean isPaused = false;

    public TimeManager() {
        speeds.put("0s", 0f);
        speeds.put("0.1s", 0.1f);
        speeds.put("0.5s", 0.5f);
        speeds.put("1s", 1.f);
        speeds.put("2s", 2.f);
        speeds.put("5s", 5.f);
        speeds.put("10s", 10.f);
        speeds.put("15s", 15.f);
        speeds.put("30s", 30.f);
        speeds.put("1m", 60.f);
        speeds.put("5m", 300.f);
        speeds.put("15m", 900.f);
        speeds.put("30m", 1800.f);
        speeds.put("1h", 3600.f);
        speeds.put("4h", 14400.f);
        speeds.put("12h", 43200.f);
        speeds.put("1d", 86400.f);
        speeds.put("7d", 604800.f);

        Statistics.timeManager = this;
        TimeManager.timeManager = this;
    }

    public LocalDateTime dateTime() {
        return dateTime;
    }

    public Date date() {
        return Date.from(dateTime().atZone(ZoneId.systemDefault()).toInstant());
    }

    public double modelDays() {
        return modelSeconds / 84600.0;
    }

    public double modelHours() {
        return modelSeconds / 3600.0;
    }

    public double modelMinutes() {
        return modelSeconds / 60.0;
    }

    public double modelSeconds() {
        return modelSeconds;
    }

    public String format(LocalDateTime date) {
        return date.format(formatter);
    }

    public void nextSpeed() {
        final String[] keys = {"0.1s", "0.5s", "1s", "2s", "5s", "10s", "15s", "30s", "1m", "5m", "15m", "30m", "1h", "4h", "12h", "1d", "7d"};
        int index = Arrays.asList(keys).indexOf(speed);
        if (index < keys.length - 1) {
            speed = keys[++index];
        }
    }

    public void previousSpeed() {
        final String[] keys = {"0.1s", "0.5s", "1s", "2s", "5s", "10s", "15s", "30s", "1m", "5m", "15m", "30m", "1h", "4h", "12h", "1d", "7d"};
        int index = Arrays.asList(keys).indexOf(speed);
        if (index > 0) {
            speed = keys[--index];
        }
    }

    public void pausePlay() {
        isPaused = !isPaused;
    }

    public String getTimeCoefficientString() {
        return speed;
    }

    public float getTimeCoefficient() {
        if (isPaused) return 0f;
        return speeds.get(speed);
    }

    @Override
    public void update(float dt) {

        seconds += dt;
        modelSeconds += dt;

        if (seconds >= 1) {
            int shift = (int) seconds;
            seconds -= shift;
            dateTime = dateTime.plusSeconds(shift);
        }
    }
}
