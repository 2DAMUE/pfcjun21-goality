package com.dam.goality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

        ImageView ivLocal;
        ImageView ivVisitante;
        TextView tvLocal;
        TextView tvVisitante;
        TextView tvHoraPartido;
        TextView tvFechaPartido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLocal = itemView.findViewById(R.id.ivLocal);
            ivVisitante = itemView.findViewById(R.id.ivVisitante);
            tvLocal = itemView.findViewById(R.id.tvLocal);
            tvVisitante = itemView.findViewById(R.id.tvVisitante);
            tvHoraPartido = itemView.findViewById(R.id.tvHoraPartido);
            tvFechaPartido = itemView.findViewById(R.id.tvFechaPartido);
        }

        public void bindItem(Partido partido) {

            if (partido.getCondicion().equalsIgnoreCase("Visitante")) {
                Glide.with(ivLocal).load(partido.getImgContrincante())
                        .into(ivLocal);
                Glide.with(ivVisitante).load(partido.getImgMiEquipo())
                        .into(ivVisitante);
                tvLocal.setText(partido.getContrincante());
                tvVisitante.setText(partido.getMiEquipo());
            } else {
                Glide.with(ivLocal).load(partido.getImgMiEquipo())
                        .into(ivLocal);
                Glide.with(ivVisitante).load(partido.getImgContrincante())
                        .into(ivVisitante);
                tvLocal.setText(partido.getMiEquipo());
                tvVisitante.setText(partido.getContrincante());
            }

            tvHoraPartido.setText(partido.getHoraPartido());
            tvFechaPartido.setText(partido.getFechaPartido());
        }
    }

}
