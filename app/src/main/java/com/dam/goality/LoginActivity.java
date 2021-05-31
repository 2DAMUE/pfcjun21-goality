package com.dam.goality;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ConstraintLayout cl;
    EditText emailLogin;
    EditText passwordLogin;
    FirebaseAuth auth;
    TextInputLayout tilVisitante;
    AutoCompleteTextView atvVisitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cl = findViewById(R.id.cl);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        auth = FirebaseAuth.getInstance();
        tilVisitante = findViewById(R.id.tilVisitante);
        atvVisitante = findViewById(R.id.atvVisitante);
    }

    public void login(View view) {
        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Por favor espere...");
        pd.show();

        String sEmail = emailLogin.getText().toString().trim();
        String sPass = passwordLogin.getText().toString().trim();

        if (sEmail.isEmpty() || sPass.isEmpty()) {
            Snackbar.make(cl, "Debes rellenar todos los campos", Snackbar.LENGTH_SHORT)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();

            pd.dismiss();

        } else {
            auth.signInWithEmailAndPassword(sEmail, sPass)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("CuerpoTecnico")
                                        .child(auth.getCurrentUser().getUid());

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        pd.dismiss();
                                        Intent i = new Intent(LoginActivity.this, MainActivityStaff.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        pd.dismiss();
                                    }
                                });
                            } else {
                                pd.dismiss();
                                Snackbar.make(cl, "La autenticación ha fallado", Snackbar.LENGTH_LONG)
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

    public void irSignup(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            startActivity(new Intent(this, MainActivityStaff.class));
        }
    }
}