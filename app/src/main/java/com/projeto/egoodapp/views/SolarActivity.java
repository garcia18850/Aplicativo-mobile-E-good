package com.projeto.egoodapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import com.projeto.egoodapp.R;

public class SolarActivity extends AppCompatActivity {

    private SeekBar sliderSystemPower;
    private SeekBar sliderLightCost;
    private TextView tvSystemPowerValue;
    private TextView tvLightCostValue;
    private TextView tvMonthlyGenerationTop;
    private TextView tvCO2Top;
    private TextView tvPaybackTop;
    private TextView tvMonthlyGeneration;
    private TextView tvMonthlySavings;
    private TextView tvInvestment;
    private TextView tvPayback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar);

        initializeViews();
        setupSliders();
        setupNavigation();
        updateCalculations();
    }

    private void initializeViews() {
        sliderSystemPower = findViewById(R.id.sliderSystemPower);
        sliderLightCost = findViewById(R.id.sliderLightCost);
        tvSystemPowerValue = findViewById(R.id.tvSystemPowerValue);
        tvLightCostValue = findViewById(R.id.tvLightCostValue);
        tvMonthlyGenerationTop = findViewById(R.id.tvMonthlyGenerationTop);
        tvCO2Top = findViewById(R.id.tvCO2Top);
        tvPaybackTop = findViewById(R.id.tvPaybackTop);
        tvMonthlyGeneration = findViewById(R.id.tvMonthlyGeneration);
        tvMonthlySavings = findViewById(R.id.tvMonthlySavings);
        tvInvestment = findViewById(R.id.tvInvestment);
        tvPayback = findViewById(R.id.tvPayback);
    }

    private void setupSliders() {
        sliderSystemPower.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double value = progress / 10.0;
                tvSystemPowerValue.setText(String.format("%.1f kWp", value));
                updateCalculations();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sliderLightCost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = progress;
                DecimalFormat df = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("pt", "BR")));
                tvLightCostValue.setText(String.format("R$ %s", df.format(value)));
                updateCalculations();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setupNavigation() {
        LinearLayout btnHome = findViewById(R.id.btnHome);
        LinearLayout btnSolar = findViewById(R.id.btnSolar);
        LinearLayout btnProfile = findViewById(R.id.btnProfile);
        LinearLayout btnComparison = findViewById(R.id.btnComparison);
        LinearLayout btnVehicles = findViewById(R.id.btnVehicles);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(SolarActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        btnSolar.setOnClickListener(v -> {
            // Already on Solar
        });

        btnVehicles.setOnClickListener(v -> {
            Intent intent = new Intent(SolarActivity.this, VehiclesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(SolarActivity.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        btnComparison.setOnClickListener(v -> {
            Intent intent = new Intent(SolarActivity.this, ComparisonActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    private void updateCalculations() {
        double systemPower = sliderSystemPower.getProgress() / 10.0;
        int lightCostDaily = sliderLightCost.getProgress();

        // Formula: Monthly Generation = System Power (kWp) × 5.4 (average daily sun hours in Brazil) × 30 days
        double monthlyGeneration = systemPower * 5.4 * 30;

        // Formula: Monthly Savings = Daily Light Cost × 30
        double monthlySavings = lightCostDaily * 30;

        // Formula: Annual CO2 avoided = Monthly Generation × 12 × 0.084 (kg CO2 per kWh)
        double annualCO2 = monthlyGeneration * 12 * 0.084;

        // Formula: System Cost = System Power × R$ 4000/kWp
        double systemCost = systemPower * 4000;

        // Formula: Payback = System Cost / (Monthly Savings × 12)
        double paybackYears = systemCost / (monthlySavings * 12);

        // Format values
        DecimalFormat dfPrice = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        DecimalFormat dfInt = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("pt", "BR")));

        // Update top stats
        tvMonthlyGenerationTop.setText(String.format("%.0f kWh", monthlyGeneration));
        tvCO2Top.setText(String.format("%.0f kg", annualCO2));
        tvPaybackTop.setText(String.format("%.1f anos", paybackYears));

        // Update results section
        tvMonthlyGeneration.setText(String.format("%.0f kWh", monthlyGeneration));
        tvMonthlySavings.setText(String.format("R$ %s", dfPrice.format(monthlySavings)));
        tvInvestment.setText(String.format("R$ %s", dfInt.format((long) systemCost)));
        tvPayback.setText(String.format("%.1f anos", paybackYears));
    }
}
