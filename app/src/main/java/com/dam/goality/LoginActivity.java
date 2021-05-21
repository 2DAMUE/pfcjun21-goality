package com.dam.goality;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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
    RadioButton btnJugadorLogin;
    RadioButton btnStaffLogin;
    TextInputLayout tilLocal;
    AutoCompleteTextView atvLocal;
    TextInputLayout tilVisitante;
    AutoCompleteTextView atvVisitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cl = findViewById(R.id.cl);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        btnJugadorLogin = findViewById(R.id.btnJugadorLogin);
        btnStaffLogin = findViewById(R.id.btnStaffLogin);
        auth = FirebaseAuth.getInstance();
        btnJugadorLogin.setChecked(true);
        tilVisitante = findViewById(R.id.tilVisitante);
        atvVisitante = findViewById(R.id.atvVisitante);
    }

    public void login(View view) {
        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Por favor espere...");
        pd.show();

        String sEmail = emailLogin.getText().toString().trim();
        String sPass = passwordLogin.getText().toString().trim();
        String opcion = (btnJugadorLogin.isChecked()) ? "Jugador" : "Staff";

        if (sEmail.isEmpty() || sPass.isEmpty()) {
//            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();

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


                                if (btnJugadorLogin.isChecked()) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Jugadores")
                                            .child(auth.getCurrentUser().getUid()).child("role");

                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            pd.dismiss();

                                            try {
                                                String userRole = snapshot.getValue().toString();
                                                Toast.makeText(LoginActivity.this, userRole, Toast.LENGTH_SHORT).show();

                                                Intent i;
                                                i = new Intent(LoginActivity.this, MainActivityJugador.class);
                                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(i);
                                                finish();

                                            } catch (NullPointerException e) {
//                                                Toast.makeText(LoginActivity.this, "El usuario ingresado no se encuentra en" +
//                                                        " la lista de jugadores, tal vez pertenece al Staff", Toast.LENGTH_SHORT).show();

                                                Snackbar.make(cl, "El correo ingresado no se encuentra en la lista" +
                                                        " de jugadores", Snackbar.LENGTH_SHORT)
                                                        .setAction("OK", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                            }
                                                        })
                                                        .setActionTextColor(getResources().getColor(R.color.primary))
                                                        .show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            pd.dismiss();
                                        }
                                    });
                                } else {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("CuerpoTecnico")
                                            .child(auth.getCurrentUser().getUid()).child("role");

                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            pd.dismiss();

                                            try {

                                                String userRole = snapshot.getValue().toString();

                                                Intent i;
                                                i = new Intent(LoginActivity.this, MainActivityStaff.class);
                                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(i);
                                                finish();

                                            } catch (NullPointerException e) {
                                                Toast.makeText(LoginActivity.this, "El usuario ingresado no se encuentra en" +
                                                        " la lista de staff, tal vez pertenece a los jugadores", Toast.LENGTH_SHORT).show();

                                                Snackbar.make(cl, "El correo ingresado no se encuentra en la lista" +
                                                        " de cuerpo técnico", Snackbar.LENGTH_SHORT)
                                                        .setAction("OK", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                            }
                                                        })
                                                        .setActionTextColor(getResources().getColor(R.color.primary))
                                                        .show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            pd.dismiss();
                                        }
                                    });
                                }


                            } else {
                                pd.dismiss();
//                                Toast.makeText(LoginActivity.this, "La autenticación ha fallado", Toast.LENGTH_SHORT).show();

                                Snackbar.make(cl, "Email o contraseña incorrectos", Snackbar.LENGTH_SHORT)
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