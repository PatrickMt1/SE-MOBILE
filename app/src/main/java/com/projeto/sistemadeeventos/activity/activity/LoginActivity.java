package com.projeto.sistemadeeventos.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projeto.sistemadeeventos.R;
import com.projeto.sistemadeeventos.activity.config.FirebaseConfig;

public class LoginActivity extends AppCompatActivity {

    private EditText textEmail, textSenha;
    private Button btLogin;
    private TextView textCadastrar;
    private String tipoConta;
    private FirebaseFirestore firestore = FirebaseConfig.getFirestore();
    private FirebaseAuth auth = FirebaseConfig.getAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciarComponente();

        textCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textEmail.getText().toString();
                String senha = textSenha.getText().toString();

                if (!email.isEmpty() && !senha.isEmpty()) {
                    auth.signInWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(LoginActivity.this,
                                            "Logado com sucesso!", Toast.LENGTH_LONG).show();
                                    recuperarUsuarioLogado();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("error", "Erro ao efetuar login" + e.getMessage());
                                    Toast.makeText(LoginActivity.this,
                                            "Email ou Senha Inválido!", Toast.LENGTH_LONG).show();
                                }
                            });

                } else {
                    if (email.isEmpty()) {
                        Toast.makeText(LoginActivity.this,
                                "Insira o email!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Insira a Senha!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void iniciarComponente() {
        textEmail = (EditText) findViewById(R.id.editEmailLogin);
        textSenha = (EditText) findViewById(R.id.editPasswordLogin);
        btLogin = (Button) findViewById(R.id.btLogin);
        textCadastrar = (TextView) findViewById(R.id.textCadastrar);

    }
    private void recuperarUsuarioLogado() {
        String userId = FirebaseConfig.getIdUsuario();
        firestore.collection("usuarios")
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        tipoConta = task.getResult().getString("tipoConta");
                        abrirTelaHome(tipoConta);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Error", "Erro ao bucar dados do usuario: " + e.getMessage());
                    }
                });
    }

    private void abrirTelaCadastro() {
        startActivity(new Intent(LoginActivity.this, CadastrarUsuarioActivity.class));
    }

    private void abrirTelaHome(String tipoConta) {
        if ("usuario".equals(tipoConta)) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        } else if("administrador".equals(tipoConta)){
            startActivity(new Intent(LoginActivity.this, AdministradorActivity.class));
        }
    }
}