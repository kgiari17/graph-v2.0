package com.example.graphtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends AppCompatActivity {

    private int x1, gsr, skt, hr, hrv, btDuration;
    private List<Integer> nums, numsTotal;
    private Button btn, backBtn, btBtn;
    private boolean isRunning, isBtOn;
    private AnyChartView anyChartView;
    private TextView gsrText, sktText, hrText, hrvText;
    private Context context;
    private CharSequence btText;
    private Toast btToast;

    private MainActivity main;
    private Cartesian cartesian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        isBtOn = false;
        nums = new ArrayList<Integer>();
        numsTotal = new ArrayList<Integer>();
        isRunning = false;
        btn = (Button) findViewById(R.id.startBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        btBtn = (Button) findViewById(R.id.btBtn);
        gsrText = (TextView) findViewById(R.id.gsrTextView);
        sktText = (TextView) findViewById(R.id.sktTextView);
        hrText = (TextView) findViewById(R.id.hrTextView);
        hrvText = (TextView) findViewById(R.id.hrvTextView);

        // set no bluetooth toast
        context = getApplicationContext();
        btText = "Bluetooth is not connected!";
        btDuration = Toast.LENGTH_SHORT;
        btToast = Toast.makeText(context, btText, btDuration);
        btToast.setGravity(Gravity.CENTER, 0, 0);

        main = new MainActivity();
        setGraph();

    }

    //NEW

    public void setGraph(){

        anyChartView = findViewById(R.id.any_chart_view);

        cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(5d, 5d, 5d, 5d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Anxiety Level of the session");

        cartesian.yAxis(0).title("Anxiety Level");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();

        for(int i=0; i<main.getTotalScore().size();i++) {
            seriesData.add(new CustomDataEntry("time", main.getTotalScore().get(i)));
            //System.out.println("getScore: "+main.getTotalScore().get(i));
        }

//        Set<String> set = new HashSet<String>();


          Set set = Set.instantiate();
          set.instantiate();
          set.data(seriesData);
          Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
//        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
//        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("No binaural");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

//        Line series2 = cartesian.line(series2Mapping);
//        series2.name("Whiskey");
//        series2.hovered().markers().enabled(true);
//        series2.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series2.tooltip()
//                .position("right")
//                .anchor(Anchor.LEFT_CENTER)
//                .offsetX(5d)
//                .offsetY(5d);
//
//        Line series3 = cartesian.line(series3Mapping);
//        series3.name("Tequila");
//        series3.hovered().markers().enabled(true);
//        series3.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series3.tooltip()
//                .position("right")
//                .anchor(Anchor.LEFT_CENTER)
//                .offsetX(5d)
//                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(5d, 5d, 10d, 5d);

        anyChartView.setChart(cartesian);
    }


    public void goBack(View view) {
        finish();
    }

    public void btCheck(View view) {
        if(isBtOn){
            btBtn.setBackgroundResource(R.drawable.bluetooth_icon_off);
            isBtOn =false;
            isRunning = false;
            btn.setText("Start");
        }else{
            btBtn.setBackgroundResource(R.drawable.bluetooth_icon);
            isBtOn =true;
        }
    }

    public class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value) {
            super(x, value);
        }

    }
    }

