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
import com.dam.goality.model.CuerpoTecnico;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MiPerfilActivity extends AppCompatActivity {

    TextInputLayout tilNacimiento;
    AutoCompleteTextView atvNacimientoCT;
    ArrayList<String> listaPaises;
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
    NumberPicker npEdadStaff;
    String fecha = "";
    MaterialButton btnDateCT;
    DatePickerDialog.OnDateSetListener setListener;

    FirebaseUser firebaseUser;
    StorageReference storageReference;
    StorageTask uploadTask;
    String myUrl = "";

    CuerpoTecnico cuerpoTecnico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        ivPerfilCT = findViewById(R.id.ivPerfilCT);
        tvCambiarFoto = findViewById(R.id.tvCambiarFoto);
        npEdadStaff = findViewById(R.id.npEdadStaff);
        npEdadStaff.setMaxValue(1);
        npEdadStaff.setMaxValue(99);
        btnDateCT = findViewById(R.id.btnDateCT);
        tvDateStaff = findViewById(R.id.tvDateStaff);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        tilNacimiento = findViewById(R.id.tilNacimiento);
        atvNacimientoCT = findViewById(R.id.atvNacimientoCT);
        tilCargo = findViewById(R.id.tilCargo);
        atvCargo = findViewById(R.id.atvCargo);

        // Calendario
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Seleccionar fecha de nacimiento
        btnDateCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MiPerfilActivity.this,
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
                tvDateStaff.setText(date);
                fecha = date;
            }
        };

        // Number picker edad
        npEdadStaff.setMaxValue(4);
        npEdadStaff.setMaxValue(99);

        // Drop down menu país nacimiento
        listaPaises = new ArrayList<>();
        listaPaises.add("España");
        listaPaises.add("Alemania");
        listaPaises.add("Italia");
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaPaises);
        atvNacimientoCT.setAdapter(adapter);
        atvNacimientoCT.setThreshold(1);

        // Drop down menu cargo
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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete la acción usando"), 1);
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");

        // Cargar info usuario
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CuerpoTecnico").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CuerpoTecnico ct = snapshot.getValue(CuerpoTecnico.class);
                Glide.with(getApplicationContext()).load(ct.getFotoPerfilUrl()).into(ivPerfilCT);
                etNombre.setText(ct.getNombre());
                etApellidos.setText(ct.getApellidos());
                tvDateStaff.setText(ct.getFechaNacimiento());
                atvNacimientoCT.setText(ct.getNacionalidad());
                atvCargo.setText(ct.getCargo());
                npEdadStaff.setValue(ct.getEdad());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            Glide.with(ivPerfilCT.getContext()).load(imageUri)
                    .into(ivPerfilCT);
        }
    }


    public void cancelar(View view) {
    }

    public void aceptar(View view) {

        if (etNombre.getText().toString().isEmpty() || etApellidos.getText().toString().isEmpty() || npEdadStaff.getValue() == 0) {
            Toast.makeText(this, "Debe agregar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            upload();
        }


    }

    private void upload() {
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

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CuerpoTecnico").child(firebaseUser.getUid());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("nombre", etNombre.getText().toString());
                        hashMap.put("apellidos", etApellidos.getText().toString());
                        hashMap.put("fechaNacimiento", fecha);
                        hashMap.put("fotoPerfilUrl", myUrl);
                        hashMap.put("edad", npEdadStaff.getValue());
                        hashMap.put("nacionalidad", atvNacimientoCT.getText().toString());
                        hashMap.put("cargo", atvCargo.getText().toString());

                        reference.updateChildren(hashMap);
                        progressDialog.dismiss();

//                        Intent i = new Intent();
//                        setResult(RESULT_OK, i);
//                        finish();

                    } else {
                        Toast.makeText(MiPerfilActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MiPerfilActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CuerpoTecnico").child(firebaseUser.getUid());

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("nombre", etNombre.getText().toString().trim());
            hashMap.put("apellidos", etApellidos.getText().toString().trim());
            hashMap.put("fechaNacimiento", fecha);
            hashMap.put("edad", npEdadStaff.getValue());
            hashMap.put("nacionalidad", atvNacimientoCT.getText().toString());
            hashMap.put("cargo", atvCargo.getText().toString());
            reference.updateChildren(hashMap);
            progressDialog.dismiss();

//            Intent i = new Intent();
//            setResult(RESULT_OK, i);
//            finish();
        }
    }
}