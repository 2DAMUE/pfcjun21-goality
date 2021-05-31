package com.dam.goality;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Jugador;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class EditJugadorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ConstraintLayout cl;
    ImageView ivFotoJugador;
    EditText etNombre;
    EditText etApellidos;
    EditText etPeso;
    EditText etEstatura;
    NumberPicker npDorsal;
    TextView tvCambiarFoto;
    TextView tvFecha;

    Button btnDate;
    DatePickerDialog.OnDateSetListener setListener;
    String fecha;

    TextInputLayout tilNacimiento;
    AutoCompleteTextView atvNacimiento;
    List<String> listaPaises;
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
    int edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jugador);

        cl = findViewById(R.id.cl);
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

        // Number picker dorsal
        npDorsal = findViewById(R.id.npDorsal);
        npDorsal.setMaxValue(1);
        npDorsal.setMaxValue(99);

        // Cargar jugador
        jugador = getIntent().getParcelableExtra("JUGADOR_EDIT");
        cargarCampos(jugador);

        // Drop down menu país nacimiento
        tilNacimiento = findViewById(R.id.tilNacimiento);
        atvNacimiento = findViewById(R.id.atvNacimiento);
        Datos datos = new Datos();
        listaPaises = datos.listaPaises;
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
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(EditJugadorActivity.this);
            }
        });
    }

    // Seleccionar fecha de nacimiento
    public void seleccionarFechaNac(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.AppTheme_DatePickerDialog,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fecha = sdf.format(c.getTime());
        tvFecha.setText(fecha);

        Calendar c3 = Calendar.getInstance();
        String fechaActual = sdf.format(c3.getTime());

        try {
            Date d1 = sdf.parse(fecha);
            Date d2 = sdf.parse(fechaActual);
            Calendar c1 = new GregorianCalendar();
            Calendar c2 = new GregorianCalendar();
            c1.setTime(d1);
            c2.setTime(d2);

            edad = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR) -1;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Cambiar foto de perfil
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Glide.with(ivFotoJugador.getContext()).load(imageUri)
                        .into(ivFotoJugador);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void cargarCampos(Jugador jugador) {
        Glide.with(ivFotoJugador.getContext()).load(jugador.getFotoPerfilUrl())
                .into(ivFotoJugador);
        etNombre.setText(jugador.getNombre());
        etApellidos.setText(jugador.getApellidos());
        etPeso.setText(String.valueOf(jugador.getPeso()));
        etEstatura.setText(String.valueOf(jugador.getEstatura()));
        npDorsal.setValue(jugador.getDorsal());
        atvNacimiento.setText(jugador.getNacionalidad());
        atvCargo.setText(jugador.getPosicion());
        tvFecha.setText(jugador.getFechaNacimiento());
        fecha = jugador.getFechaNacimiento();
        edad = jugador.getEdad();

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
            Snackbar.make(cl, "Debe agregar todos los campos", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();

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
                        hashMap.put("edad", edad);
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
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jugadores");
            String id = jugador.getId();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", id);
            hashMap.put("nombre", etNombre.getText().toString());
            hashMap.put("apellidos", etApellidos.getText().toString());
            hashMap.put("fechaNacimiento", tvFecha.getText().toString());
            hashMap.put("fotoPerfilUrl", jugador.getFotoPerfilUrl());
            hashMap.put("edad", edad);
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