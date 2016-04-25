package view.custom.derevyanko.com.piechartview;

/**
 * Created by anton on 4/24/16.
 */
public final class StaticGraphData {

    private ChartData chartData;
    private int proportion;
    private int startValue;
    private int color;

    public StaticGraphData(ChartData chartData) {
        this.chartData = chartData;
    }

    public ChartData getChartData() {
        return chartData;
    }

    public void setChartData(ChartData chartData) {
        this.chartData = chartData;
    }

    public int getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
