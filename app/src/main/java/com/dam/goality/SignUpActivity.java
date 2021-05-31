package com.dam.goality;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    ConstraintLayout cl;
    EditText etNombre;
    EditText etApellido;
    EditText etEmail;
    EditText etPassword;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        cl = findViewById(R.id.cl);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellidos);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        auth = FirebaseAuth.getInstance();
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


        if (sNombre.isEmpty() || sApellido.isEmpty() || sEmail.isEmpty() || sPassword.isEmpty()) {
            Snackbar.make(cl, "Debe agregar todos los campos", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
            pd.dismiss();

        } else if (sPassword.length() < 6) {
            Snackbar.make(cl, "La contraseña debe tener al menos 6 caracteres", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();

        } else {
            Intent i = new Intent(this, SignUpCuerpoTecnico.class);
            i.putExtra("NOMBRE", sNombre);
            i.putExtra("APELLIDO", sApellido);
            i.putExtra("EMAIL", sEmail);
            i.putExtra("PASSWORD", sPassword);
            startActivity(i);
        }
    }

}