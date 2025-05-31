package com.souunit.gohabit.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.souunit.gohabit.R;
import com.souunit.gohabit.model.Meta;

import java.util.List;

public class MetasAdapter extends RecyclerView.Adapter<MetasAdapter.MetaViewHolder> {

    private final List<Meta> metasList;

    public MetasAdapter(List<Meta> metasList) {
        this.metasList = metasList;
    }

    @NonNull
    @Override
    public MetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meta, parent, false);
        return new MetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MetaViewHolder holder, int position) {
        Meta meta = metasList.get(position);
        holder.tvTitulo.setText(meta.getTitulo());
        holder.tvDias.setText(String.join(", ", meta.getDias()));
        holder.tvIntensidade.setText("Intensidade: " + meta.getIntensidade());
    }

    @Override
    public int getItemCount() {
        return metasList.size();
    }

    static class MetaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDias, tvIntensidade;

        public MetaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTituloMeta);
            tvDias = itemView.findViewById(R.id.tvDiasMeta);
            tvIntensidade = itemView.findViewById(R.id.tvIntensidadeMeta);
        }
    }
}