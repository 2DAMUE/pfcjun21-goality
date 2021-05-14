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
import com.dam.goality.model.Jugador;

import java.util.List;

public class JugadorAdapter
        extends RecyclerView.Adapter<JugadorAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<Jugador> listaJugadores;

    private View.OnClickListener listener;

    public JugadorAdapter(Context mContext, List<Jugador> listaJugadores) {
        this.mContext = mContext;
        this.listaJugadores = listaJugadores;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_jugador_layout, parent, false);
        view.setOnClickListener(this);
        JugadorAdapter.ViewHolder ivh = new JugadorAdapter.ViewHolder(view);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(listaJugadores.get(position));
    }

    @Override
    public int getItemCount() {
        return listaJugadores.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFoto;
        TextView tvNombreJugador;
        TextView tvDorsalJugador;
        TextView tvPosicionJugador;
        TextView tvEdadJugador;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvNombreJugador = itemView.findViewById(R.id.tvNombreJugador);
            tvDorsalJugador = itemView.findViewById(R.id.tvDorsalJugador);
            tvPosicionJugador = itemView.findViewById(R.id.tvPosicionJugador);
            tvEdadJugador = itemView.findViewById(R.id.tvEdadJugador);
        }

        public void bindItem(Jugador jugador) {
            Glide.with(ivFoto).load(jugador.getFotoPerfilUrl())
                    .into(ivFoto);
            tvNombreJugador.setText(jugador.getNombre() + " " + jugador.getApellidos());
            tvDorsalJugador.setText(String.valueOf(jugador.getDorsal()));
            tvPosicionJugador.setText(jugador.getPosicion());
            tvEdadJugador.setText(String.valueOf(jugador.getEdad()));
        }
    }
}
