package view.custom.derevyanko.com.piechartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import view.custom.derevyanko.com.piechartview.dynamic.DynamicSpring;
import view.custom.derevyanko.com.piechartview.entity.ChartData;
import view.custom.derevyanko.com.piechartview.entity.DrawGraphData;

import java.util.ArrayList;
import java.util.List;

public final class ChartGraphView extends View {

    private static final int LINES_MARGIN = 10;
    private static final int STROKE_WIDTH = 18;
    private static final int FULL_ANGLE = 90;
    private static final int CIRCLE_WIDTH = 60;
    private static final int MIN_GRAPH_LENGTH = 5;

    private static final int DELAY_MILLIS = 20;

    private int outerRad;
    private int innerRad;
    private int centerX, centerY;

    List<DrawGraphData> drawGraphData;

    private RectF oval;

    private Paint fullLinePaint;
    private Paint endsPaint;
    private Paint linePaint;
    private Paint fillPaint;
    private Paint whitePaint;

    private boolean useAnimation = true;

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            boolean needNewFrame = false;
            long now = AnimationUtils.currentAnimationTimeMillis();
            for (DrawGraphData dynamics : drawGraphData) {
                dynamics.update(now);
                if (!dynamics.isFinished()) {
                    needNewFrame = true;
                }
            }
            if (needNewFrame) {
                postDelayed(this, DELAY_MILLIS);
            }
            invalidate();
        }
    };

    public ChartGraphView(Context context) {
        super(context);
        init();
    }

    public ChartGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setUseAnimation(boolean useAnimation) {
        this.useAnimation = useAnimation;
    }

    public void applyData(List<ChartData> charts) {
        drawGraphData = createGraphData(charts);
        if (useAnimation) {
            for (DrawGraphData drawingData : drawGraphData) {
                drawingData.addDynamic(new DynamicSpring(drawingData.getProportion()));
            }
            removeCallbacks(animator);
            post(animator);
        }
        invalidate();
    }

    private List<DrawGraphData> createGraphData(List<ChartData> charts) {
        List<DrawGraphData> chartGraphs = new ArrayList<>();
        int sum = 0;
        for (ChartData chart : charts) {
            sum = sum + chart.getCount();
        }
        int prevPosition = 0;
        for (ChartData chartData : charts) {
            DrawGraphData drawGraphData = new DrawGraphData(chartData);
            drawGraphData.setProportion(Math.round(chartData.getCount() * 360 / sum));
            // we wan't draw chart which is less then MIN_GRAPH_LENGTH
            if (drawGraphData.getProportion() > MIN_GRAPH_LENGTH) {
                drawGraphData.setStartValue(prevPosition);
                prevPosition = drawGraphData.getStartValue() + drawGraphData.getProportion();
                drawGraphData.setColor(ResourcesCompat.getColor(getResources(), chartData.getColorResId(), null));
                chartGraphs.add(drawGraphData);
            }
        }
        return chartGraphs;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, outerRad, fillPaint);
        canvas.drawCircle(centerX, centerY, innerRad, whitePaint);
        for (DrawGraphData drawingData : drawGraphData) {
            linePaint.setColor(drawingData.getColor());
            int startAnglePlus = drawingData.getStartValue() + LINES_MARGIN;
            if (drawingData.getChartData().isFull()) {
                fullLinePaint.setColor(drawingData.getColor());
                canvas.drawArc(oval, startAnglePlus, drawingData.getValue() - LINES_MARGIN, false, fullLinePaint);
            } else {
                endsPaint.setColor(drawingData.getColor());
                int endPointPosition = FULL_ANGLE - drawingData.getStartValue() - drawingData.getValue();
                canvas.drawArc(oval, startAnglePlus, drawingData.getValue() - LINES_MARGIN, false, linePaint);
                canvas.drawCircle(getXByAngle(FULL_ANGLE - startAnglePlus),
                        getYByAngle(FULL_ANGLE - startAnglePlus), STROKE_WIDTH / 2f, endsPaint);
                canvas.drawCircle(getXByAngle(endPointPosition), getYByAngle(endPointPosition),
                        STROKE_WIDTH / 2f, endsPaint);
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
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.chart_grey, null));

        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setColor(Color.WHITE);

        fullLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fullLinePaint.setStyle(Paint.Style.STROKE);
        fullLinePaint.setStrokeWidth(CIRCLE_WIDTH - 1);

        endsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        endsPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(STROKE_WIDTH);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    private int getCenterRadius() {
        return Math.round(innerRad + (outerRad - innerRad) / 2f);
    }
}
