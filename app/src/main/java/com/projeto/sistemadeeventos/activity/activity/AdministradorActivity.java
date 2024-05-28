package com.projeto.sistemadeeventos.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.projeto.sistemadeeventos.R;
import com.projeto.sistemadeeventos.activity.adapter.AdapterEventos;
import com.projeto.sistemadeeventos.activity.config.FirebaseConfig;
import com.projeto.sistemadeeventos.activity.entities.Evento;
import com.projeto.sistemadeeventos.activity.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AdministradorActivity extends AppCompatActivity {

    private Button btCreateEvento;
    private ImageView imgLogout;
    private AdapterEventos adapterEventos;
    private RecyclerView recyclerEventos;
    private List<Evento> eventos = new ArrayList<>();
    private FirebaseFirestore firestore = FirebaseConfig.getFirestore();

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
        initRecyclerView();
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

        recuperarEventos();

        recyclerEventos.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerEventos, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Evento evento = eventos.get(position);
                        deleteEvento(evento);
                        Toast.makeText(AdministradorActivity.this, "Evento deletado com sucesso",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    }
                }));

    }

    private void inicializarComponente() {
        btCreateEvento = (Button) findViewById(R.id.btCreateEvento);
        imgLogout = (ImageView) findViewById(R.id.imgLogoutCliente);
        recyclerEventos = (RecyclerView) findViewById(R.id.recyclerEventos);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerEventos.setLayoutManager(layoutManager);
        recyclerEventos.setHasFixedSize(true);
        adapterEventos = new AdapterEventos(eventos);
        recyclerEventos.setAdapter(adapterEventos);
    }

    private void recuperarEventos() {

        String idAdmin = FirebaseConfig.getIdUsuario();
        firestore.collection("eventos")
                .whereEqualTo("idAdmin", idAdmin)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (!value.isEmpty()) {
                            eventos.clear();
                            for (DocumentSnapshot query : value) {
                                Evento evento = new Evento();
                                evento.setIdAdmin(query.getId());
                                evento.setNome(query.getString("nome"));
                                evento.setData(query.getString("data"));
                                evento.setHorario(query.getString("horario"));
                                evento.setPrice(query.getString("price"));
                                evento.setLocal(query.getString("local"));
                                eventos.add(evento);
                            }
                            adapterEventos.notifyDataSetChanged();
                        } else {
                            eventos.clear();
                            adapterEventos.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void deleteEvento(Evento evento) {
        DocumentReference reference = firestore.collection("eventos")
                .document(evento.getIdAdmin());
        reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("DB", "Evento Deletado.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error", "Error ao deletar Evento: " + e.getMessage());
            }
        });
    }

    private void abrirTelaCadastro() {
        startActivity(new Intent(AdministradorActivity.this, CadastroEventoActivity.class));
    }

    private void abrirTelaLogin() {
        startActivity(new Intent(AdministradorActivity.this, LoginActivity.class));
        finish();
    }
}