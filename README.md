# PieChartView Library

Custom view for Android that displays a pie chart. This library allows you to create a pie chart with customizable colors, segment data, and labels. It also includes interactive features such as selecting segments and displaying detailed information in a dialog.

<img width="197" alt="Screenshot 2024-08-16 at 0 08 11" src="https://github.com/user-attachments/assets/6fc7f2af-37ef-46b3-bdfa-3197f2cf02cf"> <img width="197" alt="Screenshot 2024-08-16 at 0 08 23" src="https://github.com/user-attachments/assets/b7d245bd-048d-48e8-b4d5-b7f38f0f301d">

## Features

- **Customizable Colors**: Easily set colors for the pie chart segments.
- **Interactive Segments**: Click on a segment to view detailed information.
- **Legend Display**: Automatically generates a legend for the pie chart.
- **Dynamic Data**: Update the chart data and labels dynamically.

## Installation

 **Add the Library to Your Project**

 Update Your `build.gradle` and `settings.gradle` Files:

   - In your **settings.gradle** file, ensure that the `piechartlibrarymodule` is included:

     ```java
     dependencies {
         implementation(project(":piechartlibrarymodule"))
     }
     ```

   - In your **build.gradle** file, add the repository configuration:

     ```java
     dependencyResolutionManagement {
         repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
         repositories {
             google()
             mavenCentral()
         }
     }
     ```
     
## Usage

### Initialize the View

In your `Activity` or `Fragment`, get a reference to the `PieChartView` and set its data:

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the PieChartView by its ID
        PieChartView pieChartView = findViewById(R.id.pieChartView);

        // Define your data and labels
        float[] data = {100, 150, 250, 200, 50};
        String[] labels = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

        // Set data and labels to the PieChartView
        pieChartView.setData(data, labels);

        // Define your custom colors
        int[] pastelColors = {
                Color.parseColor("#ADD8E6"),  // Light Blue
                Color.parseColor("#FFFACD"),  // Lemon Chiffon
                Color.parseColor("#FFB6C1"),  // Light Pink
                Color.parseColor("#D8BFD8"),  // Thistle
                Color.parseColor("#BAFFC9"),  // Light Green
        };

        // Set the custom colors to the pie chart
        pieChartView.setColors(pastelColors);

        // Optionally, set the chart background color
        pieChartView.setChartBackgroundColor(Color.WHITE);
    }
}
```
## Video

![3E3F2DC5-E3B2-42B7-A3BF-0212E14AFB5F_1_102_o](https://github.com/user-attachments/assets/c4fff20c-d212-4b91-af12-4f7a8363936a)

