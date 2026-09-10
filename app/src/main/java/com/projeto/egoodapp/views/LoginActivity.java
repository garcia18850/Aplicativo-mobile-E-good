package com.projeto.egoodapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.projeto.egoodapp.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText editEmail, editSenha;
    private MaterialButton btnEntrar;
    private TextView btnTogglePessoa, btnToggleConcessionaria, tvRegister, tvForgot;
    private boolean isPessoa = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Vinculação dos componentes
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnTogglePessoa = findViewById(R.id.btnTogglePessoa);
        btnToggleConcessionaria = findViewById(R.id.btnToggleConcessionaria);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgot = findViewById(R.id.tvForgot);

        editEmail.setTextColor(ContextCompat.getColor(this, R.color.text_dark));
        editEmail.setHintTextColor(ContextCompat.getColor(this, R.color.text_gray));
        editSenha.setTextColor(ContextCompat.getColor(this, R.color.text_dark));
        editSenha.setHintTextColor(ContextCompat.getColor(this, R.color.text_gray));

        btnTogglePessoa.setCompoundDrawablePadding(8);
        btnToggleConcessionaria.setCompoundDrawablePadding(8);

        // Ações de clique
        btnTogglePessoa.setOnClickListener(v -> setToggleState(true));
        btnToggleConcessionaria.setOnClickListener(v -> setToggleState(false));
        btnEntrar.setOnClickListener(v -> realizarLogin());
        setToggleState(true);

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            Toast.makeText(this, "Indo para Cadastro", Toast.LENGTH_SHORT).show();
        });

        tvForgot.setOnClickListener(v -> {
            // startActivity(new Intent(LoginActivity.this, ForgotPasswordScreen.class));
            Toast.makeText(this, "Recuperação de senha em breve", Toast.LENGTH_SHORT).show();
        });
    }

    private void setToggleState(boolean selecionarPessoa) {
        isPessoa = selecionarPessoa;

        int selectedColor = ContextCompat.getColor(this, R.color.teal_primary);
        int defaultColor = ContextCompat.getColor(this, R.color.white);

        if (isPessoa) {
            btnTogglePessoa.setBackgroundResource(R.drawable.bg_toggle_selected);
            btnTogglePessoa.setTextColor(selectedColor);
            btnToggleConcessionaria.setBackground(null);
            btnToggleConcessionaria.setTextColor(defaultColor);
            setToggleIcon(btnTogglePessoa, R.drawable.ic_person, selectedColor);
            setToggleIcon(btnToggleConcessionaria, R.drawable.ic_store, defaultColor);
        } else {
            btnToggleConcessionaria.setBackgroundResource(R.drawable.bg_toggle_selected);
            btnToggleConcessionaria.setTextColor(selectedColor);
            btnTogglePessoa.setBackground(null);
            btnTogglePessoa.setTextColor(defaultColor);
            setToggleIcon(btnTogglePessoa, R.drawable.ic_person, defaultColor);
            setToggleIcon(btnToggleConcessionaria, R.drawable.ic_store, selectedColor);
        }
    }

    private void setToggleIcon(TextView textView, int drawableRes, int tintColor) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableRes);
        if (drawable == null) {
            return;
        }

        drawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTint(drawable, tintColor);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        textView.setCompoundDrawablePadding(8);
    }

    private void realizarLogin() {
        String email = editEmail.getText() != null ? editEmail.getText().toString().trim() : "";
        String senha = editSenha.getText() != null ? editSenha.getText().toString().trim() : "";

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha e-mail e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        btnEntrar.setEnabled(false); // Evita duplo clique

        // Bypass para usuário de teste (sem Firebase)
        if (email.equals("teste@gmail.com") && senha.equals("testeteste")) {
            Toast.makeText(this, "Login de teste realizado!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, task -> {
                    btnEntrar.setEnabled(true);
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
