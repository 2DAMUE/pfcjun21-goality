package com.dam.goality;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.goality.model.Jugador;

public class JugadorDetailActivity extends AppCompatActivity {

    TextView tvJugadorNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_detail);

        tvJugadorNombre = findViewById(R.id.tvJugadorNombre);

        Jugador jugador = getIntent().getParcelableExtra("JUGADOR");

        // Cargar los datos del jugador

        tvJugadorNombre.setText(jugador.getNombre());

    }
}