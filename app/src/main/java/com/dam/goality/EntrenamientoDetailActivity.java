package com.dam.goality;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Entrenamiento;

public class EntrenamientoDetailActivity extends AppCompatActivity {

    ImageView ivEntrenamiento;
    TextView tvFechaEntrenamietoD;
    TextView tvHoraEntrenamietoD;
    TextView tvDuracionEstimada;
    TextView tvHoraDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_detail);

        Entrenamiento entrenamiento = getIntent().getParcelableExtra("ENTRENAMIENTO");

        ivEntrenamiento = findViewById(R.id.ivEntrenamiento);
        tvFechaEntrenamietoD = findViewById(R.id.tvFechaEntrenamietoD);
        tvHoraEntrenamietoD = findViewById(R.id.tvHoraEntrenamietoD);
        tvDuracionEstimada = findViewById(R.id.tvDuracionEstimada);
        tvHoraDescripcion = findViewById(R.id.tvHoraDescripcion);

        // cargar los datos del entrenamiento

        Glide.with(ivEntrenamiento)
                .load(entrenamiento.getLugar())
                .centerCrop()
                .into(ivEntrenamiento);
        tvFechaEntrenamietoD.setText(entrenamiento.getFechaEntrenamiento());
        tvHoraEntrenamietoD.setText(entrenamiento.getHoraEntrenamiento());
        tvDuracionEstimada.setText(String.valueOf(entrenamiento.getDuracion()) + " minutos");
        tvHoraDescripcion.setText(entrenamiento.getDescripcion());
    }
}