package com.dam.goality;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.goality.model.Partido;

public class PartidoDetailActivity extends AppCompatActivity {

    TextView fecha;
    TextView hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido_detail);

        fecha = findViewById(R.id.fecha);
        hora = findViewById(R.id.hora);

        Partido p = getIntent().getParcelableExtra("PARTIDO");

        fecha.setText(p.getFechaPartido());
        hora.setText(p.getHoraPartido());
    }
}