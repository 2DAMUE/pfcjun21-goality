package com.dam.goality;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Partido;

public class PartidoDetailActivity extends AppCompatActivity {

    ImageView ivLocal;
    ImageView ivVisitante;
    TextView tvLocal;
    TextView tvVisitante;
    TextView fecha;
    TextView hora;
    TextView estadio;
    CountDownTimer countDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido_detail);

        ivLocal = findViewById(R.id.ivLocal);
        ivVisitante = findViewById(R.id.ivVisitante);
        tvLocal = findViewById(R.id.tvLocal);
        tvVisitante = findViewById(R.id.tvVisitante);
        fecha = findViewById(R.id.fecha);
        hora = findViewById(R.id.hora);
        estadio = findViewById(R.id.estadio);

        Partido p = getIntent().getParcelableExtra("PARTIDO");

        Glide.with(ivLocal).load(p.getImgLocal())
                .into(ivLocal);
        Glide.with(ivVisitante).load(p.getImgVisitante())
                .into(ivVisitante);
        tvLocal.setText(p.getLocal());
        tvVisitante.setText(p.getVisitante());
        fecha.setText(p.getFechaPartido());
        hora.setText(p.getHoraPartido());
        estadio.setText(p.getEstadio());

    }
}