package self.statistics;

import self.engine.TimeManager;
import self.simulation.demand.Order;
import self.statistics.charts.*;

import java.util.HashMap;
import java.util.Map;

public class Statistics {

    private static final Map<String, Chart> chartsByName = new HashMap<>();
    public static TimeManager timeManager;

    public static void register(Chart chart) {
        chartsByName.put(chart.getTitle(), chart);
    }

    public static void addCustomerOrdersCountValue(int y) {
        var chart = (DemandGeneratedChart) chartsByName.get("Demand Generated Statistics");
        var hourOfTrigger = timeManager.modelHours();

        chart.addValue(hourOfTrigger);
    }

    public static void addDeliverTimeValue(double deliveryTime) {
        var chart = (DeliveryTimeChart) chartsByName.get("Delivery Time Statistic");

        chart.addValue(deliveryTime);
    }

    public static void addLeadTimeValue(double leadTime) {
        var chart = (LeadTimeChart) chartsByName.get("Mean Lead Time Statistic");
        var hourOfTrigger = timeManager.modelHours();

        chart.addValue(hourOfTrigger, leadTime);
    }

    public static void addProductsBacklogValue(double amount) {
        var chart = (InventoryBacklogChart) chartsByName.get("Inventory Backlog Statistic");
        var hourOfTrigger = timeManager.modelHours();

        chart.addValue(hourOfTrigger, amount);
    }

    public static void addServiceLevelByOrdersValue(Order order, double leadTime) {
        var chart = (ServiceLevelByOrdersChart) chartsByName.get("Service Level by Orders Statistic");
        var hourOfTrigger = timeManager.modelHours();

        chart.addValue(hourOfTrigger, order, leadTime);
    }

    public static void addProfit(double amount) {
        var chart = (ProfitAndLossChart) chartsByName.get("Profit and Loss Statistic");
        var hourOfTrigger = timeManager.modelHours();

        chart.addValue(true, hourOfTrigger, amount);
    }

    public static void addLoss(double amount) {
        var chart = (ProfitAndLossChart) chartsByName.get("Profit and Loss Statistic");
        var hourOfTrigger = timeManager.modelHours();

        chart.addValue(false, hourOfTrigger, amount);
    }
}
