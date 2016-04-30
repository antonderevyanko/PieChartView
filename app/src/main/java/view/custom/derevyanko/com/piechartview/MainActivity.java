package view.custom.derevyanko.com.piechartview;

import android.os.Bundle;
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
        final ChartView chartView = (ChartView) findViewById(R.id.chartView);
        final ArrayList<ChartData> charts = new ArrayList<>();
        charts.add(new ChartData(34, R.color.first));
        charts.add(new ChartData(18, R.color.second, true));
        charts.add(new ChartData(34, R.color.third));
        charts.add(new ChartData(6, R.color.fourth));
        charts.add(new ChartData(78, R.color.fifth));
        chartView.applyData(charts);
        chartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<ChartData> charts = new ArrayList<>();
                charts.add(new ChartData(getRandomInt(), R.color.first, getRandomBool()));
                charts.add(new ChartData(getRandomInt(), R.color.second, getRandomBool()));
                charts.add(new ChartData(getRandomInt(), R.color.third, getRandomBool()));
                charts.add(new ChartData(getRandomInt(), R.color.fourth, getRandomBool()));
                charts.add(new ChartData(getRandomInt(), R.color.fifth, getRandomBool()));
                chartView.applyData(charts);
            }
        });
    }

    private int getRandomInt() {
        return random.nextInt(100);
    }

    private boolean getRandomBool() {
        return random.nextBoolean();
    }
}
