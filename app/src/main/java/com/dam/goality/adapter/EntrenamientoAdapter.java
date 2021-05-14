package com.dam.goality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.goality.R;
import com.dam.goality.model.Entrenamiento;

import java.util.List;

public class EntrenamientoAdapter
        extends RecyclerView.Adapter<EntrenamientoAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<Entrenamiento> listaEntrenamientos;

    private View.OnClickListener listener;

    public EntrenamientoAdapter(Context mContext, List<Entrenamiento> listaEntrenamientos) {
        this.mContext = mContext;
        this.listaEntrenamientos = listaEntrenamientos;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_entrenamiento_layout, parent, false);
        view.setOnClickListener(listener);
        EntrenamientoAdapter.ViewHolder ivh = new EntrenamientoAdapter.ViewHolder(view);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(listaEntrenamientos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaEntrenamientos.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHoraPartido;
        TextView tvFechaPartido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoraPartido = itemView.findViewById(R.id.tvHoraPartido);
            tvFechaPartido = itemView.findViewById(R.id.tvFechaPartido);
        }

        public void bindItem(Entrenamiento entrenamiento) {
            tvHoraPartido.setText(entrenamiento.getHoraEntrenamiento());
            tvFechaPartido.setText(entrenamiento.getFechaEntrenamiento());
        }
    }
}
