package com.projeto.egoodapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.projeto.egoodapp.R;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView tvUserName;
    private TextView tvUserInitials;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        setupViews();
        loadUserData();
        setupNavigation();
        setupPreferences();
        setupLogout();
    }

    private void setupViews() {
        tvUserName = findViewById(R.id.tvUserName);
        tvUserInitials = findViewById(R.id.tvUserInitials);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void loadUserData() {
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            String nameToShow = (displayName != null && !displayName.isEmpty()) ? displayName : 
                               (email != null ? email.split("@")[0] : "Usuário");

            tvUserName.setText(nameToShow);
            tvUserInitials.setText(getInitials(nameToShow));
        }
    }

    private void setupNavigation() {
        LinearLayout btnHome = findViewById(R.id.btnHome);
        LinearLayout btnVehicles = findViewById(R.id.btnVehicles);
        LinearLayout btnComparison = findViewById(R.id.btnComparison);
        LinearLayout btnSolar = findViewById(R.id.btnSolar);
        LinearLayout btnProfile = findViewById(R.id.btnProfile);

        btnHome.setOnClickListener(v -> navigateToHome());
        btnVehicles.setOnClickListener(v -> navigateToVehicles());
        btnComparison.setOnClickListener(v -> navigateToComparison());
        btnSolar.setOnClickListener(v -> navigateToSolar());
        btnProfile.setOnClickListener(v -> {});
    }

    private void setupPreferences() {
        SwitchCompat swNotifications = findViewById(R.id.swNotifications);
        SwitchCompat swDarkMode = findViewById(R.id.swDarkMode);

        swNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle notifications preference
        });

        swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle dark mode preference
        });

        // Setup edit profile click listeners
        LinearLayout userProfileSection = findViewById(R.id.userProfileSection);
        if (userProfileSection != null) {
            userProfileSection.setOnClickListener(v -> showEditProfileDialog());
        }

        LinearLayout accountDataSection = findViewById(R.id.accountDataSection);
        if (accountDataSection != null) {
            accountDataSection.setOnClickListener(v -> showEditProfileDialog());
        }
    }

    private void setupLogout() {
        btnLogout.setOnClickListener(v -> logout());
    }

    private void showEditProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Perfil");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);

        EditText etName = new EditText(this);
        etName.setHint("Nome");
        etName.setText(currentUser != null && currentUser.getDisplayName() != null ? 
                      currentUser.getDisplayName() : "");
        layout.addView(etName);

        EditText etEmail = new EditText(this);
        etEmail.setHint("E-mail");
        etEmail.setText(currentUser != null ? currentUser.getEmail() : "");
        etEmail.setEnabled(false);
        layout.addView(etEmail);

        builder.setView(layout);
        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String newName = etName.getText().toString().trim();
            if (!newName.isEmpty() && currentUser != null) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build();

                currentUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Refresh current user
                                currentUser.reload();
                                loadUserData();
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void logout() {
        mAuth.signOut();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }

    private void navigateToVehicles() {
        startActivity(new Intent(this, VehiclesActivity.class)
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
