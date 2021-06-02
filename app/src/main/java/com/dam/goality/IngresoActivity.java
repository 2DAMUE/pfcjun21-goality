package com.dam.goality;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class IngresoActivity extends AppCompatActivity {

    ConstraintLayout cl;
    Button btnAceptarPass;
    EditText etPass;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        cl = findViewById(R.id.cl);
        btnAceptarPass = findViewById(R.id.btnAceptarPass);
        etPass = findViewById(R.id.etPass);

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

        btnAceptarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String miPass = etPass.getText().toString();

                if (miPass.equals(password)) {
                    Intent i = new Intent(IngresoActivity.this, SignUpActivity.class);
                    startActivity(i);

                } else {
                    Snackbar.make(cl, "El código introducido es incorrecto", Snackbar.LENGTH_LONG)
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