package com.example.piechartlibrary;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.piechartlibrarymodule.PieChartView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        PieChartView pieChartView = findViewById(R.id.bar_chart_view);

        // Set data for the chart
        pieChartView.setData(new float[]{100, 150, 250, 200, 50},
                new String[]{"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"});

        // Set chart background color
        pieChartView.setChartBackgroundColor(getResources().getColor(R.color.white));  // Or any color you prefer

         // Define my custom colors
        int[] pastelColors = {
                Color.parseColor("#ADD8E6"),  // Light Blue
                Color.parseColor("#FFFACD"),  // Lemon Chiffon
                Color.parseColor("#FFB6C1"),  // Light Pink
                Color.parseColor("#D8BFD8"),  // Thistle
                Color.parseColor("#BAFFC9"),  // Light Green
        };

        // Set the light colors to the pie chart
        pieChartView.setColors(pastelColors);
    }
}
