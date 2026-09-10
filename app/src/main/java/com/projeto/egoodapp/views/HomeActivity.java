package com.projeto.egoodapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projeto.egoodapp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tvUserGreeting = findViewById(R.id.tvUserGreeting);
        TextView tvUserAvatar = findViewById(R.id.tvUserAvatar);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String nameToShow;

        if (currentUser != null && currentUser.getDisplayName() != null && !currentUser.getDisplayName().trim().isEmpty()) {
            nameToShow = currentUser.getDisplayName();
        } else if (currentUser != null && currentUser.getEmail() != null && !currentUser.getEmail().trim().isEmpty()) {
            nameToShow = currentUser.getEmail().split("@")[0];
        } else {
            nameToShow = "usuário";
        }

        tvUserGreeting.setText("Olá, " + nameToShow + " 👋");

        String initials = getInitials(nameToShow);
        tvUserAvatar.setText(initials);

        setupNavigation();
    }

    private void setupNavigation() {
        // Bottom navigation buttons
        LinearLayout btnComparison = findViewById(R.id.btnComparison);
        LinearLayout btnVehicles = findViewById(R.id.btnVehicles);
        LinearLayout btnSolar = findViewById(R.id.btnSolar);
        LinearLayout btnProfile = findViewById(R.id.btnProfile);

        // Quick action buttons (top section)
        LinearLayout quickBtnComparison = findViewById(R.id.quickBtnComparison);
        LinearLayout quickBtnVehicles = findViewById(R.id.quickBtnVehicles);
        LinearLayout quickBtnSolar = findViewById(R.id.quickBtnSolar);
        LinearLayout quickBtnDealerships = findViewById(R.id.quickBtnDealerships);

        // "Ver todos" button
        TextView btnSeeAll = findViewById(R.id.btnSeeAll);

        // Comparison navigation
        btnComparison.setOnClickListener(v -> navigateToComparison());
        quickBtnComparison.setOnClickListener(v -> navigateToComparison());

        // Vehicles navigation
        btnVehicles.setOnClickListener(v -> navigateToVehicles());
        quickBtnVehicles.setOnClickListener(v -> navigateToVehicles());
        btnSeeAll.setOnClickListener(v -> navigateToVehicles());

        // Solar navigation
        btnSolar.setOnClickListener(v -> navigateToSolar());
        quickBtnSolar.setOnClickListener(v -> navigateToSolar());

        // Profile navigation
        btnProfile.setOnClickListener(v -> navigateToProfile());

        // Dealerships navigation (placeholder)
        quickBtnDealerships.setOnClickListener(v -> {
            // TODO: Navigate to Dealerships
        });

        // Chatbot FAB
        findViewById(R.id.fabChat).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
            startActivity(intent);
        });
    }

    private void navigateToComparison() {
        Intent intent = new Intent(HomeActivity.this, ComparisonActivity.class);
        startActivity(intent);
    }

    private void navigateToSolar() {
        Intent intent = new Intent(HomeActivity.this, SolarActivity.class);
        startActivity(intent);
    }

    private void navigateToProfile() {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void navigateToVehicles() {
        Intent intent = new Intent(HomeActivity.this, VehiclesActivity.class);
        startActivity(intent);
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
