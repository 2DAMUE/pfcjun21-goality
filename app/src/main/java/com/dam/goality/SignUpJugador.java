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
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpJugador extends AppCompatActivity {

    ConstraintLayout cl;

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
    String fecha = "";

    StorageReference storageReference;
    StorageTask uploadTask;
    String myUrl = "";

    DatePickerDialog.OnDateSetListener setListener;

    FirebaseAuth auth;
    DatabaseReference reference;

    String nombre;
    String apellido;
    String email;
    String password;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_jugador);

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
        npEdad = findViewById(R.id.npEdad);

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
        storageReference = FirebaseStorage.getInstance().getReference("Jugadores");
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpJugador.this,
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

    public void registrar(View view) {
        String paisNacimiento = atvNacimiento.getText().toString();
        String posicion = atvCargo.getText().toString();
        String peso = etPeso.getText().toString();
        String estatura = etEstatura.getText().toString();
        int edad = npEdad.getValue();
        int dorsal = npDorsal.getValue();

        if (fecha.isEmpty() || paisNacimiento.equalsIgnoreCase("Selecciona la nacionalidad")
                || posicion.equalsIgnoreCase("Selecciona la posición") || edad == 0
                || peso.isEmpty() || estatura.isEmpty()) {
//            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            Snackbar.make(cl, "Debes rellenar todos los campos", Snackbar.LENGTH_SHORT)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        } else {
            registrarUsuario(paisNacimiento, posicion, Double.parseDouble(peso), Double.parseDouble(estatura), edad, dorsal);
        }
    }

    private void registrarUsuario(String paisNacimiento, String posicion, double peso, double estarura, int edad, int dorsal) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creando perfil");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Jugadores").child(userId);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("nombre", nombre);
                            hashMap.put("apellidos", apellido);
                            hashMap.put("fechaNacimiento", fecha);
                            hashMap.put("fotoPerfilUrl", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Uploads%2Fpp.png?alt=media&token=bae95164-da0f-4a38-a6c3-91b19559fc12");
                            hashMap.put("edad", edad);
                            hashMap.put("nacionalidad", paisNacimiento);
                            hashMap.put("posicion", posicion);
                            hashMap.put("peso", peso);
                            hashMap.put("estatura", estarura);
                            hashMap.put("dorsal", dorsal);
                            hashMap.put("role", tipo);
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SignUpJugador.this, MainActivityJugador.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });


                        } else {
                            progressDialog.dismiss();
//                            Toast.makeText(SignUpCuerpoTecnico.this, "Ya hay un usuario registrado con este mail",
//                                    Toast.LENGTH_SHORT).show();
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