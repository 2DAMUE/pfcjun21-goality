package com.dam.goality;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
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

public class AddCuerpoTecnico extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ConstraintLayout cl;
    TextInputLayout tilNacimiento;
    AutoCompleteTextView atvNacimiento;
    List<String> listaPaises;
    ArrayAdapter<String> adapter;

    TextInputLayout tilCargo;
    AutoCompleteTextView atvCargo;
    ArrayList<String> listaCargos;
    ArrayAdapter<String> adapterCargo;

    Uri imageUri;
    ImageView ivPerfilCT;
    TextView tvCambiarFoto;
    TextView tvDateStaff;
    EditText etNombre;
    EditText etApellidos;
    String fecha = "";
    MaterialButton btnDateCT;
    DatePickerDialog.OnDateSetListener setListener;

    StorageReference storageReference;
    StorageTask uploadTask;
    String myUrl = "";
    int edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cuerpo_tecnico);

        cl = findViewById(R.id.cl);
        ivPerfilCT = findViewById(R.id.ivPerfilCT);
        tvCambiarFoto = findViewById(R.id.tvCambiarFoto);
        btnDateCT = findViewById(R.id.btnDateCT);
        tvDateStaff = findViewById(R.id.tvDateStaff);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);

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

        // Cambiar imagen de perfil
        tvCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddCuerpoTecnico.this);
            }
        });

        // Storage
        storageReference = FirebaseStorage.getInstance().getReference("CuerpoTecnico");
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

    // Cambiar foto de perfil
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Glide.with(ivPerfilCT.getContext()).load(imageUri)
                        .into(ivPerfilCT);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void aceptar(View view) {
        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String paisNacimiento = atvNacimiento.getText().toString();
        String cargo = atvCargo.getText().toString();

        if (nombre.isEmpty() || apellidos.isEmpty() || fecha.isEmpty() || paisNacimiento.equalsIgnoreCase("Selecciona la nacionalidad") || cargo.equalsIgnoreCase("Selecciona el cargo")) {
            Snackbar.make(cl, "Debes rellenar todos los campos", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        } else {
            upload();
        }

    }

    private void upload() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creando miembro del cuerpo técnico");
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

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CuerpoTecnico");
                        String id = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", id);
                        hashMap.put("nombre", etNombre.getText().toString());
                        hashMap.put("apellidos", etApellidos.getText().toString());
                        hashMap.put("fechaNacimiento", fecha);
                        hashMap.put("fotoPerfilUrl", myUrl);
                        hashMap.put("edad", edad);
                        hashMap.put("nacionalidad", atvNacimiento.getText().toString());
                        hashMap.put("cargo", atvCargo.getText().toString());

                        reference.child(id).setValue(hashMap);
                        progressDialog.dismiss();

                        Intent i = new Intent();
                        setResult(RESULT_OK, i);
                        finish();

                    } else {
                        Snackbar.make(cl, "Failed", Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.primary))
                                .show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddCuerpoTecnico.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
//            Toast.makeText(AddJugador.this, "No has seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
//            progressDialog.dismiss();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CuerpoTecnico");
            String id = reference.push().getKey();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", id);
            hashMap.put("nombre", etNombre.getText().toString());
            hashMap.put("apellidos", etApellidos.getText().toString());
            hashMap.put("fechaNacimiento", fecha);
            hashMap.put("fotoPerfilUrl", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Uploads%2Fpp.png?alt=media&token=bae95164-da0f-4a38-a6c3-91b19559fc12");
            hashMap.put("edad", edad);
            hashMap.put("nacionalidad", atvNacimiento.getText().toString());
            hashMap.put("cargo", atvCargo.getText().toString());

            reference.child(id).setValue(hashMap);
            progressDialog.dismiss();

            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        }
    }

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

}