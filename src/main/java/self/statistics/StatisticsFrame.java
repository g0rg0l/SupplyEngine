package self.statistics;

import self.statistics.charts.*;

import javax.swing.*;
import java.awt.*;


public class StatisticsFrame extends JFrame {

    public StatisticsFrame() {
        super("Statistics Observation");

        setSize(1400, 1000);
        createGUI();
    }

    private void createGUI() {
        Container pane = getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridLayout(2, 3, 10, 10));

        var demandGeneratedChart = new DemandGeneratedChart();
        Statistics.register(demandGeneratedChart);
        demandGeneratedChart.getChartPanel().setSize(500, 500);
        add(demandGeneratedChart.getChartPanel());

        var deliveryTimeChart = new DeliveryTimeChart();
        Statistics.register(deliveryTimeChart);
        deliveryTimeChart.getChartPanel().setSize(500, 500);
        add(deliveryTimeChart.getChartPanel());

        var meanLeadTimeChart = new LeadTimeChart();
        Statistics.register(meanLeadTimeChart);
        meanLeadTimeChart.getChartPanel().setSize(500, 500);
        add(meanLeadTimeChart.getChartPanel());

        var inventoryBacklogChart = new InventoryBacklogChart();
        Statistics.register(inventoryBacklogChart);
        inventoryBacklogChart.getChartPanel().setSize(500, 500);
        add(inventoryBacklogChart.getChartPanel());

        var serviceLevelByProductsChart = new ServiceLevelByOrdersChart();
        Statistics.register(serviceLevelByProductsChart);
        serviceLevelByProductsChart.getChartPanel().setSize(500, 500);
        add(serviceLevelByProductsChart.getChartPanel());

        var profitAndLossChart = new ProfitAndLossChart();
        Statistics.register(profitAndLossChart);
        profitAndLossChart.getChartPanel().setSize(500, 500);
        add(profitAndLossChart.getChartPanel());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(false);
        pack();
    }
}
