package com.projeto.egoodapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import com.projeto.egoodapp.R;

public class ComparisonActivity extends AppCompatActivity {

    private Button tabComparacao;
    private Button tabCalculadora;
    private FrameLayout containerComparacao;
    private FrameLayout containerCalculadora;
    private Spinner spEvCalculadora;
    private Spinner spCombCalculadora;
    private Spinner spEvComparacao;
    private Spinner spCombComparacao;
    private SeekBar sliderEnergyCost;
    private SeekBar sliderGasCost;
    private SeekBar sliderMonthlyKm;
    private TextView tvEnergyCostValue;
    private TextView tvGasCostValue;
    private TextView tvMonthlyKmValue;
    private TextView tvSelectedEvCalculadora;
    private TextView tvSelectedCombCalculadora;
    private TextView tvSelectedEvComparacao;
    private TextView tvSelectedCombComparacao;
    private TextView tvAnnualSavings;
    private TextView tvMonthlySavings;
    private TextView tvConsumEvCalc;
    private TextView tvConsumCombCalc;
    private TextView tvPriceEvCalc;
    private TextView tvPriceCombCalc;
    private TextView tvCostEvCalc;
    private TextView tvCostCombCalc;
    private TextView tvConsumEvComparacao;
    private TextView tvConsumCombComparacao;
    private TextView tvPriceEvComparacao;
    private TextView tvPriceCombComparacao;
    private TextView tvCostEvComparacao;
    private TextView tvCostCombComparacao;
    private TextView tvAnnualSavingsCalc;

    private static final String[] ELECTRIC_VEHICLES = {
        "BYD Dolphin Mini — 12,9 kWh/100km",
        "BYD Dolphin — 13,4 kWh/100km",
        "Volvo EX30 — 15,1 kWh/100km",
        "Mercedes EQE SUV — 18,9 kWh/100km",
        "Fiat 500e — 14,2 kWh/100km",
        "Chevrolet Bolt EV — 15,6 kWh/100km"
    };

    private static final double[] EV_CONSUMPTION = { 12.9, 13.4, 15.1, 18.9, 14.2, 15.6 };
    private static final int[] EV_PRICES = { 119800, 149800, 285000, 729900, 189990, 199990 };

    private static final String[] COMBUSTION_VEHICLES = {
        "Chevrolet Onix 1.0 Turbo — 12,5 L/100km",
        "Renault Sandero 1.0 — 12,3 L/100km",
        "VW Polo 1.0 — 11,8 L/100km",
        "Toyota Corolla 2.0 — 10,2 L/100km",
        "Honda HR-V 1.5 Turbo — 13,1 L/100km"
    };

    private static final double[] COMBUSTION_CONSUMPTION = { 12.5, 12.3, 11.8, 10.2, 13.1 };
    private static final int[] COMBUSTION_PRICES = { 89990, 79990, 105990, 145990, 159990 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        initializeViews();
        setupSpinners();
        setupSliders();
        setupTabListeners();
        setupNavigation();
        updateCalculator();
    }

    private void initializeViews() {
        tabComparacao = findViewById(R.id.tabComparacao);
        tabCalculadora = findViewById(R.id.tabCalculadora);
        containerComparacao = findViewById(R.id.containerComparacao);
        containerCalculadora = findViewById(R.id.containerCalculadora);
        spEvCalculadora = findViewById(R.id.spEvCalculadora);
        spCombCalculadora = findViewById(R.id.spCombCalculadora);
        spEvComparacao = findViewById(R.id.spEvComparacao);
        spCombComparacao = findViewById(R.id.spCombComparacao);
        sliderEnergyCost = findViewById(R.id.sliderEnergyCost);
        sliderGasCost = findViewById(R.id.sliderGasCost);
        sliderMonthlyKm = findViewById(R.id.sliderMonthlyKm);
        tvEnergyCostValue = findViewById(R.id.tvEnergyCostValue);
        tvGasCostValue = findViewById(R.id.tvGasCostValue);
        tvMonthlyKmValue = findViewById(R.id.tvMonthlyKmValue);
        tvSelectedEvCalculadora = findViewById(R.id.tvSelectedEvCalculadora);
        tvSelectedCombCalculadora = findViewById(R.id.tvSelectedCombCalculadora);
        tvSelectedEvComparacao = findViewById(R.id.tvSelectedEvComparacao);
        tvSelectedCombComparacao = findViewById(R.id.tvSelectedCombComparacao);
        tvAnnualSavings = findViewById(R.id.tvAnnualSavings);
        tvMonthlySavings = findViewById(R.id.tvMonthlySavings);
        tvConsumEvCalc = findViewById(R.id.tvConsumEvCalc);
        tvConsumCombCalc = findViewById(R.id.tvConsumCombCalc);
        tvPriceEvCalc = findViewById(R.id.tvPriceEvCalc);
        tvPriceCombCalc = findViewById(R.id.tvPriceCombCalc);
        tvCostEvCalc = findViewById(R.id.tvCostEvCalc);
        tvCostCombCalc = findViewById(R.id.tvCostCombCalc);
        tvConsumEvComparacao = findViewById(R.id.tvConsumEvComparacao);
        tvConsumCombComparacao = findViewById(R.id.tvConsumCombComparacao);
        tvPriceEvComparacao = findViewById(R.id.tvPriceEvComparacao);
        tvPriceCombComparacao = findViewById(R.id.tvPriceCombComparacao);
        tvCostEvComparacao = findViewById(R.id.tvCostEvComparacao);
        tvCostCombComparacao = findViewById(R.id.tvCostCombComparacao);
        tvAnnualSavingsCalc = findViewById(R.id.tvAnnualSavingsCalc);
    }

    private void setupSpinners() {
        ArrayAdapter<String> evAdapter = new ArrayAdapter<>(
            this, 
            android.R.layout.simple_spinner_item, 
            ELECTRIC_VEHICLES
        );
        evAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEvCalculadora.setAdapter(evAdapter);
        spEvComparacao.setAdapter(evAdapter);

        ArrayAdapter<String> combAdapter = new ArrayAdapter<>(
            this, 
            android.R.layout.simple_spinner_item, 
            COMBUSTION_VEHICLES
        );
        combAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCombCalculadora.setAdapter(combAdapter);
        spCombComparacao.setAdapter(combAdapter);

        spEvCalculadora.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                updateVehicleDisplay();
                updateCalculator();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spCombCalculadora.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                updateVehicleDisplay();
                updateCalculator();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spEvComparacao.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                updateVehicleDisplay();
                updateComparacao();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spCombComparacao.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                updateVehicleDisplay();
                updateComparacao();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spEvComparacao.setSelection(3);
        spCombComparacao.setSelection(1);
    }

    private void setupSliders() {
        sliderEnergyCost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double value = progress / 100.0;
                tvEnergyCostValue.setText(String.format("R$ %.2f", value));
                updateCalculator();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sliderGasCost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double value = (progress + 400) / 100.0;
                tvGasCostValue.setText(String.format("R$ %.2f", value));
                updateCalculator();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sliderMonthlyKm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int km = 500 + (progress * 50);
                DecimalFormat df = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("pt", "BR")));
                tvMonthlyKmValue.setText(df.format(km) + " km");
                updateCalculator();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setupTabListeners() {
        tabComparacao.setOnClickListener(v -> {
            showComparacao();
        });

        tabCalculadora.setOnClickListener(v -> {
            showCalculadora();
        });
    }

    private void showComparacao() {
        containerComparacao.setVisibility(FrameLayout.VISIBLE);
        containerCalculadora.setVisibility(FrameLayout.GONE);
        tabComparacao.setBackground(getDrawable(R.drawable.bg_tab_active));
        tabComparacao.setTextColor(getColor(R.color.primary));
        tabCalculadora.setBackground(getDrawable(R.drawable.bg_tab_inactive));
        tabCalculadora.setTextColor(getColor(R.color.text_dark));
        updateComparacao();
    }

    private void showCalculadora() {
        containerComparacao.setVisibility(FrameLayout.GONE);
        containerCalculadora.setVisibility(FrameLayout.VISIBLE);
        tabCalculadora.setBackground(getDrawable(R.drawable.bg_tab_active));
        tabCalculadora.setTextColor(getColor(R.color.primary));
        tabComparacao.setBackground(getDrawable(R.drawable.bg_tab_inactive));
        tabComparacao.setTextColor(getColor(R.color.text_dark));
        updateCalculator();
    }

    private void setupNavigation() {
        LinearLayout btnHome = findViewById(R.id.btnHome);
        LinearLayout btnComparisonNav = findViewById(R.id.btnComparison);
        LinearLayout btnSolar = findViewById(R.id.btnSolar);
        LinearLayout btnVehicles = findViewById(R.id.btnVehicles);
        LinearLayout btnProfile = findViewById(R.id.btnProfile);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ComparisonActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        btnComparisonNav.setOnClickListener(v -> {
            // Já está na tela de comparação
        });

        btnSolar.setOnClickListener(v -> {
            Intent intent = new Intent(ComparisonActivity.this, SolarActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        btnVehicles.setOnClickListener(v -> {
            Intent intent = new Intent(ComparisonActivity.this, VehiclesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ComparisonActivity.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    private void updateCalculator() {
        int evIdx = spEvCalculadora.getSelectedItemPosition();
        int combIdx = spCombCalculadora.getSelectedItemPosition();

        double evConsumption = EV_CONSUMPTION[evIdx];
        double combConsumption = COMBUSTION_CONSUMPTION[combIdx];

        double energyCost = sliderEnergyCost.getProgress() / 100.0;
        double gasCost = (sliderGasCost.getProgress() + 400) / 100.0;
        int monthlyKm = 500 + (sliderMonthlyKm.getProgress() * 50);

        double monthlyElec = (evConsumption / 100.0) * monthlyKm * energyCost;
        double monthlyComb = (combConsumption / 100.0) * monthlyKm * gasCost;
        double monthlySavings = monthlyComb - monthlyElec;
        double annualSavings = monthlySavings * 12;

        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        DecimalFormat dfInt = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("pt", "BR")));

        tvCostEvCalc.setText(String.format("R$ %s", df.format(monthlyElec)));
        tvCostCombCalc.setText(String.format("R$ %s", df.format(monthlyComb)));
        tvMonthlySavings.setText(String.format("R$ %s", df.format(Math.max(0, monthlySavings))));
        tvAnnualSavingsCalc.setText(String.format("R$ %s", dfInt.format((long) Math.max(0, annualSavings))));
    }

    private void updateVehicleDisplay() {
        int evIdxCalc = spEvCalculadora.getSelectedItemPosition();
        int combIdxCalc = spCombCalculadora.getSelectedItemPosition();
        int evIdxComp = spEvComparacao.getSelectedItemPosition();
        int combIdxComp = spCombComparacao.getSelectedItemPosition();

        DecimalFormat dfPrice = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("pt", "BR")));

        tvSelectedEvCalculadora.setText(ELECTRIC_VEHICLES[evIdxCalc]);
        tvSelectedCombCalculadora.setText(COMBUSTION_VEHICLES[combIdxCalc]);
        tvSelectedEvComparacao.setText(ELECTRIC_VEHICLES[evIdxComp]);
        tvSelectedCombComparacao.setText(COMBUSTION_VEHICLES[combIdxComp]);

        tvConsumEvCalc.setText(String.format("%.1f kWh/100km", EV_CONSUMPTION[evIdxCalc]));
        tvPriceEvCalc.setText(String.format("R$ %s", dfPrice.format(EV_PRICES[evIdxCalc])));
        tvConsumCombCalc.setText(String.format("%.1f L/100km", COMBUSTION_CONSUMPTION[combIdxCalc]));
        tvPriceCombCalc.setText(String.format("R$ %s", dfPrice.format(COMBUSTION_PRICES[combIdxCalc])));

        tvConsumEvComparacao.setText(String.format("%.1f kWh/100km", EV_CONSUMPTION[evIdxComp]));
        tvPriceEvComparacao.setText(String.format("R$ %s", dfPrice.format(EV_PRICES[evIdxComp])));
        tvConsumCombComparacao.setText(String.format("%.1f L/100km", COMBUSTION_CONSUMPTION[combIdxComp]));
        tvPriceCombComparacao.setText(String.format("R$ %s", dfPrice.format(COMBUSTION_PRICES[combIdxComp])));
    }

    private void updateComparacao() {
        int evIdx = spEvComparacao.getSelectedItemPosition();
        int combIdx = spCombComparacao.getSelectedItemPosition();

        double evConsumption = EV_CONSUMPTION[evIdx];
        double combConsumption = COMBUSTION_CONSUMPTION[combIdx];

        double energyCost = 0.80;
        double gasCost = 5.50;
        int annualKm = 15000;

        double annualElec = (evConsumption / 100.0) * annualKm * energyCost;
        double annualComb = (combConsumption / 100.0) * annualKm * gasCost;
        double annualSavings = annualComb - annualElec;

        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        DecimalFormat dfInt = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("pt", "BR")));

        tvCostEvComparacao.setText(String.format("R$ %s", dfInt.format(annualElec)));
        tvCostCombComparacao.setText(String.format("R$ %s", dfInt.format(annualComb)));
        tvAnnualSavings.setText(String.format("R$ %s", dfInt.format(Math.max(0, annualSavings))));
    }
}
