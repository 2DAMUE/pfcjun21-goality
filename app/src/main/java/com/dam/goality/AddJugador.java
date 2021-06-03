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
import android.widget.NumberPicker;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AddJugador extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
    CircleImageView ivFotoJugador;
    TextView tvFecha;
    TextView tvCambiarFoto;
    TextView etNombre;
    TextView etApellidos;
    TextView etPeso;
    TextView etEstatura;
    NumberPicker npDorsal;
    MaterialButton btnDate;
    String fecha = "";

    StorageReference storageReference;
    StorageTask uploadTask;
    String myUrl = "";

    DatePickerDialog.OnDateSetListener setListener;

    int edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jugador);

        cl = findViewById(R.id.cl);
        tilNacimiento = findViewById(R.id.tilNacimiento);
        atvNacimiento = findViewById(R.id.atvNacimiento);

        btnDate = findViewById(R.id.btnDate);
        tvFecha = findViewById(R.id.tvFecha);
        tvCambiarFoto = findViewById(R.id.tvCambiarFoto);
        ivFotoJugador = findViewById(R.id.ivFotoJugador);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etPeso = findViewById(R.id.etPeso);
        etEstatura = findViewById(R.id.etEstatura);

        // Number picker dorsal
        npDorsal = findViewById(R.id.npDorsal);
        npDorsal.setMaxValue(1);
        npDorsal.setMaxValue(99);

        // Firebase
        storageReference = FirebaseStorage.getInstance().getReference("Jugadores");

        // Calendario
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

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
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/jpeg");
//                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//                startActivityForResult(Intent.createChooser(intent, "Complete la acción usando"), 1);
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddJugador.this);
            }
        });

    }

    // Seleccionar fecha de nacimiento
    public void seleccionarFechaNacJ(View view) {
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

            edad = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

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

    public void aceptar(View view) {

        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String nacionalidad = atvNacimiento.getText().toString();
        String posicion = atvCargo.getText().toString();
        String peso = etPeso.getText().toString().trim();
        String estatura = etEstatura.getText().toString().trim();

        if (nombre.isEmpty() || apellidos.isEmpty() || fecha.isEmpty() || npDorsal.getValue() == 0
                || nacionalidad.equalsIgnoreCase("Selecciona el país")
                || posicion.equalsIgnoreCase("Selecciona la posición")
                || peso.isEmpty() || estatura.isEmpty()) {
            Snackbar.make(cl, "Debe llenar todos los campos", Snackbar.LENGTH_LONG)
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
        progressDialog.setMessage("Creando jugador");
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
                        String id = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", id);
                        hashMap.put("nombre", etNombre.getText().toString());
                        hashMap.put("apellidos", etApellidos.getText().toString());
                        hashMap.put("fechaNacimiento", fecha);
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
                        Toast.makeText(AddJugador.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddJugador.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
//            Toast.makeText(AddJugador.this, "No has seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
//            progressDialog.dismiss();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jugadores");
            String id = reference.push().getKey();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", id);
            hashMap.put("nombre", etNombre.getText().toString());
            hashMap.put("apellidos", etApellidos.getText().toString());
            hashMap.put("fechaNacimiento", fecha);
            hashMap.put("fotoPerfilUrl", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Uploads%2Fpp.png?alt=media&token=bae95164-da0f-4a38-a6c3-91b19559fc12");
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

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

}