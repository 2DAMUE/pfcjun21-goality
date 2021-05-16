package com.dam.goality;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.dam.goality.dialogFragments.DatePickerFragment;
import com.dam.goality.dialogFragments.TimePickerFragment;
import com.dam.goality.model.Equipo;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddPartido extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    TextView horaPartido;
    TextView fechaPartido;
    Spinner spnVisitante;
    TextInputLayout tilLocal;
    AutoCompleteTextView atvLocal;
    ArrayAdapter<String> adapter;
    TextInputLayout tilVisitante;
    AutoCompleteTextView atvVisitante;

    ArrayList<Equipo> lista;
    ArrayList<String> listaEquipos;

    DatabaseReference reference;

    int posLocal;
    int posVisitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partido);

        spnVisitante = findViewById(R.id.spnVisitante);
        horaPartido = findViewById(R.id.horaPartido);
        fechaPartido = findViewById(R.id.fechaPartido);

        listaEquipos = new ArrayList<>();
//        listaEquipos.add("Equipo 1");
//        listaEquipos.add("Equipo 2");
//        listaEquipos.add("Equipo 3");

        cargarEquipos();

        // Drop down equipo local
        tilLocal = findViewById(R.id.tilLocal);
        atvLocal = findViewById(R.id.atvLocal);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaEquipos);
        atvLocal.setAdapter(adapter);
        atvLocal.setThreshold(1);
        atvLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posLocal = position;
            }
        });

        // Drop down equipo visitante
        tilVisitante = findViewById(R.id.tilVisitante);
        atvVisitante = findViewById(R.id.atvVisitante);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaEquipos);
        atvVisitante.setAdapter(adapter);
        atvVisitante.setThreshold(1);
        atvVisitante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posVisitante = position;
            }
        });

    }

    public void cargarEquipos() {
        lista = new ArrayList<Equipo>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ListaEquipos");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Equipo e = snap.getValue(Equipo.class);
                    lista.add(e);
                }

                for (Equipo eq : lista) {
                    listaEquipos.add(eq.getNombre());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void habilitarBotones(Boolean b) {
        atvLocal.setEnabled(b);
        atvVisitante.setEnabled(!b);
    }

    public void seleccionarHora(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    public void seleccionarFecha(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        horaPartido.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        fechaPartido.setText(currentDate);
    }


    public void aceptar(View view) {
        String local = atvLocal.getText().toString();
        String visitante = atvVisitante.getText().toString();
        String hora = horaPartido.getText().toString();
        String fecha = fechaPartido.getText().toString();

        if (local.isEmpty() || visitante.isEmpty() || hora.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Partidos");
            String partidoId = reference.push().getKey();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", partidoId);
            hashMap.put("local", local);
            hashMap.put("imgLocal", lista.get(posLocal).getImageUrl());
            hashMap.put("visitante", visitante);
            hashMap.put("imgVisitante", lista.get(posVisitante).getImageUrl());
            hashMap.put("horaPartido", hora);
            hashMap.put("fechaPartido", fecha);
            hashMap.put("estadio", lista.get(posLocal).getEstadio());

            reference.child(partidoId).setValue(hashMap);

            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        }
    }

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}