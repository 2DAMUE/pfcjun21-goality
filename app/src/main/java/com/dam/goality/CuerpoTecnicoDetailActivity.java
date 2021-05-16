package com.dam.goality;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.CuerpoTecnico;

public class CuerpoTecnicoDetailActivity extends AppCompatActivity {

    ImageView ivFotoCTecnico;
    TextView tvNombreCT;
    TextView tvApellidoCT;
    TextView tvNacimientoCT;
    TextView tvPaisJugadorCT;
    TextView tvEdadCT;
    TextView tvCargoCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuerpo_tecnico_detail);

        ivFotoCTecnico = findViewById(R.id.ivFotoCTecnico);
        tvNombreCT = findViewById(R.id.tvNombreCT);
        tvApellidoCT = findViewById(R.id.tvApellidoCT);
        tvNacimientoCT = findViewById(R.id.tvNacimientoCT);
        tvPaisJugadorCT = findViewById(R.id.tvPaisJugadorCT);
        tvEdadCT = findViewById(R.id.tvEdadJugador);
        tvCargoCT = findViewById(R.id.tvCargoCT);

        CuerpoTecnico ct = getIntent().getParcelableExtra("STAFF");

        String apellido = getIntent().getStringExtra("APEL");
        String cargo = getIntent().getStringExtra("CARG");
//        String edad = getIntent().getStringExtra("ED");
        String nacimiento = getIntent().getStringExtra("NACI");
        String url = getIntent().getStringExtra("URL");
        String nacionalidad = getIntent().getStringExtra("NACIONALIDAD");
        String nombre = getIntent().getStringExtra("NOMBRE");

        // Cargar los datos del cuerpo técnico

        Glide.with(ivFotoCTecnico).load(url)
                .into(ivFotoCTecnico);
        tvNombreCT.setText(nombre);
        tvApellidoCT.setText(apellido);
        tvNacimientoCT.setText(nacimiento);
        tvPaisJugadorCT.setText(nacionalidad);
        tvCargoCT.setText(cargo);

    }
}