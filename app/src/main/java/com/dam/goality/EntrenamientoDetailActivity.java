package com.dam.goality;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.goality.model.Entrenamiento;

public class EntrenamientoDetailActivity extends AppCompatActivity {

    TextView horaEntrenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_detail);

        Entrenamiento entrenamiento = getIntent().getParcelableExtra("ENTRENAMIENTO");

        horaEntrenamiento = findViewById(R.id.horaEntrenamiento);

        // cargar los datos del entrenamiento

        horaEntrenamiento.setText(entrenamiento.getHoraEntrenamiento());
    }
}