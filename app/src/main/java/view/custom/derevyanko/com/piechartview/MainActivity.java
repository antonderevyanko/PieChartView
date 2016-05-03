package view.custom.derevyanko.com.piechartview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import view.custom.derevyanko.com.piechartview.entity.ChartData;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random();
        setContentView(R.layout.main);
        // static example
        ChartGraphView staticChartView = (ChartGraphView) findViewById(R.id.staticView);
        staticChartView.setUseAnimation(false);
        staticChartView.applyData(getRandomCharts());
        staticChartView.setOnClickListener(onClickListener);
        // dynamic example
        ChartGraphView dynamicChartView = (ChartGraphView) findViewById(R.id.dynamicView);
        dynamicChartView.setUseAnimation(true); // true by default, by the way
        dynamicChartView.applyData(getRandomCharts());
        dynamicChartView.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final ArrayList<ChartData> charts = getRandomCharts();
            ((ChartGraphView) view).applyData(charts);
        }
    };

    @NonNull
    private ArrayList<ChartData> getRandomCharts() {
        final ArrayList<ChartData> charts = new ArrayList<>();
        charts.add(new ChartData(getRandomInt(), R.color.first, getRandomBool()));
        charts.add(new ChartData(getRandomInt(), R.color.second, getRandomBool()));
        charts.add(new ChartData(getRandomInt(), R.color.third, getRandomBool()));
        charts.add(new ChartData(getRandomInt(), R.color.fourth, getRandomBool()));
        charts.add(new ChartData(getRandomInt(), R.color.fifth, getRandomBool()));
        return charts;
    }

    private int getRandomInt() {
        return random.nextInt(100);
    }

    private boolean getRandomBool() {
        return random.nextBoolean();
    }
}
