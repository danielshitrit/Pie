package com.example.piechartlibrarymodule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

public class PieChartView extends View {
    private Paint paint;
    private Paint strokePaint;
    private float[] data;
    private int[] colors;
    private String[] labels;
    private int chartBackgroundColor;
    private RectF pieBounds;
    private int selectedSegment = -1;

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.WHITE);
        strokePaint.setStrokeWidth(5);

        data = new float[]{};
        colors = new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};
        labels = new String[]{};
        chartBackgroundColor = Color.LTGRAY;  // Default chart background color
        pieBounds = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Set up the pie chart area
        float chartDiameter = Math.min(getWidth(), getHeight()) * 0.6f;
        float chartLeft = (getWidth() - chartDiameter) / 2;
        float chartTop = (getHeight() - chartDiameter) / 2;

        pieBounds.set(chartLeft, chartTop, chartLeft + chartDiameter, chartTop + chartDiameter);

        // Draw the chart background color
        paint.setColor(chartBackgroundColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        // Calculate the sum of all values
        float totalValue = getTotalValue();

        float startAngle = 0;
        for (int i = 0; i < data.length; i++) {
            paint.setColor(colors[i % colors.length]);
            float sweepAngle = (data[i] / totalValue) * 360;

            // Adjust the segment position if it is selected
            if (i == selectedSegment) {
                float shiftX = 20 * (float) Math.cos(Math.toRadians(startAngle + sweepAngle / 2));
                float shiftY = 20 * (float) Math.sin(Math.toRadians(startAngle + sweepAngle / 2));
                pieBounds.offset(shiftX, shiftY);
            }

            // Draw the pie segment
            canvas.drawArc(pieBounds, startAngle, sweepAngle, true, paint);
            canvas.drawArc(pieBounds, startAngle, sweepAngle, true, strokePaint);

            // Draw the percentage label inside the segment
            float angle = (startAngle + sweepAngle / 2) * (float) Math.PI / 180;
            float labelX = pieBounds.centerX() + (float) (chartDiameter / 3 * Math.cos(angle));
            float labelY = pieBounds.centerY() + (float) (chartDiameter / 3 * Math.sin(angle));
            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
            String percentage = String.format("%.1f%%", (data[i] / totalValue) * 100);
            canvas.drawText(percentage, labelX, labelY, paint);

            startAngle += sweepAngle;

            // Reset the offset after drawing the selected segment
            if (i == selectedSegment) {
                pieBounds.offset(-pieBounds.left, -pieBounds.top);
                pieBounds.set(chartLeft, chartTop, chartLeft + chartDiameter, chartTop + chartDiameter);
            }
        }

        // Draw the legend
        drawLegend(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            float centerX = pieBounds.centerX();
            float centerY = pieBounds.centerY();
            float dx = x - centerX;
            float dy = y - centerY;
            float distanceFromCenter = (float) Math.sqrt(dx * dx + dy * dy);

            // Check if the touch is inside the pie chart
            if (distanceFromCenter <= pieBounds.width() / 2) {
                float totalValue = getTotalValue();
                float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
                angle = angle < 0 ? 360 + angle : angle;

                float currentAngle = 0;
                for (int i = 0; i < data.length; i++) {
                    float sweepAngle = (data[i] / totalValue) * 360;
                    if (angle >= currentAngle && angle < currentAngle + sweepAngle) {
                        selectedSegment = i;
                        showBarValueDialog(labels[i], data[i], (data[i] / totalValue) * 100);
                        invalidate();  // Redraw to highlight the selected segment
                        return true;
                    }
                    currentAngle += sweepAngle;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void showBarValueDialog(String label, float value, float percentage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(label)
                .setMessage("Value: " + value + "\nPercent Value: " + String.format("%.1f%%", percentage))
                .setPositiveButton("OK", null)
                .show();
    }

    private void drawLegend(Canvas canvas) {
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.FILL);
        float legendX = pieBounds.left;
        float legendY = pieBounds.bottom + 50;

        for (int i = 0; i < labels.length; i++) {
            paint.setColor(colors[i % colors.length]);
            canvas.drawRect(legendX, legendY - 30, legendX + 30, legendY, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(labels[i], legendX + 40, legendY, paint);
            legendY += 50;
        }
    }

    public void setData(float[] data, String[] labels) {
        this.data = data;
        this.labels = labels;
        invalidate();
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public void setChartBackgroundColor(int color) {
        this.chartBackgroundColor = color;
        invalidate();
    }

    private float getTotalValue() {
        float total = 0;
        for (float value : data) {
            total += value;
        }
        return total;
    }
}
