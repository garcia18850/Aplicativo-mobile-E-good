package com.projeto.egoodapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projeto.egoodapp.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class VehiclesActivity extends AppCompatActivity {

    private LinearLayout vehiclesContainer;
    private EditText etSearch;
    private Button btnFilterAll, btnFilterHatch, btnFilterSUV, btnFilterCompact, btnFilterLuxury;
    private String currentFilter = "Todos";

    private static class Vehicle {
        String name;
        String type; // Hatch, SUV, SUV compacto, Luxo
        int price;
        double consumption; // kWh/100km
        int battery; // kWh
        String transmission; // Automático, Manual
        int autonomy; // km
        int power; // cv
        String charging; // AC/DC
        int imageResourceId;
        String badge; // Mais vendido, Premium, etc

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

    private Vehicle[] vehicles = {
        new Vehicle("Dolphin Mini", "Hatch", 119800, 12.9, 30, "Automático", 240, 100, "AC", R.drawable.car_byd_dolphin_mini, "Mais vendido"),
        new Vehicle("Dolphin", "Hatch", 149800, 13.4, 38, "Automático", 305, 101, "AC", R.drawable.car_byd_dolphin, "Popular"),
        new Vehicle("Volvo EX30", "SUV", 285000, 15.1, 40, "Automático", 265, 170, "AC/DC", R.drawable.car_volvo_ex30, "Premium"),
        new Vehicle("Mercedes EQE SUV", "Luxo", 729900, 18.9, 53, "Automático", 280, 260, "AC/DC", R.drawable.car_eqe_suv, "Luxo"),
        new Vehicle("Geely EX2", "Hatch", 123800, 14.2, 42, "Automático", 320, 118, "AC 11 kW", R.drawable.car_geely_ex2, "Novo"),
        new Vehicle("Chevrolet Bolt EV", "Hatch", 199990, 15.6, 65, "Automático", 417, 200, "AC/DC", R.drawable.car_bolt_ev, "Destaque")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);

        initializeViews();
        setupNavigation();
        setupFilters();
        displayVehicles("Todos");
    }

    private void initializeViews() {
        vehiclesContainer = findViewById(R.id.vehiclesContainer);
        etSearch = findViewById(R.id.etSearch);
        btnFilterAll = findViewById(R.id.btnFilterAll);
        btnFilterHatch = findViewById(R.id.btnFilterHatch);
        btnFilterSUV = findViewById(R.id.btnFilterSUV);
        btnFilterCompact = findViewById(R.id.btnFilterCompact);
        btnFilterLuxury = findViewById(R.id.btnFilterLuxury);

        TextView tvUserAvatar = findViewById(R.id.tvUserAvatar);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.getDisplayName() != null && !currentUser.getDisplayName().isEmpty()) {
            tvUserAvatar.setText(getInitials(currentUser.getDisplayName()));
        }
    }

    private void setupFilters() {
        btnFilterAll.setOnClickListener(v -> applyFilter("Todos"));
        btnFilterHatch.setOnClickListener(v -> applyFilter("Hatch"));
        btnFilterSUV.setOnClickListener(v -> applyFilter("SUV"));
        btnFilterCompact.setOnClickListener(v -> applyFilter("SUV compacto"));
        btnFilterLuxury.setOnClickListener(v -> applyFilter("Luxo"));

        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBySearch(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void applyFilter(String filter) {
        currentFilter = filter;
        etSearch.setText("");
        updateFilterButtons(filter);
        displayVehicles(filter);
    }

    private void updateFilterButtons(String filter) {
        Drawable activeDrawable = ContextCompat.getDrawable(this, R.drawable.bg_tab_active);
        Drawable inactiveDrawable = ContextCompat.getDrawable(this, R.drawable.bg_tab_inactive);

        btnFilterAll.setBackground(filter.equals("Todos") ? activeDrawable : inactiveDrawable);
        btnFilterAll.setTextColor(filter.equals("Todos") ? getColor(R.color.white) : getColor(R.color.text_gray));

        btnFilterHatch.setBackground(filter.equals("Hatch") ? activeDrawable : inactiveDrawable);
        btnFilterHatch.setTextColor(filter.equals("Hatch") ? getColor(R.color.white) : getColor(R.color.text_gray));

        btnFilterSUV.setBackground(filter.equals("SUV") ? activeDrawable : inactiveDrawable);
        btnFilterSUV.setTextColor(filter.equals("SUV") ? getColor(R.color.white) : getColor(R.color.text_gray));

        btnFilterCompact.setBackground(filter.equals("SUV compacto") ? activeDrawable : inactiveDrawable);
        btnFilterCompact.setTextColor(filter.equals("SUV compacto") ? getColor(R.color.white) : getColor(R.color.text_gray));

        btnFilterLuxury.setBackground(filter.equals("Luxo") ? activeDrawable : inactiveDrawable);
        btnFilterLuxury.setTextColor(filter.equals("Luxo") ? getColor(R.color.white) : getColor(R.color.text_gray));
    }

    private void displayVehicles(String filter) {
        vehiclesContainer.removeAllViews();

        for (int i = 0; i < vehicles.length; i++) {
            Vehicle vehicle = vehicles[i];
            if (filter.equals("Todos") || vehicle.type.equals(filter)) {
                addVehicleCard(vehicle, i);
            }
        }
    }

    private void filterBySearch(String query) {
        vehiclesContainer.removeAllViews();

        for (int i = 0; i < vehicles.length; i++) {
            Vehicle vehicle = vehicles[i];
            if (vehicle.name.toLowerCase().contains(query.toLowerCase())) {
                addVehicleCard(vehicle, i);
            }
        }
    }

    private void addVehicleCard(Vehicle vehicle, int vehicleIndex) {
        LinearLayout cardLayout = new LinearLayout(this);
        cardLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setBackground(getDrawable(android.R.color.white));
        cardLayout.setElevation(2);

        int margin = (int) (8 * getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, margin, 0, margin);
        cardLayout.setLayoutParams(layoutParams);

        // Add click listener to open detail view
        cardLayout.setOnClickListener(v -> {
            Intent intent = new Intent(VehiclesActivity.this, VehicleDetailActivity.class);
            intent.putExtra("vehicleIndex", vehicleIndex);
            startActivity(intent);
        });

        // Vehicle Image with Type Badge
        FrameLayout imageContainer = new FrameLayout(this);
        imageContainer.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                (int) (200 * getResources().getDisplayMetrics().density)
        ));
        imageContainer.setBackgroundColor(getColor(android.R.color.darker_gray));

        // Add vehicle image
        android.widget.ImageView imageView = new android.widget.ImageView(this);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        imageView.setImageResource(vehicle.imageResourceId);
        imageView.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
        imageContainer.addView(imageView);

        // Badge (Mais vendido, Premium, etc)
        if (vehicle.badge != null && !vehicle.badge.isEmpty()) {
            Button badgeButton = new Button(this);
            badgeButton.setText(vehicle.badge);
            badgeButton.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    (int) (28 * getResources().getDisplayMetrics().density),
                    android.view.Gravity.TOP | android.view.Gravity.START
            ));
            badgeButton.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_teal));
            badgeButton.setTextColor(getColor(R.color.white));
            badgeButton.setTextSize(10);
            ((FrameLayout.LayoutParams) badgeButton.getLayoutParams()).leftMargin = margin;
            ((FrameLayout.LayoutParams) badgeButton.getLayoutParams()).topMargin = margin;
            badgeButton.setPadding(12, 4, 12, 4);
            imageContainer.addView(badgeButton);
        }

        cardLayout.addView(imageContainer);

        // Vehicle Info Section
        LinearLayout infoLayout = new LinearLayout(this);
        infoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        infoLayout.setPadding(margin, margin, margin, margin);

        // Name and Price row
        LinearLayout namePrice = new LinearLayout(this);
        namePrice.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        namePrice.setOrientation(LinearLayout.HORIZONTAL);

        TextView tvName = new TextView(this);
        tvName.setText(vehicle.name);
        tvName.setTextSize(16);
        tvName.setTextColor(getColor(R.color.text_dark));
        tvName.setTypeface(null, android.graphics.Typeface.BOLD);
        tvName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        namePrice.addView(tvName);

        TextView tvPrice = new TextView(this);
        DecimalFormat df = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("pt", "BR")));
        if (vehicle.price > 0) {
            tvPrice.setText("R$ " + df.format(vehicle.price));
        } else {
            tvPrice.setText("Em breve");
        }
        tvPrice.setTextSize(14);
        tvPrice.setTextColor(getColor(R.color.primary));
        tvPrice.setTypeface(null, android.graphics.Typeface.BOLD);
        namePrice.addView(tvPrice);

        infoLayout.addView(namePrice);

        // Specs row - only autonomy, battery and category
        if (vehicle.price > 0) {
            LinearLayout specsLayout = new LinearLayout(this);
            specsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            specsLayout.setOrientation(LinearLayout.HORIZONTAL);
            ((LinearLayout.LayoutParams) specsLayout.getLayoutParams()).topMargin = margin / 2;

            // Autonomy
            LinearLayout spec1 = createSpecItem("📍 " + vehicle.autonomy + "\nkm");
            spec1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            specsLayout.addView(spec1);

            // Battery
            LinearLayout spec2 = createSpecItem("🔋 " + vehicle.battery + "\nkWh");
            spec2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            specsLayout.addView(spec2);

            // Category
            LinearLayout spec3 = createSpecItem("📦 " + vehicle.type);
            spec3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            specsLayout.addView(spec3);

            infoLayout.addView(specsLayout);
        }

        cardLayout.addView(infoLayout);
        vehiclesContainer.addView(cardLayout);
    }

    private LinearLayout createSpecItem(String text) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(12);
        tv.setTextColor(getColor(R.color.text_gray));
        layout.addView(tv);

        return layout;
    }

    private void setupNavigation() {
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
