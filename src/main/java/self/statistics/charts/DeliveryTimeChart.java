package self.statistics.charts;

import lombok.Getter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;

import java.awt.*;


public class DeliveryTimeChart extends Chart {
    private final SimpleHistogramDataset dataSet;

    @Getter
    private final ChartPanel chartPanel;

    public DeliveryTimeChart() {
        super("Delivery Time Statistic");

        dataSet = new SimpleHistogramDataset("Delivery Time");

        JFreeChart chart = ChartFactory.createHistogram(
                title,
                "Time (Minutes)",
                "Frequency",
                dataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        for (int i = 0; i < 18; i++) {
            dataSet.addBin(
                    new SimpleHistogramBin(i * 20, (i + 1) * 20, true, false)
            );
        }
    }

    public void addValue(double value) {
        dataSet.addObservation(value);
    }
}
