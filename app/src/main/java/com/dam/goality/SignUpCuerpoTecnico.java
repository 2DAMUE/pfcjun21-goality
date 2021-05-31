package com.dam.goality;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class SignUpCuerpoTecnico extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ConstraintLayout cl;
    TextInputLayout tilNacimiento;
    AutoCompleteTextView atvNacimiento;
    List<String> listaPaises;
    ArrayAdapter<String> adapter;

    TextInputLayout tilCargo;
    AutoCompleteTextView atvCargo;
    ArrayList<String> listaCargos;
    ArrayAdapter<String> adapterCargo;

    Button btnDateCT;
    DatePickerDialog.OnDateSetListener setListener;
    StorageTask uploadTask;
    DatabaseReference reference;
    StorageReference storageReference;
    String myUrl = "";
    ProgressDialog progressDialog;

    ImageView ivPerfil;
    Uri imageUri;
    String fecha = "";
    TextView tvDateStaff;
    TextView tvCambiarFoto;
    String nombre;
    String apellido;
    String email;
    String password;

    FirebaseAuth auth;
    int edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_cuerpo_tecnico);

        cl = findViewById(R.id.cl);

        ivPerfil = findViewById(R.id.ivPerfil);
        btnDateCT = findViewById(R.id.btnDateCT);
        tvDateStaff = findViewById(R.id.tvDateStaff);
        tvCambiarFoto = findViewById(R.id.tvCambiarFoto);

        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("CuerpoTecnico");

        // Obtener nombre, apellidos, email, password
        nombre = getIntent().getStringExtra("NOMBRE");
        apellido = getIntent().getStringExtra("APELLIDO");
        email = getIntent().getStringExtra("EMAIL");
        password = getIntent().getStringExtra("PASSWORD");

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
        listaCargos.add("Entrenador");
        listaCargos.add("Segundo entrenador");
        listaCargos.add("Entrenador de porteros");
        listaCargos.add("Preparador físico");
        listaCargos.add("Nutricionista");
        listaCargos.add("Fisioterapeuta");
        listaCargos.add("Psicólogo");
        adapterCargo = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaCargos);
        atvCargo.setAdapter(adapterCargo);
        atvCargo.setThreshold(1);

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
        tvDateStaff.setText(fecha);

        Calendar c3 = Calendar.getInstance();
        String fechaActual = sdf.format(c3.getTime());

        try {
            Date d1 = sdf.parse(fecha);
            Date d2 = sdf.parse(fechaActual);
            Calendar c1 = new GregorianCalendar();
            Calendar c2 = new GregorianCalendar();
            c1.setTime(d1);
            c2.setTime(d2);

            edad = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR) - 1;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void cambiarFotoPerfil(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(SignUpCuerpoTecnico.this);
    }

    // Cambiar foto de perfil
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Glide.with(ivPerfil.getContext()).load(imageUri)
                        .into(ivPerfil);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void registrar(View view) {
        String paisNacimiento = atvNacimiento.getText().toString();
        String cargo = atvCargo.getText().toString();

        if (fecha.isEmpty() || paisNacimiento.equalsIgnoreCase("Selecciona la nacionalidad")
                || cargo.equalsIgnoreCase("Selecciona el cargo")) {
            Snackbar.make(cl, "Debes rellenar todos los campos", Snackbar.LENGTH_SHORT)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        } else {
            registrarUsuario(paisNacimiento, cargo);
        }
    }

    private void registrarUsuario(String paisNacimiento, String cargo) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creando perfil");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("CuerpoTecnico").child(userId);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("nombre", nombre);
                            hashMap.put("apellidos", apellido);
                            hashMap.put("fotoPerfilUrl", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Uploads%2Fpp.png?alt=media&token=bae95164-da0f-4a38-a6c3-91b19559fc12");
                            hashMap.put("edad", edad);
                            hashMap.put("fechaNacimiento", fecha);
                            hashMap.put("nacionalidad", paisNacimiento);
                            hashMap.put("cargo", cargo);
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SignUpCuerpoTecnico.this, MainActivityStaff.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });


                        } else {
                            progressDialog.dismiss();
                            Snackbar.make(cl, "Ya hay un usuario registrado con este email", Snackbar.LENGTH_SHORT)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(R.color.primary))
                                    .show();
                        }
                    }
                });

    }

}