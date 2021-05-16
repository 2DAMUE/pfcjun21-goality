package com.dam.goality.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.dam.goality.R;
import com.dam.goality.model.Equipo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class EstadisticasFragment extends Fragment {

    ArrayList<Equipo> listaEquipos;
    Button cargarEquipos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estadisticas, container, false);

        cargarEquipos = view.findViewById(R.id.cargarEquipos);

        cargarEquipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarEquipos();
            }
        });

        return view;
    }

    public void cargarEquipos() {
        listaEquipos = new ArrayList<>();

        Equipo equipo1 = new Equipo("1", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FA.D.%20COMPLUTENSE%20ALCALA%20A.png?alt=media&token=40665c90-1633-432d-b6c8-0f23485dc6ac", "A.D. Complutense Alcala A", "Alcalá - Recinto Ferial");
        Equipo equipo2 = new Equipo("2", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FA.D.%20SPORTING%20HORTALEZA%20A.png?alt=media&token=aa054cb6-6ed6-48a4-b70d-6257c3cb6ed3", "A.D. Sporting Hortaleza A", "Pvo. Mpal. Sporting Hortaleza");
        Equipo equipo3 = new Equipo("3", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FA.D.%20TORREJON%20C.F.%20B.png?alt=media&token=6bf991d3-d5eb-4f5a-9ac7-1fb85600b624", "A.D. Torrejón C.F. 'B'", "Torrejón de Ardoz - Est. Mpal. Las Veredillas");

        listaEquipos.add(equipo1);
        listaEquipos.add(equipo2);
        listaEquipos.add(equipo3);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ListaEquipos");

        for (Equipo i : listaEquipos) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", i.getId());
            hashMap.put("imageUrl", i.getImageUrl());
            hashMap.put("nombre", i.getNombre());
            hashMap.put("estadio", i.getEstadio());

            reference.child(i.getId()).setValue(hashMap);
        }


    }
}