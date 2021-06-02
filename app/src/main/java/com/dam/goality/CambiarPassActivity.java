package com.dam.goality;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dam.goality.model.Password;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CambiarPassActivity extends AppCompatActivity {

    ConstraintLayout cl;
    String password;
    String passwordActual;
    String passwordNueva;
    EditText etPass;
    EditText etPassNueva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);

        cl = findViewById(R.id.cl);
        etPass = findViewById(R.id.etPass);
        etPassNueva = findViewById(R.id.etPassNueva);

        // Cargar info usuario
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Password").child("1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Password pass = snapshot.getValue(Password.class);
                password = pass.getPassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void aceptar(View view) {
        passwordActual = etPass.getText().toString();
        passwordNueva = etPassNueva.getText().toString();

        if (passwordActual.isEmpty() || passwordNueva.isEmpty()) {
            Snackbar.make(cl, "Debes introducir todos los campos", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();

        } else {
            if (passwordActual.equals(password)) {

                if (passwordActual.equals(passwordNueva)) {
                    Snackbar.make(cl, "La contraseña nueva es la misma que la actual", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.primary))
                            .show();

                } else {
                    if (passwordNueva.length() < 6) {
                        Snackbar.make(cl, "La contraseña debe tener un mínimo de 6 caracteres", Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.primary))
                                .show();

                    } else {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Password");

                        Password pass = new Password("1", passwordNueva);

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", pass.getId());
                        hashMap.put("password", pass.getPassword());

                        reference.child(pass.getId()).setValue(hashMap);

                        Intent i = new Intent();
                        setResult(RESULT_OK, i);
                        finish();
                    }
                }

            } else {
                Snackbar.make(cl, "La contraseña actual no coincide con la introducida", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.primary))
                        .show();
            }

        }

    }
}