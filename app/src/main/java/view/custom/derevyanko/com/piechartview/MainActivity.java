package view.custom.derevyanko.com.piechartview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ChartView chartView = (ChartView) findViewById(R.id.chartView);
        ArrayList<ChartData> charts = new ArrayList<>();
        charts.add(new ChartData(34, R.color.first));
        charts.add(new ChartData(18, R.color.second, true));
        charts.add(new ChartData(34, R.color.third));
        charts.add(new ChartData(6, R.color.fourth));
        charts.add(new ChartData(78, R.color.fifth));
        chartView.applyData(charts);

    }
}
