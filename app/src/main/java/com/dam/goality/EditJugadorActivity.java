package com.dam.goality;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Jugador;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class EditJugadorActivity extends AppCompatActivity {

    ImageView ivFotoJugador;
    EditText etNombre;
    EditText etApellidos;
    EditText etPeso;
    EditText etEstatura;
    NumberPicker npEdad;
    NumberPicker npDorsal;
    TextView tvCambiarFoto;
    TextView tvFecha;

    Button btnDate;
    DatePickerDialog.OnDateSetListener setListener;
    String fecha;

    TextInputLayout tilNacimiento;
    AutoCompleteTextView atvNacimiento;
    ArrayList<String> listaPaises;
    ArrayAdapter<String> adapter;

    TextInputLayout tilCargo;
    AutoCompleteTextView atvCargo;
    ArrayList<String> listaCargos;
    ArrayAdapter<String> adapterCargo;

    Uri imageUri;
    StorageReference storageReference;
    StorageTask uploadTask;
    String myUrl = "";

    Jugador jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jugador);

        ivFotoJugador = findViewById(R.id.ivFotoJugador);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        tvFecha = findViewById(R.id.tvFecha);
        atvCargo = findViewById(R.id.atvCargo);
        etPeso = findViewById(R.id.etPeso);
        etEstatura = findViewById(R.id.etEstatura);
        btnDate = findViewById(R.id.btnDate);
        tvCambiarFoto = findViewById(R.id.tvCambiarFoto);
        tvFecha = findViewById(R.id.tvFecha);

        tilNacimiento = findViewById(R.id.tilNacimiento);
        atvNacimiento = findViewById(R.id.atvNacimiento);

        // Firebase
        storageReference = FirebaseStorage.getInstance().getReference("Jugadores");

        // Number picker edad
        npEdad = findViewById(R.id.npEdad);
        npEdad.setMaxValue(4);
        npEdad.setMaxValue(99);

        // Number picker dorsal
        npDorsal = findViewById(R.id.npDorsal);
        npDorsal.setMaxValue(1);
        npDorsal.setMaxValue(99);

        // Cargar jugador
        jugador = getIntent().getParcelableExtra("JUGADOR_EDIT");
        cargarCampos(jugador);

        // Calendario
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Seleccionar fecha de nacimiento
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditJugadorActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                tvFecha.setText(date);
                fecha = date;
            }
        };

        // Drop down menu país nacimiento
        tilNacimiento = findViewById(R.id.tilNacimiento);
        atvNacimiento = findViewById(R.id.atvNacimiento);
        listaPaises = new ArrayList<>();
        listaPaises.add("España");
        listaPaises.add("Alemania");
        listaPaises.add("Italia");
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaPaises);
        atvNacimiento.setAdapter(adapter);
        atvNacimiento.setThreshold(1);

        // Drop down menu cargo
        tilCargo = findViewById(R.id.tilCargo);
        atvCargo = findViewById(R.id.atvCargo);
        listaCargos = new ArrayList<>();
        listaCargos.add("Portero");
        listaCargos.add("Defensa");
        listaCargos.add("Mediocentro");
        listaCargos.add("Delantero");
        adapterCargo = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaCargos);
        atvCargo.setAdapter(adapterCargo);
        atvCargo.setThreshold(1);

        // Cambiar imagen de perfil
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
            Glide.with(ivFotoJugador.getContext()).load(imageUri)
                    .into(ivFotoJugador);
        }
    }

    private void cargarCampos(Jugador jugador) {
        Glide.with(ivFotoJugador.getContext()).load(jugador.getFotoPerfilUrl())
                .into(ivFotoJugador);
        etNombre.setText(jugador.getNombre());
        etApellidos.setText(jugador.getApellidos());
        etPeso.setText(String.valueOf(jugador.getPeso()));
        etEstatura.setText(String.valueOf(jugador.getEstatura()));
        npEdad.setValue(jugador.getEdad());
        npDorsal.setValue(jugador.getDorsal());
        atvNacimiento.setText(jugador.getNacionalidad());
        atvCargo.setText(jugador.getPosicion());
        tvFecha.setText(jugador.getFechaNacimiento());

    }

    public void cancelarCambios(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void aceptarCambios(View view) {
        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String nacionalidad = atvNacimiento.getText().toString();
        String posicion = atvCargo.getText().toString();
        String peso = etPeso.getText().toString().trim();
        String estatura = etEstatura.getText().toString().trim();

        if (nombre.isEmpty() || apellidos.isEmpty() || nacionalidad.isEmpty() || posicion.isEmpty() || peso.isEmpty() || estatura.isEmpty()) {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            upload(peso, estatura);
        }

    }

    private void upload(String peso, String estatura) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Editando perfil");
        progressDialog.show();

        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "");
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jugadores");
                        String id = jugador.getId();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", id);
                        hashMap.put("nombre", etNombre.getText().toString());
                        hashMap.put("apellidos", etApellidos.getText().toString());
                        hashMap.put("fechaNacimiento", tvFecha.getText().toString());
                        hashMap.put("fotoPerfilUrl", myUrl);
                        hashMap.put("edad", npEdad.getValue());
                        hashMap.put("nacionalidad", atvNacimiento.getText().toString());
                        hashMap.put("posicion", atvCargo.getText().toString());
                        hashMap.put("peso", Double.parseDouble(peso));
                        hashMap.put("estatura", Double.parseDouble(estatura));
                        hashMap.put("dorsal", npDorsal.getValue());

                        reference.child(id).setValue(hashMap);
                        progressDialog.dismiss();

                        Intent i = new Intent();
                        setResult(RESULT_OK, i);
                        finish();

                    } else {
                        Toast.makeText(EditJugadorActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditJugadorActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
//            Toast.makeText(AddJugador.this, "No has seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
//            progressDialog.dismiss();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jugadores");
            String id = jugador.getId();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", id);
            hashMap.put("nombre", etNombre.getText().toString());
            hashMap.put("apellidos", etApellidos.getText().toString());
            hashMap.put("fechaNacimiento", tvFecha.getText().toString());
            hashMap.put("fotoPerfilUrl", jugador.getFotoPerfilUrl());
            hashMap.put("edad", npEdad.getValue());
            hashMap.put("nacionalidad", atvNacimiento.getText().toString());
            hashMap.put("posicion", atvCargo.getText().toString());
            hashMap.put("peso", Double.parseDouble(peso));
            hashMap.put("estatura", Double.parseDouble(estatura));
            hashMap.put("dorsal", npDorsal.getValue());

            reference.child(id).setValue(hashMap);
            progressDialog.dismiss();

            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        }
    }
}