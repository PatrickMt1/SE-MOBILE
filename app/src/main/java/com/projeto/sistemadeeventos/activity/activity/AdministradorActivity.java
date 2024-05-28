package com.projeto.sistemadeeventos.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.projeto.sistemadeeventos.R;

public class AdministradorActivity extends AppCompatActivity {

    private Button btCreateEvento;
    private ImageView imgLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicializarComponente();

        btCreateEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastro();
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaLogin();
            }
        });
    }

    private void inicializarComponente() {
        btCreateEvento = (Button) findViewById(R.id.btCreateEvento);
        imgLogout = (ImageView) findViewById(R.id.imgLogoutCliente);
    }

    private void abrirTelaCadastro() {
        startActivity(new Intent(AdministradorActivity.this, CadastroEventoActivity.class));
    }

    private void abrirTelaLogin() {
        startActivity(new Intent(AdministradorActivity.this, LoginActivity.class));
        finish();
    }
}