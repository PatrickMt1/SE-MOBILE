package com.projeto.sistemadeeventos.activity.activity;

import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projeto.sistemadeeventos.activity.config.FirebaseConfig;

public class CadastrarEnderecoActivity {
    private EditText textCidade, textCep, textEstado, textRua, textNumero;

    private FirebaseFirestore firestore = FirebaseConfig.getFirestore();
    private FirebaseAuth auth = FirebaseConfig.getAuth();
}
