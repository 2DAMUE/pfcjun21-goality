package com.dam.goality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.goality.R;
import com.dam.goality.model.EquipoCategoria;

import java.util.List;

public class EquiposAdapter extends RecyclerView.Adapter<EquiposAdapter.ViewHolder> {

    private Context mContext;
    private List<EquipoCategoria> mEquipos;

    public EquiposAdapter(Context mContext, List<EquipoCategoria> mEquipos) {
        this.mContext = mContext;
        this.mEquipos = mEquipos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_equipo_layout, parent, false);
        ViewHolder ivh = new ViewHolder(view);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(mEquipos.get(position));
    }

    @Override
    public int getItemCount() {
        return mEquipos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivLogoEquipo;
        TextView tvRangoEdadEquipo;
        TextView tvGeneroEquipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivLogoEquipo = itemView.findViewById(R.id.ivLogoEquipo);
            tvRangoEdadEquipo = itemView.findViewById(R.id.tvRangoEdadEquipo);
            tvGeneroEquipo = itemView.findViewById(R.id.tvGeneroEquipo);
        }

        public void bindItem(EquipoCategoria equipo) {
            tvRangoEdadEquipo.setText(equipo.getRangoEdad());
            tvGeneroEquipo.setText(equipo.getGenero());
        }
    }
}
