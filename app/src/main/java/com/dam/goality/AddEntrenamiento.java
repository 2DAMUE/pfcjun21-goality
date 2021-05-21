package com.dam.goality;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.dam.goality.dialogFragments.DatePickerFragment;
import com.dam.goality.dialogFragments.TimePickerFragment;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddEntrenamiento extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    TextView horaEntrenamiento;
    TextView fechaEntrenamiento;
    Slider slider;
    EditText etDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entrenamiento);

        horaEntrenamiento = findViewById(R.id.horaEntrenamiento);
        fechaEntrenamiento = findViewById(R.id.fechaEntrenamiento);
        slider = findViewById(R.id.slider);
        etDescripcion = findViewById(R.id.etDescripcion);

    }

    public void seleccionarHora(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    public void seleccionarFecha(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    public void aceptar(View view) {
        String hora = horaEntrenamiento.getText().toString();
        String fecha = fechaEntrenamiento.getText().toString();

        if (hora.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Entrenamientos");
            String EntrenamientoId = reference.push().getKey();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", EntrenamientoId);
            hashMap.put("horaEntrenamiento", hora);
            hashMap.put("fechaEntrenamiento", fecha);
            hashMap.put("duracion", slider.getValue());
            hashMap.put("lugar", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Estadios%2Ffutbol-fuentelucha2.jpg?alt=media&token=b2ec4343-2887-4372-8455-c2a3be1537f6");
            hashMap.put("descripcion", etDescripcion.getText().toString());

            reference.child(EntrenamientoId).setValue(hashMap);

            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        }

        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        fechaEntrenamiento.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        horaEntrenamiento.setText(hourOfDay + ":" + minute);
    }
}