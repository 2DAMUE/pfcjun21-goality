package com.dam.goality;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AddJugadorOpciones extends AppCompatActivity {

    RelativeLayout rl1;
    RelativeLayout rl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jugador_opciones);

        rl1 = findViewById(R.id.rl1);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddJugadorOpciones.this, SignUpJugador_2.class);
                startActivity(i);
            }
        });

        rl2 = findViewById(R.id.rl2);
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddJugadorOpciones.this, AddJugador.class);
                startActivity(i);
            }
        });
    }
}