package com.dam.goality;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Jugador;

public class JugadorDetailActivity extends AppCompatActivity {

    ImageView ivFotoJugador;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvNacimientoJugador;
    TextView tvPaisJugador;
    TextView tvPosicionJugador;
    TextView tvPesoJugador;
    TextView tvPesoEstaturaJugador;
    TextView tvPesoEdadJugador;
    TextView tvPesoDorsalJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_detail);

        ivFotoJugador = findViewById(R.id.ivFotoJugador);
        tvNombre = findViewById(R.id.tvNombre);
        tvApellido = findViewById(R.id.tvApellido);
        tvNacimientoJugador = findViewById(R.id.tvNacimientoJugador);
        tvPaisJugador = findViewById(R.id.tvPaisJugador);
        tvPosicionJugador = findViewById(R.id.tvPosicionJugador);
        tvPesoJugador = findViewById(R.id.tvPesoJugador);
        tvPesoEstaturaJugador = findViewById(R.id.tvPesoEstaturaJugador);
        tvPesoEdadJugador = findViewById(R.id.tvPesoEdadJugador);
        tvPesoDorsalJugador = findViewById(R.id.tvPesoDorsalJugador);

        Jugador jugador = getIntent().getParcelableExtra("JUGADOR");

        // Cargar los datos del jugador

        Glide.with(ivFotoJugador).load(jugador.getFotoPerfilUrl())
                .into(ivFotoJugador);
        tvNombre.setText(jugador.getNombre());
        tvApellido.setText(jugador.getApellidos());
        tvNacimientoJugador.setText(jugador.getFechaNacimiento());
        tvPaisJugador.setText(jugador.getNacionalidad());
        tvPosicionJugador.setText(jugador.getPosicion());
        tvPesoJugador.setText(String.valueOf(jugador.getPeso()));
        tvPesoEstaturaJugador.setText(String.valueOf(jugador.getEstatura()));
        tvPesoEdadJugador.setText(String.valueOf(jugador.getPeso()));
        tvPesoDorsalJugador.setText(String.valueOf(jugador.getPeso()));

    }
}