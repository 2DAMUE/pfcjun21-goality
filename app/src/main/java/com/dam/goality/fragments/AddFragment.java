package com.dam.goality.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dam.goality.AddCuerpoTecnico;
import com.dam.goality.AddEntrenamiento;
import com.dam.goality.AddJugador;
import com.dam.goality.AddPartido;
import com.dam.goality.R;
import com.google.android.material.button.MaterialButton;

public class AddFragment extends Fragment {

    MaterialButton btnAddPartido;
    MaterialButton btnAddEntrenamiento;
    MaterialButton btnAddJugador;
    MaterialButton btnAddCuerpoTecnico;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        btnAddPartido = view.findViewById(R.id.btnAddPartido);
        btnAddEntrenamiento = view.findViewById(R.id.btnAddEntrenamiento);
        btnAddJugador = view.findViewById(R.id.btnAddJugador);
        btnAddCuerpoTecnico = view.findViewById(R.id.btnAddCuerpoTecnico);

        btnAddPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddPartido.class);
                startActivityForResult(i, 3);
            }
        });

        btnAddEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddEntrenamiento.class);
                startActivityForResult(i, 4);
            }
        });

        btnAddJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddJugador.class);
                startActivityForResult(i, 1);
            }
        });

        btnAddCuerpoTecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddCuerpoTecnico.class);
                startActivityForResult(i, 2);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
        }

    }
}