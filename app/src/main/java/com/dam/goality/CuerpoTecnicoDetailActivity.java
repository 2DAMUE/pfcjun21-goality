package com.dam.goality;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.goality.model.CuerpoTecnico;

public class CuerpoTecnicoDetailActivity extends AppCompatActivity {

    TextView nombreCuerpoTecnico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuerpo_tecnico_detail);

        nombreCuerpoTecnico = findViewById(R.id.nombreCuerpoTecnico);

        CuerpoTecnico ct = getIntent().getParcelableExtra("CT");

        // Cargar los datos del cuerpo técnico

        nombreCuerpoTecnico.setText(ct.getNombre());

    }
}