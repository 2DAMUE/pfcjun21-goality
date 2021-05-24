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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dam.goality.model.Password;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpJugador_2 extends AppCompatActivity {

    TextInputLayout tilNacimiento;
    AutoCompleteTextView atvNacimiento;
    ArrayList<String> listaPaises;
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
    NumberPicker npEdad;
    MaterialButton btnDate;


    TextView etNombreJ;
    TextView etApellidoJ;
    TextView etEmailJ;
    EditText etPasswordJu;
    FirebaseAuth auth;
    DatePickerDialog.OnDateSetListener setListener;

    //Atributos
    String nombre;
    String apellido;
    String email;
    String password;
    String tipo;
    String fecha = "";

    Password p;

    StorageReference storageReference;
    StorageTask uploadTask;
    String myUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_jugador_2);

        etNombreJ = findViewById(R.id.etNombreJ);
        etApellidoJ = findViewById(R.id.etApellidoJ);
        etEmailJ = findViewById(R.id.etEmailJ);
        etPasswordJu = findViewById(R.id.etPasswordJu);

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
        npEdad = findViewById(R.id.npEdad);

        auth = FirebaseAuth.getInstance();

        // Obtener nombre, apellidos, email, password
        nombre = getIntent().getStringExtra("NOMBRE");
        apellido = getIntent().getStringExtra("APELLIDO");
        email = getIntent().getStringExtra("EMAIL");
        password = getIntent().getStringExtra("PASSWORD");
        tipo = getIntent().getStringExtra("TIPO");

        // Number picker edad
        npEdad.setMaxValue(4);
        npEdad.setMaxValue(99);

        // Number picker dorsal
        npDorsal = findViewById(R.id.npDorsal);
        npDorsal.setMaxValue(1);
        npDorsal.setMaxValue(99);

        // Firebase
        auth = FirebaseAuth.getInstance();

        // Calendario
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Seleccionar fecha de nacimiento
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpJugador_2.this,
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

        // Firebase
        storageReference = FirebaseStorage.getInstance().getReference("Jugadores");

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

        // Cargar contraseña
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Password").child("1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Password pass = snapshot.getValue(Password.class);
                etPasswordJu.setText(pass.getPassword());
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
            Glide.with(ivFotoJugador.getContext()).load(imageUri)
                    .into(ivFotoJugador);
        }
    }

    public void registrar(View view) {
        String nombre = etNombreJ.getText().toString().trim();
        String apellido = etApellidoJ.getText().toString().trim();
        String email = etEmailJ.getText().toString().trim();
        String password = etPasswordJu.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            registrarUsuario(nombre, apellido, email, password);
        }
    }

    private void registrarUsuario(String nombre, String apellido, String email, String password) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creando perfil");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            upload(etPeso.getText().toString().trim(), etEstatura.getText().toString().trim());

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpJugador_2.this, "Ya hay un usuario registrado con este mail",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                        hashMap.put("nombre", etNombreJ.getText().toString());
                        hashMap.put("apellidos", etApellidoJ.getText().toString());
                        hashMap.put("fechaNacimiento", fecha);
                        hashMap.put("fotoPerfilUrl", myUrl);
                        hashMap.put("edad", npEdad.getValue());
                        hashMap.put("nacionalidad", atvNacimiento.getText().toString());
                        hashMap.put("posicion", atvCargo.getText().toString());
                        hashMap.put("peso", Double.parseDouble(peso));
                        hashMap.put("estatura", Double.parseDouble(estatura));
                        hashMap.put("dorsal", npDorsal.getValue());
                        hashMap.put("role", "Jugador");

                        reference.child(id).setValue(hashMap);
                        progressDialog.dismiss();

                        Intent i = new Intent();
                        setResult(RESULT_OK, i);
                        finish();

                    } else {
                        Toast.makeText(SignUpJugador_2.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpJugador_2.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            hashMap.put("nombre", etNombreJ.getText().toString());
            hashMap.put("apellidos", etApellidoJ.getText().toString());
            hashMap.put("fechaNacimiento", fecha);
            hashMap.put("fotoPerfilUrl", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Uploads%2Fpp.png?alt=media&token=bae95164-da0f-4a38-a6c3-91b19559fc12");
            hashMap.put("edad", npEdad.getValue());
            hashMap.put("nacionalidad", atvNacimiento.getText().toString());
            hashMap.put("posicion", atvCargo.getText().toString());
            hashMap.put("peso", Double.parseDouble(peso));
            hashMap.put("estatura", Double.parseDouble(estatura));
            hashMap.put("dorsal", npDorsal.getValue());
            hashMap.put("role", "Jugador");

            reference.child(id).setValue(hashMap);
            progressDialog.dismiss();

            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        }
    }

}