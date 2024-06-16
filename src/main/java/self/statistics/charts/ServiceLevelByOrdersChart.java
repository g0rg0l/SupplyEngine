package self.statistics.charts;

import lombok.Getter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import self.simulation.demand.Order;

import java.awt.*;


public class ServiceLevelByOrdersChart extends Chart {

    private final XYSeries series;

    @Getter
    private final ChartPanel chartPanel;

    private double onTimeOrdersCount;
    private double lateOrdersCount;

    public ServiceLevelByOrdersChart() {
        super("Service Level by Orders Statistic");

        series = new XYSeries("Service Level by Orders");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Model Hour",
                "Ratio",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);


        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRangeAxis().setAutoRange(false);

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        renderer.setDefaultShapesVisible(false);
        plot.setRenderer(renderer);

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
    }

    public void addValue(double x, Order order, double leadTime) {
        if (order.getExpectedLeadTime() == 0) return;

        if (leadTime > order.getExpectedLeadTime()) lateOrdersCount++;
        else onTimeOrdersCount++;

        series.addOrUpdate(x, onTimeOrdersCount / (onTimeOrdersCount + lateOrdersCount));
    }
}
