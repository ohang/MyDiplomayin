package com.example.goro.mydiplomayin;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;

import static com.example.goro.mydiplomayin.Thread_CSS.CssSize;
import static com.example.goro.mydiplomayin.Thread_IMG.IMGSize;
import static com.example.goro.mydiplomayin.Thread_JS.JsSize;

public class MainActivity extends Activity {
    PieChart pieChart;


    public   int  pagesize;     /** Called when the activity is first created. */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                final TextView t2 =(TextView)findViewById(R.id.textView2);
                final TextView t3 =(TextView)findViewById(R.id.textView3);
                final TextView t4 =(TextView)findViewById(R.id.textView4);
                final TextView t5 =(TextView)findViewById(R.id.textView5);
                final TextView t6 =(TextView)findViewById(R.id.textView6);

            final EditText editText = (EditText)findViewById(R.id.edit);

            Intent intent = getIntent();
                 final String stringData= intent.getStringExtra("key");





            final Button button = findViewById(R.id.but);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button

                    //String web= editText.getText().toString();
                    String web=stringData;
                    Thread t = Thread.currentThread();

                    Thread_JS js = new Thread_JS(web);
                    Thread_CSS css = new Thread_CSS(web);
                    Thread_IMG img = new Thread_IMG(web);
                    int Start = (int) System.currentTimeMillis();
                    img.start();
                    css.start();
                    js.start();

                    try {
                        img.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        css.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        js.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int Final = (int) System.currentTimeMillis();


                    pagesize= (int) (CssSize+JsSize+IMGSize);

                    System.out.println(Final-Start+ " ms");

                    t2.setText(CssSize+"  kb css ");

                    t3.setText(JsSize+"  kb js");

                    t4.setText(IMGSize+"  kb  img");

                    t5.setText(Final-Start+"  Load time");


                    t6.setText((pagesize/1024)+"kb  Page size ");



                    pieChart=(PieChart)findViewById(R.id.piechart);
                    pieChart.setUsePercentValues(true);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setExtraOffsets(5,10,5,5);

                    pieChart.setDragDecelerationFrictionCoef(0.99f);

                    pieChart.setDrawHoleEnabled(false); // yete true uremn mech datarka
                    pieChart.setHoleColor(Color.WHITE);
                    pieChart.setTransparentCircleRadius(61f);


                    ArrayList<PieEntry> yvals = new ArrayList<>();

                    yvals.add(new PieEntry((int)IMGSize,"images"));
                    yvals.add(new PieEntry(160000,"html"));
                    yvals.add(new PieEntry((int)CssSize,"css"));
                    yvals.add(new PieEntry((int)JsSize,"js"));
                    //  yvals.add(new PieEntry(40,"other"));

                    Description description = new Description();
                    description.setText("website Content");
                    description.setTextSize(12f);
                    pieChart.setDescription(description);

                    pieChart.animateY(500, Easing.EasingOption.EaseInCubic); // bacelu animacia


                    PieDataSet set =new  PieDataSet(yvals,"Content Type");
                    set.setSliceSpace(3f);// aranqneri hastutyun
                    set.setSelectionShift(5f);
                    set.setColors(ColorTemplate.JOYFUL_COLORS);

                    PieData data = new PieData(set);
                    data.setValueTextSize(20f);
                    data.setValueTextColor(Color.YELLOW);

                    pieChart.setData(data);
                    IMGSize=0;
                    JsSize=0;
                    CssSize=0;


                }
            });







        }
}



