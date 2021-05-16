package com.dam.goality.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.goality.AddEntrenamiento;
import com.dam.goality.EntrenamientoDetailActivity;
import com.dam.goality.R;
import com.dam.goality.adapter.EntrenamientoAdapter;
import com.dam.goality.model.Entrenamiento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EntrenamientoFragment extends Fragment {

    DatabaseReference mDatabase;
//    Button btnAddEquipo;
    RecyclerView rvEntrenamientos;
    ArrayList<Entrenamiento> listaEntrenamientos;
    EntrenamientoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);

//        btnAddEquipo = view.findViewById(R.id.btnAddEquipo);
        rvEntrenamientos = view.findViewById(R.id.rvEntrenamientos);

        rvEntrenamientos.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvEntrenamientos.setLayoutManager(linearLayoutManager);

        listaEntrenamientos = new ArrayList<>();
        adapter = new EntrenamientoAdapter(getContext(), listaEntrenamientos);
        rvEntrenamientos.setAdapter(adapter);

        cargarEntrenamentos();

//        btnAddEquipo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), AddEntrenamiento.class);
//                startActivityForResult(i, 1);
//            }
//        });

        return view;
    }

    private void cargarEntrenamentos() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Entrenamientos");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaEntrenamientos.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Entrenamiento ent = snap.getValue(Entrenamiento.class);
                    listaEntrenamientos.add(ent);
                }

                adapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvEntrenamientos.getChildAdapterPosition(v);
                        Entrenamiento entrenamiento = listaEntrenamientos.get(i);

                        Toast.makeText(getContext(), entrenamiento.toString(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getContext(), EntrenamientoDetailActivity.class);
                        intent.putExtra("ENTRENAMIENTO", entrenamiento);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
        }

    }
}