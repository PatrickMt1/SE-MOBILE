package com.projeto.sistemadeeventos.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projeto.sistemadeeventos.R;
import com.projeto.sistemadeeventos.activity.config.FirebaseConfig;
import com.projeto.sistemadeeventos.activity.entities.Evento;

public class CadastroEventoActivity extends AppCompatActivity {

    private ImageView imgVoltarHome;
    private TextView textNome, textData, textHorario, textPreco, textLocal;
    private String idAdmin = "";
    private String urlImagemEvento = "";
    private Button btSalvarEvento;
    private FirebaseFirestore firestore = FirebaseConfig.getFirestore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inicializaComponente();

        imgVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaHome();
            }
        });

        btSalvarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalvarEvento();
            }
        });

    }

    private void inicializaComponente() {
        imgVoltarHome = (ImageView) findViewById(R.id.imgVolta);
        textNome = (TextView) findViewById(R.id.textNome);
        textData = (TextView) findViewById(R.id.textData);
        textHorario = (TextView) findViewById(R.id.textHorario);
        textPreco = (TextView) findViewById(R.id.textPrecoEvento);
        textLocal = (TextView) findViewById(R.id.textLocal);
        btSalvarEvento = (Button) findViewById(R.id.btsalvarEvento);
    }


    private void SalvarEvento() {

        idAdmin = FirebaseConfig.getIdUsuario();
        String nome = textNome.getText().toString();
        String data = textData.getText().toString();
        String horario = textHorario.getText().toString();
        String preco = textPreco.getText().toString();
        String local = textLocal.getText().toString();

        if (!nome.isEmpty() && !data.isEmpty() && !horario.isEmpty() && !preco.isEmpty() && !local.isEmpty()) {

            Evento evento = new Evento(idAdmin, nome, data, horario, preco, local);
            firestore.collection("eventos")
                    .add(evento)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Log.d("dbEvento", "Dados de Eventos => " + task);

                                Toast.makeText(CadastroEventoActivity.this, "Cadastro realizado com Sucesso!",
                                        Toast.LENGTH_LONG).show();
                                abrirTelaHome();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("dbEventoErro", "Erro ao salvar dados de Eventos => " + e.getMessage());
                        }
                    });
        } else {
            Toast.makeText(CadastroEventoActivity.this, "Todos os campos são obrigatorios", Toast.LENGTH_LONG).show();
        }
    }

    private void abrirTelaHome() {
        startActivity(new Intent(this, AdministradorActivity.class));
        finish();
    }
}