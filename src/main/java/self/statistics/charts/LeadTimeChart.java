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

import java.awt.*;


public class LeadTimeChart extends Chart {

    private final XYSeries series;
    private double sum;
    private int count;

    @Getter
    private final ChartPanel chartPanel;


    public LeadTimeChart() {
        super("Mean Lead Time Statistic");

        series = new XYSeries("Mean Lead Time");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Model Hour",
                "Time (hours)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        XYPlot plot = (XYPlot) chart.getPlot();
        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        renderer.setDefaultShapesVisible(false);
        plot.setRenderer(renderer);
    }

    public void addValue(double x, double y) {
        sum += (y / 3600.0);
        count++;

        series.addOrUpdate(x, sum / (double) count);
    }
}
