package com.projeto.sistemadeeventos.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projeto.sistemadeeventos.R;
import com.projeto.sistemadeeventos.activity.config.FirebaseConfig;
import com.projeto.sistemadeeventos.activity.entities.Usuario;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class CadastrarUsuarioActivity extends AppCompatActivity {
    private EditText textNome, textGenero, textTelefone, textEmail, textSenha;
    private RadioButton radioAdmin, radioCliente;
    private Usuario user;
    private Button btSalvar, btCancelar;
    private FirebaseFirestore firestore = FirebaseConfig.getFirestore();
    private FirebaseAuth auth = FirebaseConfig.getAuth();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciarComponentes();

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaLogin();
                finish();
            }
        });

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = textNome.getText().toString();
                String genero = textGenero.getText().toString();
                String telefone = textTelefone.getText().toString();
                String email = textEmail.getText().toString();
                String senha = textSenha.getText().toString();
                String tipoConta = null;

                if (radioAdmin.isChecked()) {
                    tipoConta = "administrador";
                } else if (radioCliente.isChecked()) {
                    tipoConta = "usuario";
                }

                String criptSenha = BCrypt.withDefaults().hashToString(12, senha.toCharArray());
                user = new Usuario(nome, email, genero, telefone, criptSenha,tipoConta);

                if (nome.isEmpty() || telefone.isEmpty() || genero.isEmpty() || email.isEmpty()
                        || senha.isEmpty() || tipoConta.isEmpty()) {

                    Toast.makeText(CadastrarUsuarioActivity.this,
                            "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();

                } else {
                    cadastrarUsuario(user.getEmail(), user.getSenha());
                }
            }
        });

    }

    private void iniciarComponentes() {
        textNome = (EditText) findViewById(R.id.editTextNome);
        textGenero = (EditText) findViewById((R.id.editTextGenero));
        textTelefone = (EditText) findViewById(R.id.editTextTelefone);
        textEmail = (EditText) findViewById(R.id.editTextEmail);
        textSenha = (EditText) findViewById(R.id.editTextSenha);
        btSalvar = (Button) findViewById(R.id.btSalvar);
        btCancelar = (Button) findViewById(R.id.btCancelar);
        radioAdmin = (RadioButton) findViewById(R.id.radioAdmin);
        radioCliente = (RadioButton) findViewById(R.id.radioCliente);
    }

    private void cadastrarUsuario(String email, String senha) {

        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            salvarDadosUsuario(user);

                            Toast.makeText(CadastrarUsuarioActivity.this,
                                    "Cadastro Criado com Sucesso!", Toast.LENGTH_LONG).show();

                            abrirTelaLogin();

                        } else {
                            String error = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                error = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                error = "Por favor, digite um e-mail valido!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                error = "Esta conta já foi cadastrada!";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastrarUsuarioActivity.this,
                                    error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void salvarDadosUsuario(Usuario usuario) {
        String userId = FirebaseConfig.getIdUsuario();

        DocumentReference document = firestore.collection("usuarios").document(userId);
        document.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("dbUsuario", "Dados salvos com sucesso!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error", "Erro ao salvar dados do usuario: " + e.getMessage());
            }
        });
    }

    private void abrirTelaLogin() {
        startActivity(new Intent(CadastrarUsuarioActivity.this, LoginActivity.class));
        finish();
    }
}