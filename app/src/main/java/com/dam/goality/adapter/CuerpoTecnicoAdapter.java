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
import com.dam.goality.model.CuerpoTecnico;

import java.util.List;

public class CuerpoTecnicoAdapter
        extends RecyclerView.Adapter<CuerpoTecnicoAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<CuerpoTecnico> listaCT;

    private View.OnClickListener listener;

    public CuerpoTecnicoAdapter(Context mContext, List<CuerpoTecnico> listaCT) {
        this.mContext = mContext;
        this.listaCT = listaCT;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ct_layout, parent, false);
        view.setOnClickListener(this);
        CuerpoTecnicoAdapter.ViewHolder ivh = new CuerpoTecnicoAdapter.ViewHolder(view);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(listaCT.get(position));
    }

    @Override
    public int getItemCount() {
        return listaCT.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFotoCT;
        TextView tvNombreCT;
        TextView tvPosicionCT;
        TextView tvCargoCT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFotoCT = itemView.findViewById(R.id.ivFotoCT);
            tvNombreCT = itemView.findViewById(R.id.tvNombreCT);
            tvPosicionCT = itemView.findViewById(R.id.tvPosicionCT);
            tvCargoCT = itemView.findViewById(R.id.tvCargoCT);
        }

        public void bindItem(CuerpoTecnico ct) {
            Glide.with(ivFotoCT).load(ct.getFotoPerfilUrl())
                    .into(ivFotoCT);
            tvNombreCT.setText(ct.getNombre() + " " + ct.getApellidos());
            tvPosicionCT.setText(ct.getCargo());
            tvCargoCT.setText(String.valueOf(ct.getEdad()));
        }
    }
}
