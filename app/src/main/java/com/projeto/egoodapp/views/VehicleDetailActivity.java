package com.projeto.egoodapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projeto.egoodapp.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class VehicleDetailActivity extends AppCompatActivity {

    private static class Vehicle {
        String name;
        String type;
        int price;
        double consumption;
        int battery;
        String transmission;
        int autonomy;
        int power;
        String charging;
        int imageResourceId;
        String badge;

        Vehicle(String name, String type, int price, double consumption, int battery, String transmission, int autonomy, int power, String charging, int imageResourceId, String badge) {
            this.name = name;
            this.type = type;
            this.price = price;
            this.consumption = consumption;
            this.battery = battery;
            this.transmission = transmission;
            this.autonomy = autonomy;
            this.power = power;
            this.charging = charging;
            this.imageResourceId = imageResourceId;
            this.badge = badge;
        }
    }

    private ImageView ivVehicleImage;
    private TextView tvVehicleName, tvPrice, tvAutonomy, tvBattery, tvConsumption, tvCategory, tvPower, tvCharging;
    private Button badgeDetail, btnFindDealer;
    private LinearLayout btnBackContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        initializeViews();
        loadVehicleData();
        setupNavigation();
    }

    private void initializeViews() {
        ivVehicleImage = findViewById(R.id.ivVehicleImage);
        tvVehicleName = findViewById(R.id.tvVehicleName);
        tvPrice = findViewById(R.id.tvPrice);
        tvAutonomy = findViewById(R.id.tvAutonomy);
        tvBattery = findViewById(R.id.tvBattery);
        tvConsumption = findViewById(R.id.tvConsumption);
        tvCategory = findViewById(R.id.tvCategory);
        tvPower = findViewById(R.id.tvPower);
        tvCharging = findViewById(R.id.tvCharging);
        badgeDetail = findViewById(R.id.badgeDetail);
        btnBackContainer = findViewById(R.id.btnBackContainer);
        btnFindDealer = findViewById(R.id.btnFindDealer);

        TextView tvUserAvatar = findViewById(R.id.tvUserAvatar);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.getDisplayName() != null && !currentUser.getDisplayName().isEmpty()) {
            tvUserAvatar.setText(getInitials(currentUser.getDisplayName()));
        }
    }

    private void loadVehicleData() {
        Intent intent = getIntent();
        int vehicleIndex = intent.getIntExtra("vehicleIndex", -1);

        if (vehicleIndex == -1) {
            finish();
            return;
        }

        Vehicle[] vehicles = {
            new Vehicle("Dolphin Mini", "Hatch", 119800, 12.9, 30, "Automático", 240, 100, "AC", R.drawable.car_byd_dolphin_mini, "Mais vendido"),
            new Vehicle("Dolphin", "Hatch", 149800, 13.4, 38, "Automático", 305, 101, "AC", R.drawable.car_byd_dolphin, "Popular"),
            new Vehicle("Volvo EX30", "SUV", 285000, 15.1, 40, "Automático", 265, 170, "AC/DC", R.drawable.car_volvo_ex30, "Premium"),
            new Vehicle("Mercedes EQE SUV", "Luxo", 729900, 18.9, 53, "Automático", 280, 260, "AC/DC", R.drawable.car_eqe_suv, "Luxo"),
            new Vehicle("Geely EX2", "Hatch", 123800, 14.2, 42, "Automático", 320, 118, "AC 11 kW", R.drawable.car_geely_ex2, "Novo"),
            new Vehicle("Chevrolet Bolt EV", "Hatch", 199990, 15.6, 65, "Automático", 417, 200, "AC/DC", R.drawable.car_bolt_ev, "Destaque")
        };

        if (vehicleIndex >= 0 && vehicleIndex < vehicles.length) {
            Vehicle vehicle = vehicles[vehicleIndex];
            displayVehicleData(vehicle);
        }
    }

    private void displayVehicleData(Vehicle vehicle) {
        ivVehicleImage.setImageResource(vehicle.imageResourceId);
        tvVehicleName.setText(vehicle.name);

        DecimalFormat df = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("pt", "BR")));
        tvPrice.setText("R$ " + df.format(vehicle.price));

        tvAutonomy.setText(vehicle.autonomy + " km");
        tvBattery.setText(vehicle.battery + " kWh");
        tvConsumption.setText(vehicle.consumption + " kWh/100km");
        tvCategory.setText(vehicle.type);
        tvPower.setText(vehicle.power + " cv");
        tvCharging.setText(vehicle.charging);

        badgeDetail.setText(vehicle.badge);
    }

    private void setupNavigation() {
        btnBackContainer.setOnClickListener(v -> finish());
        
        LinearLayout btnHome = findViewById(R.id.btnHome);
        LinearLayout btnVehicles = findViewById(R.id.btnVehicles);
        LinearLayout btnComparison = findViewById(R.id.btnComparison);
        LinearLayout btnSolar = findViewById(R.id.btnSolar);
        LinearLayout btnProfile = findViewById(R.id.btnProfile);

        btnHome.setOnClickListener(v -> navigateToHome());
        btnVehicles.setOnClickListener(v -> {});
        btnComparison.setOnClickListener(v -> navigateToComparison());
        btnSolar.setOnClickListener(v -> navigateToSolar());
        btnProfile.setOnClickListener(v -> navigateToProfile());

        btnFindDealer.setOnClickListener(v -> {
            // TODO: Implement dealer search
        });
    }

    private void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }

    private void navigateToComparison() {
        startActivity(new Intent(this, ComparisonActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }

    private void navigateToSolar() {
        startActivity(new Intent(this, SolarActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }

    private void navigateToProfile() {
        startActivity(new Intent(this, ProfileActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }

    private String getInitials(String text) {
        String[] parts = text.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, 1).toUpperCase();
        }
        String first = parts[0].substring(0, 1);
        String second = parts[1].substring(0, 1);
        return (first + second).toUpperCase();
    }
}
