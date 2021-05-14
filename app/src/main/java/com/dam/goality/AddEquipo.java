package com.dam.goality;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.dam.goality.dialogFragments.EdadDialogFragment;
import com.dam.goality.dialogFragments.GeneroDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddEquipo extends AppCompatActivity implements GeneroDialogFragment.SingleChoiceListener, EdadDialogFragment.SingleChoiceListener {

    Uri imageUri;
    CircleImageView ivLogoEquipo;
    TextView tvCambiarFoto;
    TextView tvGenero;
    TextView tvEdadEquipo;
    Button btnGenero;
    Button btnEdadEquipo;
    EditText etNombre;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipo);

        tvGenero = findViewById(R.id.tvGenero);
        ivLogoEquipo = findViewById(R.id.ivLogoEquipo);
        tvEdadEquipo = findViewById(R.id.tvEdadEquipo);
        tvCambiarFoto = findViewById(R.id.tvCambiarFoto);
        btnGenero = findViewById(R.id.btnGenero);
        btnEdadEquipo = findViewById(R.id.btnEdadEquipo);
        etNombre = findViewById(R.id.etNombre);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        reference = database.getReference();

        // Dialog genero del equipo
        btnGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment generoDialog = new GeneroDialogFragment();
                generoDialog.setCancelable(false);
                generoDialog.show(getSupportFragmentManager(), "Single choice dialog");
            }
        });

        // Dialog categoría
        btnEdadEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment edadDialog = new EdadDialogFragment();
                edadDialog.setCancelable(false);
                edadDialog.show(getSupportFragmentManager(), "Single choice dialog");
            }
        });

        // Cambiar logo equipo
        tvCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete la acción usando"), 1);
            }
        });
    }

    // Cambiar foto de perfil
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Cargamos la imagen seleccionada en el ImageView
            imageUri = data.getData();
            Glide.with(ivLogoEquipo.getContext()).load(imageUri)
                    .into(ivLogoEquipo);
        }
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position, int tipo) {
        if (tipo == 1) {
            tvGenero.setText(list[position]);
        } else {
            tvEdadEquipo.setText(list[position]);
        }
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    public void aceptar(View view) {
        String nombre = etNombre.getText().toString().trim();
        String genero = tvGenero.getText().toString().trim();
        String edad = tvEdadEquipo.getText().toString().trim();

        if (nombre.isEmpty() || genero.equalsIgnoreCase("Género") || edad.equalsIgnoreCase("Rango de edad")) {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            uploadEquipo(nombre, genero, edad);
            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        }
    }

    private void uploadEquipo(String nombre, String genero, String edad) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Equipos");
        String postId = reference.push().getKey();


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", postId);
        hashMap.put("nombre", nombre);
        hashMap.put("genero", genero);
        hashMap.put("rangoEdad", edad);

        reference.child(postId).setValue(hashMap);
        Toast.makeText(AddEquipo.this, "Successfully uploaded!", Toast.LENGTH_SHORT).show();
    }

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}