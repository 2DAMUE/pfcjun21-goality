package com.dam.goality;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Entrenamiento;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EntrenamientoDetailActivity extends AppCompatActivity {

    RelativeLayout rl;
    ImageView ivEntrenamiento;
    TextView tvFechaEntrenamietoD;
    TextView tvHoraEntrenamietoD;
    TextView tvDuracionEstimada;
    TextView tvHoraDescripcion;
    RelativeLayout rl4;

    Entrenamiento entrenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_detail);

        entrenamiento = getIntent().getParcelableExtra("ENTRENAMIENTO");

        rl = findViewById(R.id.rl);
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

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void eliminarEntrenamiento(View view) {
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(this);
        alertDialog.setMessage("¿Estás seguro que desea eliminar este partido?");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Entrenamientos").child(entrenamiento.getId());
                dR.removeValue();

                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.create().show();
    }
}