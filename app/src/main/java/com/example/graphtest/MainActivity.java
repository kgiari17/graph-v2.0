package com.example.graphtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;

import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.enums.Anchor;
import com.anychart.graphics.vector.text.HAlign;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Random r;
    private int x1, gsr, skt, hr, hrv, btDuration;
    private static List<Integer> nums, numsTotal;
    private Button btn, backBtn, btBtn;
    private static boolean isRunning, isBtOn;
    private AnyChartView anyChartView;
    private CircularGauge circularGauge;
    private double currentValue;
    private TextView gsrText, sktText, hrText, hrvText;
    private Context context;
    private CharSequence btText;
    private Toast btToast;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isBtOn = false;
        r = new Random();
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

        context = getApplicationContext();
        btText = "Bluetooth is not connected!";
        btDuration = Toast.LENGTH_SHORT;
        btToast = Toast.makeText(context, btText, btDuration);
        btToast.setGravity(Gravity.CENTER, 0, 0);
        setNums();

        anyChartView = findViewById(R.id.any_chart_view);
        //anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        circularGauge = AnyChart.circular();
        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0, 0, 0, 0)
                .margin(30, 30, 30, 30);
        circularGauge.startAngle(0)
                .sweepAngle(360);

        currentValue = 20.3D;
        circularGauge.data(new SingleValueDataSet(new Double[] { currentValue }));

        circularGauge.axis(0)
                .startAngle(-150)
                .radius(80)
                .sweepAngle(300)
                .width(3)
                .ticks("{ type: 'line', length: 4, position: 'outside' }");

        circularGauge.axis(0).labels().position("outside");

        //scale
        circularGauge.axis(0).scale()
                .minimum(0)
                .maximum(100);

        circularGauge.axis(0).scale()
                .ticks("{interval: 10}")
                .minorTicks("{interval: 10}");

        circularGauge.needle(0)
                .stroke(null)
                .startRadius("6%")
                .endRadius("38%")
                .startWidth("2%")
                .endWidth(0);

        circularGauge.cap()
                .radius("4%")
                .enabled(true)
                .stroke(null);

        circularGauge.label(0)
                .text("<span style=\"font-size: 25\">Anxiety Level</span>") //Label in the center
                .useHtml(true)
                .hAlign(HAlign.CENTER);
        circularGauge.label(0)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .padding(15, 20, 0, 0);

        circularGauge.label(1)
                .text("<span style=\"font-size: 20\">" + currentValue + "</span>") //currentValue should be Anxiety Level
                .useHtml(true)
                .hAlign(HAlign.CENTER);
        circularGauge.label(1)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(-100)
                .padding(5, 10, 0, 0)
                .background("{fill: 'none', stroke: '#c1c1c1', corners: 3, cornerType: 'ROUND'}");

        circularGauge.range(0,
                "{\n" +
                        "    from: 0,\n" +
                        "    to: 40,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'green 0.5',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        circularGauge.range(1,
                "{\n" +
                        "    from: 40,\n" +
                        "    to: 75,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'yellow 0.5',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        circularGauge.range(2,
                "{\n" +
                        "    from: 75,\n" +
                        "    to: 100,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'red 0.5',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        anyChartView.setChart(circularGauge);
    }

    public void setNums(){
        for(int i=0;i<=10;i++){
            nums.add(0);
        }
    }

    public void start(View view) {

        if (!isRunning && isBtOn) {
            isRunning = true;
            btn.setText("Stop");
            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!isRunning) {
                        t.cancel();
                    }
                    x1 = r.nextInt(100);
                    gsr = r.nextInt(100);
                    skt = r.nextInt(100);
                    hr = r.nextInt(100);
                    hrv = r.nextInt(100);

                    nums.add(x1);
                    numsTotal.add(x1);

                    if (nums.size() > 10) {
                        nums.remove(0);
                    }
                    currentValue = x1;

                    circularGauge.label(1)
                            .text("<span style=\"font-size: 20\">" + currentValue + "</span>") //currentValue should be Anxiety Level
                            .useHtml(true)
                            .hAlign(HAlign.CENTER);

                    circularGauge.data(new SingleValueDataSet(new Double[]{currentValue}));

                    gsrText.setText("GSR : " + gsr);
                    sktText.setText("SKT : " + skt);
                    hrText.setText("HR : " + hr);
                    hrvText.setText("HRV : " + hrv);


                }
            }, 0, 700);
        }else if(!isRunning && !isBtOn){
            btToast.show();
        }else{
            isRunning = false;
            btn.setText("Start");
            startActivity(new Intent(MainActivity.this, ResultsActivity.class));
        }
    }

    public void btCheck(View view){

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

    public static List<Integer> getTotalScore(){
        return numsTotal;
    }
}
