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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.dam.goality.dialogFragments.DatePickerFragment;
import com.dam.goality.dialogFragments.TimePickerFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddPartido extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    TextView horaPartido;
    TextView fechaPartido;
    Spinner spnVisitante;
    RadioButton btnLocal;
    RadioButton btnVisitante;
    TextInputLayout tilLocal;
    AutoCompleteTextView atvLocal;
    ArrayAdapter<String> adapter;
    TextInputLayout tilVisitante;
    AutoCompleteTextView atvVisitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partido);

        spnVisitante = findViewById(R.id.spnVisitante);
        horaPartido = findViewById(R.id.horaPartido);
        fechaPartido = findViewById(R.id.fechaPartido);
        btnLocal = findViewById(R.id.btnLocal);
        btnVisitante = findViewById(R.id.btnVisitante);
        btnLocal.setChecked(true);

        ArrayList<String> listaEquipos = new ArrayList<>();
        listaEquipos.add("Equipo 1");
        listaEquipos.add("Equipo 2");
        listaEquipos.add("Equipo 3");

        // Drop down equipo local
        tilLocal = findViewById(R.id.tilLocal);
        atvLocal = findViewById(R.id.atvLocal);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaEquipos);
        atvLocal.setAdapter(adapter);
        atvLocal.setThreshold(1);

        // Drop down equipo visitante
        tilVisitante = findViewById(R.id.tilVisitante);
        atvVisitante = findViewById(R.id.atvVisitante);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaEquipos);
        atvVisitante.setAdapter(adapter);
        atvVisitante.setThreshold(1);

        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarBotones(true);
            }
        });

        btnVisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarBotones(false);
            }
        });

        habilitarBotones(true);

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
            hashMap.put("visitante", visitante);
            hashMap.put("horaPartido", hora);
            hashMap.put("fechaPartido", fecha);

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