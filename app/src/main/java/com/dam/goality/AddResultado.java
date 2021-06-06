package com.dam.goality;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Partido;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddResultado extends AppCompatActivity {

    RelativeLayout rl;
    Partido p;
    EditText etResultado1;
    EditText etResultado2;
    ImageView ivEquipo1;
    ImageView ivEquipo2;
    TextView tvLocal;
    TextView tvVisitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resultado);

        p = getIntent().getParcelableExtra("PARTIDO");

        rl = findViewById(R.id.rl);
        etResultado1 = findViewById(R.id.etResultado1);
        etResultado2 = findViewById(R.id.etResultado2);
        ivEquipo1 = findViewById(R.id.ivEquipo1);
        ivEquipo2 = findViewById(R.id.ivEquipo2);
        tvLocal = findViewById(R.id.tvLocal);
        tvVisitante = findViewById(R.id.tvVisitante);


//        Toast.makeText(this, p.getMiEquipo(), Toast.LENGTH_SHORT).show();

        if (p.getCondicion().equalsIgnoreCase("Visitante")) {
            Glide.with(ivEquipo1).load(p.getImgContrincante())
                    .into(ivEquipo1);
            Glide.with(ivEquipo2).load(p.getImgMiEquipo())
                    .into(ivEquipo2);
            tvLocal.setText(p.getContrincante());
            tvVisitante.setText(p.getMiEquipo());
            cargarResultado(String.valueOf(p.getGolesContrincante()), String.valueOf(p.getGolesMiEquipo()));
        } else {
            Glide.with(ivEquipo1).load(p.getImgMiEquipo())
                    .into(ivEquipo1);
            Glide.with(ivEquipo2).load(p.getImgContrincante())
                    .into(ivEquipo2);
            tvLocal.setText(p.getMiEquipo());
            tvVisitante.setText(p.getContrincante());
            cargarResultado(String.valueOf(p.getGolesMiEquipo()), String.valueOf(p.getGolesContrincante()));
        }
    }

    private void cargarResultado(String res1, String res2) {
        if (p.getGolesMiEquipo() >= 0 && p.getGolesContrincante() >= 0) {
            etResultado1.setText(res1);
            etResultado2.setText(res2);
        }
    }

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void aceptar(View view) {
        String local = etResultado1.getText().toString();
        String visitante = etResultado2.getText().toString();

        if (local.isEmpty() || visitante.isEmpty()) {
            Snackbar.make(rl, "Debe ingresar el resultado", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        } else {
            if (p.getCondicion().equalsIgnoreCase("Local")) {
                updatePartido(local, visitante);

            } else if (p.getCondicion().equalsIgnoreCase("Visitante")) {
                updatePartido(visitante, local);
            }
        }
    }

    private void updatePartido(String local, String visitante) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Partidos");

//            hashMap.put("local", local);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", p.getId());
        hashMap.put("miEquipo", p.getMiEquipo());
        hashMap.put("contrincante", p.getContrincante());
        hashMap.put("imgMiEquipo", p.getImgMiEquipo());
        hashMap.put("imgContrincante", p.getImgContrincante());
        hashMap.put("golesMiEquipo", Integer.parseInt(local));
        hashMap.put("golesContrincante", Integer.parseInt(visitante));
        hashMap.put("condicion", p.getCondicion());
        hashMap.put("horaPartido", p.getHoraPartido());
        hashMap.put("fechaPartido", p.getFechaPartido());
        hashMap.put("estadio", p.getEstadio());

        reference.child(p.getId()).setValue(hashMap);

        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }
}