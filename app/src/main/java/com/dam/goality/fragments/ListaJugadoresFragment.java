package com.dam.goality.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.goality.JugadorDetailActivity;
import com.dam.goality.R;
import com.dam.goality.adapter.JugadorAdapter;
import com.dam.goality.model.Jugador;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaJugadoresFragment extends Fragment {

    RecyclerView rvJugadores;
    List<Jugador> listaJugadores;
    JugadorAdapter adapter;
//    Button btnAddEquipo;

    // Chips
    Chip cpTodos;
    Chip cpPorteros;
    Chip cpDefensas;
    Chip cpMediocentros;
    Chip cpDelanteros;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_jugadores, container, false);

//        btnAddEquipo = view.findViewById(R.id.btnAddEquipo);

        rvJugadores = view.findViewById(R.id.rvJugadores);
        rvJugadores.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvJugadores.setLayoutManager(linearLayoutManager);

        cpTodos = view.findViewById(R.id.cpTodos);
        cpTodos.setChecked(true);
        cpPorteros = view.findViewById(R.id.cpPorteros);
        cpDefensas = view.findViewById(R.id.cpDefensas);
        cpMediocentros = view.findViewById(R.id.cpMediocentros);
        cpDelanteros = view.findViewById(R.id.cpDplanteros);

        listaJugadores = new ArrayList<>();
        adapter = new JugadorAdapter(getContext(), listaJugadores);
        rvJugadores.setAdapter(adapter);

        cargarJugadores();

//        btnAddEquipo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), AddJugador.class);
//                startActivityForResult(i, 1);
//            }
//        });

        // Filtrar jugadores
        cpTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cpTodos.isChecked()) {
                    cargarJugadores();
                } else {
                    limpiarRecyclerView();
                }

            }
        });

        cpPorteros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpPorteros, "Portero");
            }
        });

        cpDefensas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpDefensas, "Defensa");
            }
        });

        cpMediocentros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpMediocentros, "Mediocentro");
            }
        });

        cpDelanteros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpDelanteros, "Delantero");
            }
        });

        return view;
    }

    private void cargarChip(Chip chip, String tipo) {
        if (chip.isChecked()) {
            seleccionarJugadores(tipo);
        } else {
            limpiarRecyclerView();
        }
    }

    private void cargarJugadores() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jugadores");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaJugadores.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Jugador jug = snap.getValue(Jugador.class);
                    listaJugadores.add(jug);
                }

                adapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvJugadores.getChildAdapterPosition(v);
                        Jugador jugador = listaJugadores.get(i);

                        Intent intent = new Intent(getContext(), JugadorDetailActivity.class);
                        intent.putExtra("JUGADOR", jugador);
                        startActivity(intent);
                    }
                });

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void seleccionarJugadores(String tipo) {

        Query query = FirebaseDatabase.getInstance().getReference("Jugadores")
                .orderByChild("posicion")
                .equalTo(tipo);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            listaJugadores.clear();
            if (snapshot.exists()) {
                for (DataSnapshot sna : snapshot.getChildren()) {
                    Jugador jug = sna.getValue(Jugador.class);
                    listaJugadores.add(jug);
                }

                adapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvJugadores.getChildAdapterPosition(v);
                        Jugador jugador = listaJugadores.get(i);

                        Intent intent = new Intent(getContext(), JugadorDetailActivity.class);
                        intent.putExtra("JUGADOR", jugador);
                        startActivity(intent);
                    }
                });

                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarRecyclerView() {
        listaJugadores.clear();
        adapter.notifyDataSetChanged();
    }
}