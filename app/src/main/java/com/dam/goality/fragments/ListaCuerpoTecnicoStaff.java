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

import com.dam.goality.CuerpoTecnicoDetailActivity;
import com.dam.goality.R;
import com.dam.goality.adapter.CuerpoTecnicoAdapter;
import com.dam.goality.model.CuerpoTecnico;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaCuerpoTecnicoStaff extends Fragment {

//    Button btnAddCuerpoTecnico;
    RecyclerView rvCuerpoTecnico;
    ArrayList<CuerpoTecnico> listaCT;
    CuerpoTecnicoAdapter adapter;

    Chip cpTodos;
    Chip cpEntrenador;
    Chip cpSegundoEntrenador;
    Chip cpEntrenadorPorteros;
    Chip cpreparadorFisico;
    Chip cpNutricionista;
    Chip cpFisioterapeuta;
    Chip cpPsicologo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_cuerpo_tecnico_staff, container, false);

//        btnAddCuerpoTecnico = view.findViewById(R.id.btnAddCuerpoTecnico);

        rvCuerpoTecnico = view.findViewById(R.id.rvCuerpoTecnico);
        rvCuerpoTecnico.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvCuerpoTecnico.setLayoutManager(linearLayoutManager);

        // Chips
        cpTodos = view.findViewById(R.id.cpTodos);
        cpTodos.setChecked(true);
        cpEntrenador = view.findViewById(R.id.cpEntrenador);
        cpSegundoEntrenador = view.findViewById(R.id.cpSegundoEntrenador);
        cpEntrenadorPorteros = view.findViewById(R.id.cpEntrenadorPorteros);
        cpreparadorFisico = view.findViewById(R.id.cpreparadorFisico);
        cpNutricionista = view.findViewById(R.id.cpNutricionista);
        cpFisioterapeuta = view.findViewById(R.id.cpFisioterapeuta);
        cpPsicologo = view.findViewById(R.id.cpPsicologo);

        listaCT = new ArrayList<>();
        adapter = new CuerpoTecnicoAdapter(getContext(), listaCT);
        rvCuerpoTecnico.setAdapter(adapter);

        cargarCuerpoTecnico();

//        btnAddCuerpoTecnico.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), AddCuerpoTecnico.class);
//                startActivityForResult(i, 1);
//            }
//        });

        // Filtrar jugadores
        cpTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cpTodos.isChecked()) {
                    cargarCuerpoTecnico();
                } else {
                    limpiarRecyclerView();
                }
            }
        });

        cpEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpEntrenador, "Entrenador");
            }
        });

        cpSegundoEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpSegundoEntrenador, "Segundo entrenador");
            }
        });

        cpEntrenadorPorteros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpEntrenadorPorteros, "Entrenador de porteros");
            }
        });

        cpreparadorFisico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpreparadorFisico, "Preparador físico");
            }
        });

        cpNutricionista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpNutricionista, "Nutricionista");
            }
        });

        cpFisioterapeuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpFisioterapeuta, "Fisioterapeuta");
            }
        });

        cpPsicologo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarChip(cpPsicologo, "Psicólogo");
            }
        });

        return view;
    }

    private void cargarChip(Chip chip, String tipo) {
        if (chip.isChecked()) {
            seleccionarCT(tipo);
        } else {
            limpiarRecyclerView();
        }
    }

    private void cargarCuerpoTecnico() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CuerpoTecnico");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaCT.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    CuerpoTecnico ct = snap.getValue(CuerpoTecnico.class);
                    listaCT.add(ct);
                }

                adapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvCuerpoTecnico.getChildAdapterPosition(v);
                        CuerpoTecnico cu = listaCT.get(i);

                        Intent intent = new Intent(getContext(), CuerpoTecnicoDetailActivity.class);
                        intent.putExtra("CUERPO_TECNICO", cu);
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

    private void seleccionarCT(String tipo) {

        Query query = FirebaseDatabase.getInstance().getReference("CuerpoTecnico")
                .orderByChild("cargo")
                .equalTo(tipo);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            listaCT.clear();
            if (snapshot.exists()) {
                for (DataSnapshot sna : snapshot.getChildren()) {
                    CuerpoTecnico ct = sna.getValue(CuerpoTecnico.class);
                    listaCT.add(ct);
                }

                adapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvCuerpoTecnico.getChildAdapterPosition(v);
                        CuerpoTecnico cu = listaCT.get(i);

                        Intent intent = new Intent(getContext(), CuerpoTecnicoDetailActivity.class);
                        intent.putExtra("CUERPO_TECNICO", cu);
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
        listaCT.clear();
        adapter.notifyDataSetChanged();
    }
}