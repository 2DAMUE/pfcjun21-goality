package com.dam.goality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.CuerpoTecnico;
import com.google.android.material.snackbar.Snackbar;

public class CuerpoTecnicoDetailActivity extends AppCompatActivity {

    RelativeLayout rl;
    ImageView ivFotoCTecnico;
    TextView tvNombre;
    TextView tvNacimientoCT;
    TextView tvPaisNacimiento;
    TextView tvEdadCT;
    TextView tvCargo;

    CuerpoTecnico ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuerpo_tecnico_detail);

        rl = findViewById(R.id.rl);
        ivFotoCTecnico = findViewById(R.id.ivFotoCTecnico);
        tvNombre = findViewById(R.id.tvNombre);
        tvNacimientoCT = findViewById(R.id.tvNacimientoCT);
        tvPaisNacimiento = findViewById(R.id.tvPaisNacimiento);
        tvEdadCT = findViewById(R.id.tvEdadCT);
        tvCargo = findViewById(R.id.tvCargo);

        ct = getIntent().getParcelableExtra("CUERPO_TECNICO");

        Glide.with(ivFotoCTecnico).load(ct.getFotoPerfilUrl())
                .into(ivFotoCTecnico);
        tvNombre.setText(ct.getNombre() + " - " + ct.getApellidos());
        tvNacimientoCT.setText(ct.getFechaNacimiento());
        tvPaisNacimiento.setText(ct.getNacionalidad());
        tvCargo.setText(ct.getCargo());
        tvEdadCT.setText(String.valueOf(ct.getEdad()));

    }

    public void editarPerfilCT(View view) {
        Intent i = new Intent(CuerpoTecnicoDetailActivity.this, EditCuerpoTecnicoActivity.class);
        i.putExtra("CUERPO_TECNICO", ct);
        startActivityForResult(i, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Snackbar.make(rl, "Se ha editado exitosamente", Snackbar.LENGTH_LONG)
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