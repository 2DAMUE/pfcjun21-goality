package com.dam.goality;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText etNombre;
    EditText etApellido;
    EditText etEmail;
    EditText etPassword;
    RadioButton rbtnJugador;
    RadioButton rbtnStaff;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellidos);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        rbtnJugador = findViewById(R.id.rbtnJugador);
        rbtnStaff = findViewById(R.id.rbtnStaff);

        auth = FirebaseAuth.getInstance();
        rbtnJugador.setChecked(true);
    }

    public void irLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void signUpUser(View view) {
        pd = new ProgressDialog(SignUpActivity.this);
        pd.setMessage("Por favor espere...");
        pd.show();

        String sNombre = etNombre.getText().toString().trim();
        String sApellido = etApellido.getText().toString().trim();
        String sEmail = etEmail.getText().toString().trim();
        String sPassword = etPassword.getText().toString().trim();
        String tipo = (rbtnJugador.isChecked()) ? "Jugador" : "Staff";


        if (sNombre.isEmpty() || sApellido.isEmpty() || sEmail.isEmpty() || sPassword.isEmpty()) {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            pd.dismiss();

        } else if (sPassword.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

        } else {
            if (tipo.equalsIgnoreCase("Staff")) {
                Intent i = new Intent(this, SignUpCuerpoTecnico.class);
                i.putExtra("NOMBRE", sNombre);
                i.putExtra("APELLIDO", sApellido);
                i.putExtra("EMAIL", sEmail);
                i.putExtra("PASSWORD", sPassword);
                i.putExtra("TIPO", tipo);
                startActivity(i);
            }

            if (tipo.equalsIgnoreCase("Jugador")) {

                Intent i = new Intent(this, SignUpJugador.class);
                i.putExtra("NOMBRE", sNombre);
                i.putExtra("APELLIDO", sApellido);
                i.putExtra("EMAIL", sEmail);
                i.putExtra("PASSWORD", sPassword);
                i.putExtra("TIPO", tipo);
                startActivity(i);
            }
//            registrarUsuario(sNombre, sApellido, sEmail, sPassword, tipo);
//            Toast.makeText(this, tipo, Toast.LENGTH_SHORT).show();
        }
    }

    private void registrarUsuario(String sNombre, String sApellido, String sEmail, String sPassword, String tipo) {
        auth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            if (tipo.equalsIgnoreCase("Jugador")) {
                                reference = FirebaseDatabase.getInstance().getReference().child("Jugadores").child(userId);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("id", userId);
                                hashMap.put("fullname", sNombre);
                                hashMap.put("username", sApellido);
                                hashMap.put("fotoPerfilUrl", "");
                                hashMap.put("bio", "");
                                hashMap.put("role", tipo);
                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            pd.dismiss();
                                            Intent intent = new Intent(SignUpActivity.this, MainActivityJugador.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } else {
                                reference = FirebaseDatabase.getInstance().getReference().child("CuerpoTecnico").child(userId);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("id", userId);
                                hashMap.put("fullname", sNombre);
                                hashMap.put("username", sApellido);
                                hashMap.put("fotoPerfilUrl", "");
                                hashMap.put("bio", "");
                                hashMap.put("role", tipo);
                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            pd.dismiss();
                                            Intent intent = new Intent(SignUpActivity.this, MainActivityStaff.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }


                        } else {
                            pd.dismiss();
                            Toast.makeText(SignUpActivity.this, "Ya hay un usuario registrado con este mail",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}