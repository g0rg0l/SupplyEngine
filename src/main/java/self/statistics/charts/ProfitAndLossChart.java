package self.statistics.charts;

import lombok.Getter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StrokeMap;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.StrokeSample;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;


public class ProfitAndLossChart extends Chart {
    private final XYSeries lossSeries;
    private double loss = 0;
    private double profit = 0;
    private final XYSeries profitSeries;

    @Getter
    private final ChartPanel chartPanel;

    public ProfitAndLossChart() {
        super("Profit and Loss Statistic");

        lossSeries = new XYSeries("Loss");
        profitSeries = new XYSeries("Profit");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(lossSeries);
        dataset.addSeries(profitSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Model Hour",
                "Amount",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        XYPlot plot = (XYPlot) chart.getPlot();
        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        renderer.setSeriesStroke(1, new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        renderer.setDefaultShapesVisible(false);
        plot.setRenderer(renderer);

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        addValue(true, 0, 0);
        addValue(false, 0, 0);
        addValue(true, 1, 1);
        addValue(false, 1, 0.75);
    }

    public void addValue(boolean isProfit, double x, double y) {
        if (isProfit) {
            profit += y;
            profitSeries.addOrUpdate(x, profit);
        }
        else {
            loss += y;
            lossSeries.addOrUpdate(x, loss);
        }
    }
}
