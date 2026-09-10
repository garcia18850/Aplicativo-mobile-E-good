package com.projeto.egoodapp.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.projeto.egoodapp.R;

public class RegisterActivity extends AppCompatActivity {

    private boolean isPessoa = true;
    private LinearLayout cardPessoa, cardConcessionaria, formPessoa, formConcessionaria,
            locationPessoaContainer, locationConcessionariaContainer;
    private ImageView iconPessoa, iconConcessionaria;
    private TextView titlePessoa, descPessoa, titleConcessionaria, descConcessionaria;

    private TextInputEditText editNome, editEmail, editSenha, editConfSenha;
    private TextInputEditText editNomeEmpresa, editCnpj, editEmailEmpresa, editSenhaEmpresa,
            editConfSenhaEmpresa, editTelefone, editEndereco, editCidade, editEstado, editCep;
    private MaterialButton btnCadastrar;
    private TextView tvVoltar, tvLogin;

    private FusedLocationProviderClient fusedLocationClient;
    private final ActivityResultLauncher<String> locationPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    preencherLocalizacaoAtual();
                } else {
                    Toast.makeText(this, "Permiss?o de localiza??o negada", Toast.LENGTH_SHORT).show();
                }
            });

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        cardPessoa = findViewById(R.id.cardPessoa);
        cardConcessionaria = findViewById(R.id.cardConcessionaria);
        formPessoa = findViewById(R.id.formPessoa);
        formConcessionaria = findViewById(R.id.formConcessionaria);
        locationPessoaContainer = findViewById(R.id.locationPessoaContainer);
        locationConcessionariaContainer = findViewById(R.id.locationConcessionariaContainer);
        iconPessoa = findViewById(R.id.iconPessoa);
        iconConcessionaria = findViewById(R.id.iconConcessionaria);
        titlePessoa = findViewById(R.id.titlePessoa);
        descPessoa = findViewById(R.id.descPessoa);
        titleConcessionaria = findViewById(R.id.titleConcessionaria);
        descConcessionaria = findViewById(R.id.descConcessionaria);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        editConfSenha = findViewById(R.id.editConfSenha);

        editNomeEmpresa = findViewById(R.id.editNomeEmpresa);
        editCnpj = findViewById(R.id.editCnpj);
        editEmailEmpresa = findViewById(R.id.editEmailEmpresa);
        editSenhaEmpresa = findViewById(R.id.editSenhaEmpresa);
        editConfSenhaEmpresa = findViewById(R.id.editConfSenhaEmpresa);
        editTelefone = findViewById(R.id.editTelefone);
        editEndereco = findViewById(R.id.editEndereco);
        editCidade = findViewById(R.id.editCidade);
        editEstado = findViewById(R.id.editEstado);
        editCep = findViewById(R.id.editCep);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        tvVoltar = findViewById(R.id.tvVoltar);
        tvLogin = findViewById(R.id.tvLogin);

        findViewById(R.id.btnAutoLocation).setOnClickListener(v -> pedirLocalizacaoAtual());
        findViewById(R.id.btnManualLocation).setOnClickListener(v -> abrirPreenchimentoManual());
        findViewById(R.id.btnAutoLocationConcessionariaContainer).setOnClickListener(v -> pedirLocalizacaoAtual());
        findViewById(R.id.btnManualLocationConcessionariaContainer).setOnClickListener(v -> abrirPreenchimentoManual());

        cardPessoa.setOnClickListener(v -> setCardState(true));
        cardConcessionaria.setOnClickListener(v -> setCardState(false));

        tvVoltar.setOnClickListener(v -> finish());
        tvLogin.setOnClickListener(v -> finish());

        btnCadastrar.setOnClickListener(v -> realizarCadastro());
        setCardState(true);
    }

    private void setCardState(boolean selecionarPessoa) {
        isPessoa = selecionarPessoa;
        int colorPrimary = ContextCompat.getColor(this, R.color.primary);
        int colorTextDark = ContextCompat.getColor(this, R.color.text_dark);
        int colorTextGray = ContextCompat.getColor(this, R.color.text_gray);

        if (isPessoa) {
            cardPessoa.setBackgroundResource(R.drawable.bg_card_selected);
            iconPessoa.setColorFilter(colorPrimary);
            titlePessoa.setTextColor(colorPrimary);
            descPessoa.setTextColor(colorPrimary);

            cardConcessionaria.setBackgroundResource(R.drawable.bg_card_unselected);
            iconConcessionaria.setColorFilter(colorTextGray);
            titleConcessionaria.setTextColor(colorTextDark);
            descConcessionaria.setTextColor(colorTextGray);

            formPessoa.setVisibility(View.VISIBLE);
            formConcessionaria.setVisibility(View.GONE);
            locationPessoaContainer.setVisibility(View.VISIBLE);
            locationConcessionariaContainer.setVisibility(View.GONE);
            btnCadastrar.setText("Cadastrar");
        } else {
            cardConcessionaria.setBackgroundResource(R.drawable.bg_card_selected);
            iconConcessionaria.setColorFilter(colorPrimary);
            titleConcessionaria.setTextColor(colorPrimary);
            descConcessionaria.setTextColor(colorPrimary);

            cardPessoa.setBackgroundResource(R.drawable.bg_card_unselected);
            iconPessoa.setColorFilter(colorTextGray);
            titlePessoa.setTextColor(colorTextDark);
            descPessoa.setTextColor(colorTextGray);

            formPessoa.setVisibility(View.GONE);
            formConcessionaria.setVisibility(View.VISIBLE);
            locationPessoaContainer.setVisibility(View.GONE);
            locationConcessionariaContainer.setVisibility(View.VISIBLE);
            btnCadastrar.setText("Cadastrar concessionária");
        }

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) btnCadastrar.getLayoutParams();
        params.topToBottom = isPessoa ? R.id.locationPessoaContainer : R.id.locationConcessionariaContainer;
        btnCadastrar.setLayoutParams(params);
    }

    private void realizarCadastro() {
        if (isPessoa) {
            String nome = getText(editNome);
            String email = getText(editEmail);
            String senha = getText(editSenha);
            String confSenha = getText(editConfSenha);

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos da pessoa", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confSenha)) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            String nomeEmpresa = getText(editNomeEmpresa);
            String cnpj = getText(editCnpj);
            String email = getText(editEmailEmpresa);
            String senha = getText(editSenhaEmpresa);
            String confSenha = getText(editConfSenhaEmpresa);
            String telefone = getText(editTelefone);
            String endereco = getText(editEndereco);
            String cidade = getText(editCidade);
            String estado = getText(editEstado);
            String cep = getText(editCep);

            if (nomeEmpresa.isEmpty() || cnpj.isEmpty() || email.isEmpty() || senha.isEmpty()
                    || confSenha.isEmpty() || telefone.isEmpty() || endereco.isEmpty()
                    || cidade.isEmpty() || estado.isEmpty() || cep.isEmpty()) {
                Toast.makeText(this, "Preencha todos os dados da concessionária", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confSenha)) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        btnCadastrar.setEnabled(false);

        String email = isPessoa ? getText(editEmail) : getText(editEmailEmpresa);
        String senha = isPessoa ? getText(editSenha) : getText(editSenhaEmpresa);

        // Bypass para usuário de teste (sem Firebase)
        if (email.equals("teste@gmail.com")) {
            Toast.makeText(this, "Cadastro de teste realizado com sucesso!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, task -> {
                    btnCadastrar.setEnabled(true);
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Erro: " + (task.getException() != null ? task.getException().getMessage() : "Falha no cadastro"), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void pedirLocalizacaoAtual() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            preencherLocalizacaoAtual();
            return;
        }

        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void preencherLocalizacaoAtual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiss?o de localiza??o necess?ria", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location == null) {
                Toast.makeText(this, "N?o foi poss?vel obter o GPS agora", Toast.LENGTH_SHORT).show();
                return;
            }

            String texto = String.format(java.util.Locale.getDefault(), "Lat: %.5f, Lng: %.5f", location.getLatitude(), location.getLongitude());
            preencheEndereco(texto);
            Toast.makeText(this, "Localiza??o atual preenchida", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(this, "Erro ao obter localiza??o", Toast.LENGTH_SHORT).show());
    }

    private void abrirPreenchimentoManual() {
        preencheEndereco("Digite seu endere?o manualmente");
    }

    private void preencheEndereco(String endereco) {
        if (isPessoa && editEndereco != null) {
            editEndereco.setText(endereco);
        } else if (!isPessoa && editEndereco != null) {
            editEndereco.setText(endereco);
        }
    }

    private String getText(TextInputEditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }
}