package com.dam.goality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Jugador;

public class JugadorDetailActivity extends AppCompatActivity {

    ImageView editProfile;
    ImageView ivFotoJugador;
    TextView tvNombre;
    TextView tvNacimientoJugador;
    TextView tvPaisJugador;
    TextView tvPosicionJugador;
    TextView tvPesoJugador;
    TextView tvEstatura;
    TextView tvEdad;
    TextView tvDorsal;

    Jugador jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_detail);

        editProfile = findViewById(R.id.editProfile);
        ivFotoJugador = findViewById(R.id.ivFotoJugador);
        tvNombre = findViewById(R.id.tvNombre);
        tvNacimientoJugador = findViewById(R.id.tvNacimientoJugador);
        tvPaisJugador = findViewById(R.id.tvPaisJugador);
        tvPosicionJugador = findViewById(R.id.tvPosicionJugador);
        tvPesoJugador = findViewById(R.id.tvPeso);
        tvEstatura = findViewById(R.id.tvEstatura);
        tvEdad = findViewById(R.id.tvEdad);
        tvDorsal = findViewById(R.id.tvPesoDorsalJugador);

        jugador = getIntent().getParcelableExtra("JUGADOR");

        // Cargar los datos del jugador

        Glide.with(ivFotoJugador).load(jugador.getFotoPerfilUrl())
                .into(ivFotoJugador);
        tvNombre.setText(jugador.getNombre() + " " + jugador.getApellidos());
        tvNacimientoJugador.setText(jugador.getFechaNacimiento());
        tvPaisJugador.setText(jugador.getNacionalidad());
        tvPosicionJugador.setText(jugador.getPosicion());
        tvPesoJugador.setText(String.valueOf(jugador.getPeso()));
        tvEstatura.setText(String.valueOf(jugador.getEstatura()));
        tvEdad.setText(String.valueOf(jugador.getPeso()));
        tvDorsal.setText(String.valueOf(jugador.getDorsal()));

    }

    public void editarPerfil(View view) {
        Intent i = new Intent(JugadorDetailActivity.this, EditJugadorActivity.class);
        i.putExtra("JUGADOR_EDIT", jugador);
        startActivityForResult(i, 2);
    }

    // Cambiar foto de perfil
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(JugadorDetailActivity.this, "OK", Toast.LENGTH_SHORT).show();
        }
    }
}