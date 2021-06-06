package com.dam.goality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Partido;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;

public class PartidoDetailActivity extends AppCompatActivity {

    RelativeLayout rl;
    ImageView ivLocal;
    ImageView ivVisitante;
    TextView tvLocal;
    TextView tvVisitante;
    TextView fecha;
    TextView hora;
    TextView estadio;
    CountDownTimer countDownTimer = null;

    Partido p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido_detail);

        rl = findViewById(R.id.rl);
        ivLocal = findViewById(R.id.ivLocal);
        ivVisitante = findViewById(R.id.ivVisitante);
        tvLocal = findViewById(R.id.tvLocal);
        tvVisitante = findViewById(R.id.tvVisitante);
        fecha = findViewById(R.id.fecha);
        hora = findViewById(R.id.hora);
        estadio = findViewById(R.id.estadio);

        p = getIntent().getParcelableExtra("PARTIDO");

        if (p.getCondicion().equalsIgnoreCase("Visitante")) {
            Glide.with(ivLocal).load(p.getImgContrincante())
                    .into(ivLocal);
            Glide.with(ivVisitante).load(p.getImgMiEquipo())
                    .into(ivVisitante);
            tvLocal.setText(p.getContrincante());
            tvVisitante.setText(p.getMiEquipo());
        } else {
            Glide.with(ivLocal).load(p.getImgMiEquipo())
                    .into(ivLocal);
            Glide.with(ivVisitante).load(p.getImgContrincante())
                    .into(ivVisitante);
            tvLocal.setText(p.getMiEquipo());
            tvVisitante.setText(p.getContrincante());
        }

        fecha.setText(p.getFechaPartido());
        hora.setText(p.getHoraPartido());
        estadio.setText(p.getEstadio());

    }

    public void agregarResultado(View view) throws ParseException {
        Intent i = new Intent(PartidoDetailActivity.this, AddResultado.class);
        i.putExtra("PARTIDO", p);
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Snackbar.make(rl, "El resultado ha sido agregado", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        }

    }
}