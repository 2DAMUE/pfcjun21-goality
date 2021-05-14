package com.dam.goality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.goality.R;
import com.dam.goality.model.Partido;

import java.util.List;

public class PartidosAdapter
        extends RecyclerView.Adapter<PartidosAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<Partido> listaEquipos;

    private View.OnClickListener listener;

    public PartidosAdapter(Context mContext, List<Partido> listaEquipos) {
        this.mContext = mContext;
        this.listaEquipos = listaEquipos;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_partido_layout, parent, false);
        view.setOnClickListener(this);
        PartidosAdapter.ViewHolder ivh = new PartidosAdapter.ViewHolder(view);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(listaEquipos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaEquipos.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLocal;
        TextView tvVisitante;
        TextView tvHoraPartido;
        TextView tvFechaPartido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocal = itemView.findViewById(R.id.tvLocal);
            tvVisitante = itemView.findViewById(R.id.tvVisitante);
            tvHoraPartido = itemView.findViewById(R.id.tvHoraPartido);
            tvFechaPartido = itemView.findViewById(R.id.tvFechaPartido);
        }

        public void bindItem(Partido partido) {
            tvLocal.setText(partido.getLocal());
            tvVisitante.setText(partido.getVisitante());
            tvHoraPartido.setText(partido.getHoraPartido());
            tvFechaPartido.setText(partido.getFechaPartido());
        }
    }
}
