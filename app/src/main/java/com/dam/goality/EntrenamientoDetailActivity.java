package com.dam.goality;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.goality.model.Entrenamiento;

public class EntrenamientoDetailActivity extends AppCompatActivity {

    TextView tvFechaEntrenamietoD;
    TextView tvHoraEntrenamietoD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_detail);

        Entrenamiento entrenamiento = getIntent().getParcelableExtra("ENTRENAMIENTO");

        tvFechaEntrenamietoD = findViewById(R.id.tvFechaEntrenamietoD);
        tvHoraEntrenamietoD = findViewById(R.id.tvHoraEntrenamietoD);

        // cargar los datos del entrenamiento

        tvFechaEntrenamietoD.setText(entrenamiento.getFechaEntrenamiento());
        tvHoraEntrenamietoD.setText(entrenamiento.getHoraEntrenamiento());
    }
}