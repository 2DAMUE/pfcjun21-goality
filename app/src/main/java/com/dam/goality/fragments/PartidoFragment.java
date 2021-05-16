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

import com.dam.goality.AddPartido;
import com.dam.goality.PartidoDetailActivity;
import com.dam.goality.R;
import com.dam.goality.adapter.PartidosAdapter;
import com.dam.goality.model.Partido;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PartidoFragment extends Fragment {

//    Button btnAddEquipo;
    RecyclerView rvPartidos;
    List<Partido> listaPartidos;
    PartidosAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        btnAddEquipo = view.findViewById(R.id.btnAddEquipo);

        rvPartidos = view.findViewById(R.id.rvPartidos);
        rvPartidos.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvPartidos.setLayoutManager(linearLayoutManager);

        listaPartidos = new ArrayList<>();
        adapter = new PartidosAdapter(getContext(), listaPartidos);
        rvPartidos.setAdapter(adapter);

        cargarPartidos();

//        btnAddEquipo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), AddPartido.class);
//                startActivityForResult(i, 3);
//            }
//        });

        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
        }

    }

}