package com.projeto.sistemadeeventos.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.sistemadeeventos.R;
import com.projeto.sistemadeeventos.activity.entities.Evento;

import java.util.List;

public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.MyViewHolder> {

    private List<Evento> eventos;

    public AdapterEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_eventos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.nomeEvento.setText(evento.getNome());
        holder.dataEvento.setText("Data: " + evento.getData());
        holder.horarioEvento.setText("Horario: " + evento.getHorario());
        holder.localEvento.setText("Local: " + evento.getLocal());
        holder.precoEvento.setText("R$: " + evento.getPrice());
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeEvento;
        TextView dataEvento;
        TextView horarioEvento;
        TextView localEvento;
        TextView precoEvento;

        public MyViewHolder(View itemView) {
            super(itemView);
            nomeEvento = itemView.findViewById(R.id.textNomeEvento);
            dataEvento = itemView.findViewById(R.id.textDataEvento);
            horarioEvento = itemView.findViewById(R.id.textHorarioEvento);
            localEvento = itemView.findViewById(R.id.textLocalEvento);
            precoEvento = itemView.findViewById(R.id.textPrecoEvento);
        }
    }
}
