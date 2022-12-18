package com.example.goodhouse;

import static java.lang.Thread.sleep;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ViewMoreActivity extends AppCompatActivity {
    private Chart chart;
    private int[] dbArr;
    private Button aDayBtn, aWeekBtn, aMonthBtn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_more);
        chart = findViewById(R.id.linechart);
        dbArr = new int[30];
        aDayBtn = findViewById(R.id.oneDayBtn);
        aWeekBtn = findViewById(R.id.weekBtn);
        aMonthBtn = findViewById(R.id.amonthBtn);
        makeChart(24);

        aDayBtn.setOnClickListener(v -> makeChart(24));
        aWeekBtn.setOnClickListener(v -> makeChart(7));
        aMonthBtn.setOnClickListener(v -> makeChart(30));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        ComplaintAdapter adapter = new ComplaintAdapter();

        adapter.setList(getList());
        recyclerView.setAdapter(adapter);
    }

    static ArrayList<Complaint> getList(){
        ArrayList<Complaint> list = new ArrayList<>();
        for(int i=0;i<MainPageActivity.list2.length;i++) {
            list.add(new Complaint(MainPageActivity.list2[i][2],MainPageActivity.list2[i][3],Integer.parseInt(MainPageActivity.list2[i][1]),Integer.parseInt(MainPageActivity.list2[i][0])));
        }
        for(int i=0;i<MainPageActivity.list.length;i++) {
            list.add(new Complaint(MainPageActivity.list[i][2],MainPageActivity.list[i][3],Integer.parseInt(MainPageActivity.list[i][1]),Integer.parseInt(MainPageActivity.list[i][0])));
        }
        return list;
    }

    void makeChart(int n) {
        ArrayList<Entry> values = getVal(n);
        //여기 구현 필요
        LineDataSet set1;
        set1 = new LineDataSet(values, "dB");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points
        set1.setDrawCircleHole(false);
        set1.setDrawCircles(false);
        set1.setColor(Color.BLACK);
        //set1.setCircleColor(Color.BLACK);
        set1.setDrawFilled(true); // 차트 아래 fill(채우기) 설정
        set1.setFillColor(Color.BLACK); // 차트 아래 채우기 색 설정

        // set data
        chart.setData(data);
        chart.invalidate();
    }

    ArrayList<Entry> getVal(int n) { //통신 미구현으로 인한 미구현
        ArrayList<Entry> values = new ArrayList<>();
        getDB(n);
        for (int i = 0; i <n; i++) {
            float val = (float) dbArr[i];
            values.add(new Entry(i, val));
        }
        return values;
    }

    void getDB(int n) {
        if(n == 24) {
            for(int i=0;i<24;i++) {
                dbArr[i] = Integer.parseInt(Firebase.noise.get(Integer.toString(i+1)).toString());
            }
        }
        else if(n == 7) {
            for(int i=0;i<7;i++) {
                dbArr[i] = Integer.parseInt(Firebase.week_noise.get(Integer.toString(i+1)).toString());
            }
        }
        else {
            for(int i=0;i<30;i++) {
                dbArr[i] = Integer.parseInt(Firebase.month_noise.get(Integer.toString(i+1)).toString());
            }
        }
    }
}
