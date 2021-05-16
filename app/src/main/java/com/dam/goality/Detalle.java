package com.dam.goality;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.goality.model.CuerpoTecnico;

public class Detalle extends AppCompatActivity {

    TextView tvNombreCuerpoTecnico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        tvNombreCuerpoTecnico = findViewById(R.id.tvNombreCuerpoTecnico);

        CuerpoTecnico ct = getIntent().getParcelableExtra("CT");

//        String nombre = getIntent().getStringExtra("NOMBRE");

        tvNombreCuerpoTecnico.setText(ct.getNombre());


    }
}