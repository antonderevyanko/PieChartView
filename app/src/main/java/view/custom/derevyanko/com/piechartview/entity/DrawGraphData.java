package view.custom.derevyanko.com.piechartview.entity;

import view.custom.derevyanko.com.piechartview.dynamic.Dynamic;

/**
 * Created by anton on 4/24/16.
 */
public final class DrawGraphData {

    private final ChartData chartData;
    private int proportion;
    private int startValue;
    private int color;
    private Dynamic<Integer> dynamic;

    public DrawGraphData(ChartData chartData) {
        this.chartData = chartData;
    }

    public ChartData getChartData() {
        return chartData;
    }

    // Dynamic part

    public void addDynamic(Dynamic<Integer> dynamic) {
        this.dynamic = dynamic;
    }

    public int getValue() {
        if (dynamic != null) {
            return dynamic.getPosition();
        } else {
            return getProportion();
        }
    }

    public void update(long now) {
        if (dynamic != null) {
            dynamic.update(now);
        }
    }

    public boolean isFinished() {
        return dynamic == null || dynamic.isFinished();
    }

    // static part

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
