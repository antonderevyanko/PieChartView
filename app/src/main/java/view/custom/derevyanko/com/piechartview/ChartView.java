package view.custom.derevyanko.com.piechartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public final class ChartView extends View {

    private static final int LINES_MARGIN = 10;
    private static final int STROKE_WIDTH = 18;
    private static final int FULL_ANGLE = 90;
    private static final int CIRCLE_WIDTH = 60;

    private Paint fillPaint;
    private Paint whitePaint;

    private int outerRad;
    private int innerRad;
    private int centerX, centerY;

    List<ChartGraphData> chartGraphs;
    private RectF oval;
    private Paint fullLinePaint;
    private Paint endsPaint;
    private Paint linePaint;

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void applyData(List<ChartData> charts) {
        if (charts.isEmpty()) {
            return;
        }
        int sum = 0;
        for (ChartData chart : charts) {
            sum = sum + chart.getCount();
        }
        int prevPosition = 0;
        for (ChartData chartData : charts) {
            ChartGraphData chartGraphData = new ChartGraphData(chartData);
            chartGraphData.proportion = Math.round(chartData.getCount() * 360 / sum);
            chartGraphData.startValue = prevPosition;
            prevPosition = chartGraphData.startValue + chartGraphData.proportion;

            chartGraphData.color = ResourcesCompat.getColor(getResources(), chartData.getColorResId(), null);
            chartGraphs.add(chartGraphData);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, outerRad, fillPaint);
        canvas.drawCircle(centerX, centerY, innerRad, whitePaint);
        canvas.drawPoint(centerX, centerY, fillPaint);
        for (ChartGraphData chartGraph : chartGraphs) {
            if (chartGraph.proportion > 5) {
                linePaint.setColor(chartGraph.color);
                int startAnglePlus = chartGraph.startValue + LINES_MARGIN;
                if (chartGraph.chartData.isFull()) {
                    fullLinePaint.setColor(chartGraph.color);
                    canvas.drawArc(oval, startAnglePlus, chartGraph.proportion - LINES_MARGIN, false, fullLinePaint);
                } else {
                    endsPaint.setColor(chartGraph.color);
                    int endPointPosition = FULL_ANGLE - chartGraph.startValue - chartGraph.proportion;
                    canvas.drawArc(oval, startAnglePlus, chartGraph.proportion - LINES_MARGIN, false, linePaint);
                    canvas.drawCircle(getXByAngle(FULL_ANGLE - startAnglePlus),
                            getYByAngle(FULL_ANGLE - startAnglePlus), STROKE_WIDTH / 2f, endsPaint);
                    canvas.drawCircle(getXByAngle(endPointPosition), getYByAngle(endPointPosition),
                            STROKE_WIDTH / 2f, endsPaint);
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldWidth);
        int viewDiam = Math.min(width, height);
        outerRad = viewDiam / 2;
        innerRad = outerRad - CIRCLE_WIDTH;
        centerX = width / 2;
        centerY = height / 2;
        oval = new RectF();
        int centerRadius = getCenterRadius();
        oval.set(centerX - centerRadius, centerY - centerRadius, centerX + centerRadius, centerY + centerRadius);
    }

    private float getXByAngle(int angle) {
        return (float) (centerX + (getCenterRadius() * Math.sin(Math.toRadians(angle))));
    }

    private float getYByAngle(int angle) {
        return (float) (centerY + (getCenterRadius() * Math.cos(Math.toRadians(angle))));
    }

    private void init() {
        chartGraphs = new ArrayList<>();

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.chart_grey, null));

        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setColor(Color.WHITE);

        fullLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fullLinePaint.setStyle(Paint.Style.STROKE);
        fullLinePaint.setStrokeWidth(CIRCLE_WIDTH);

        endsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        endsPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(STROKE_WIDTH);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    private int getCenterRadius() {
        return Math.round(innerRad + (outerRad - innerRad) / 2f);
    }

    private class ChartGraphData {
        ChartData chartData;
        int proportion;
        int startValue;
        int color;

        public ChartGraphData(ChartData chartData) {
            this.chartData = chartData;
        }
    }
}
