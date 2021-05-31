package com.dam.goality.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.goality.PartidoDetailActivity;
import com.dam.goality.R;
import com.dam.goality.adapter.PartidosAdapter;
import com.dam.goality.model.Partido;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PartidoFragment extends Fragment {

    //    Button btnAddEquipo;
    Chip cpPartidos;
    Chip cpLocal;
    Chip cpVisitante;
    RecyclerView rvPartidos;
    List<Partido> listaPartidos;
    PartidosAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_partido, container, false);

//        btnAddEquipo = view.findViewById(R.id.btnAddEquipo);

        cpPartidos = view.findViewById(R.id.cpPartidos);
        cpPartidos.setChecked(true);
        cpLocal = view.findViewById(R.id.cpLocal);
        cpVisitante = view.findViewById(R.id.cpVisitante);
        rvPartidos = view.findViewById(R.id.rvPartidos);
        rvPartidos.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvPartidos.setLayoutManager(linearLayoutManager);

        listaPartidos = new ArrayList<>();
        adapter = new PartidosAdapter(getContext(), listaPartidos);
        rvPartidos.setAdapter(adapter);

//        btnAddEquipo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), AddPartido.class);
//                startActivityForResult(i, 3);
//            }
//        });

        cargarPartidos();

        cpPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cpPartidos.isChecked()) {
                    cargarPartidos();
                } else {
                    limpiarRecyclerView();
                }
            }
        });

        cpLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cpLocal.isChecked()) {
                    filtrarPartidos("Local");
                } else {
                    limpiarRecyclerView();
                }
            }
        });

        cpVisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cpVisitante.isChecked()) {
                    filtrarPartidos("Visitante");
                } else {
                    limpiarRecyclerView();
                }
            }
        });

        return view;
    }

    private void limpiarRecyclerView() {
        listaPartidos.clear();
        adapter.notifyDataSetChanged();
    }

    private void cargarPartidos() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Partidos");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaPartidos.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Partido partido = snap.getValue(Partido.class);
                    listaPartidos.add(partido);
                }

                adapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvPartidos.getChildAdapterPosition(v);
                        Partido partido = listaPartidos.get(i);

                        Intent intent = new Intent(getContext(), PartidoDetailActivity.class);
                        intent.putExtra("PARTIDO", partido);
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

    public void filtrarPartidos(String condicion) {
        Query query = FirebaseDatabase.getInstance().getReference("Partidos")
                .orderByChild("condicion")
                .equalTo(condicion);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            listaPartidos.clear();
            if (snapshot.exists()) {
                for (DataSnapshot sna : snapshot.getChildren()) {
                    Partido partido = sna.getValue(Partido.class);
                    listaPartidos.add(partido);
                }

//                adapter.setListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int i = rvPartidos.getChildAdapterPosition(v);
//                        Jugador jugador = listaPartidos.get(i);
//
//                        Intent intent = new Intent(getContext(), JugadorDetailActivity.class);
//                        intent.putExtra("JUGADOR", jugador);
//                        startActivity(intent);
//                    }
//                });
//
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

//        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
//            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
//        }

    }

}