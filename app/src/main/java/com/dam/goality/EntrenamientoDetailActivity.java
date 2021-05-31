package com.dam.goality;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    RelativeLayout rl4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_detail);

        Entrenamiento entrenamiento = getIntent().getParcelableExtra("ENTRENAMIENTO");

        ivEntrenamiento = findViewById(R.id.ivEntrenamiento);
        tvFechaEntrenamietoD = findViewById(R.id.tvFechaEntrenamietoD);
        tvHoraEntrenamietoD = findViewById(R.id.tvHoraEntrenamietoD);
        tvDuracionEstimada = findViewById(R.id.tvDuracionEstimada);
        tvHoraDescripcion = findViewById(R.id.tvDescripcion);
        rl4 = findViewById(R.id.rl4);

        // cargar los datos del entrenamiento

        Glide.with(ivEntrenamiento)
                .load(entrenamiento.getLugar())
                .centerCrop()
                .into(ivEntrenamiento);
        tvFechaEntrenamietoD.setText(entrenamiento.getFechaEntrenamiento());
        tvHoraEntrenamietoD.setText(entrenamiento.getHoraEntrenamiento());
        tvDuracionEstimada.setText(String.valueOf(entrenamiento.getDuracion()) + " minutos");
        tvHoraDescripcion.setText(entrenamiento.getDescripcion());

        if (!entrenamiento.getDescripcion().equals("")) {
            tvHoraDescripcion.setText(entrenamiento.getDescripcion());
            rl4.setVisibility(View.VISIBLE);
        }
    }
}