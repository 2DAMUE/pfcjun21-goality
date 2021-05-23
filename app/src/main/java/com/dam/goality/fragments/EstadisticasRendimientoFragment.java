package com.dam.goality.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dam.goality.R;
import com.dam.goality.model.Partido;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EstadisticasRendimientoFragment extends Fragment {

    TextView tvGolesFavor;
    TextView tvGolesContra;
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estadisticas_rendimiento, container, false);

        tvGolesFavor = view.findViewById(R.id.tvGolesFavor);
        tvGolesContra = view.findViewById(R.id.tvGolesContra);
        pieChart = view.findViewById(R.id.pieChart);

        // Cargar info usuario
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Partidos");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Partido> listaPartidos = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot sna : snapshot.getChildren()) {
                        Partido partido = sna.getValue(Partido.class);
                        listaPartidos.add(partido);
                    }
                }

                int golesFavor = 0;
                int golesContra = 0;

                for (Partido partido : listaPartidos) {
                    if (partido.getGolesContrincante() != -1 && partido.getGolesMiEquipo() != -1) {
                        golesFavor += partido.getGolesMiEquipo();
                        golesContra += partido.getGolesContrincante();
                    }
                }

                tvGolesFavor.setText(String.valueOf(golesFavor));
                tvGolesContra.setText(String.valueOf(golesContra));

                ArrayList<PieEntry> records = new ArrayList<>();
                records.add(new PieEntry(golesFavor, "Goles a favor"));
                records.add(new PieEntry(golesContra, "Goles en contra"));
                PieDataSet dataSet = new PieDataSet(records, "Pie Chart Report");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setValueTextSize(22f);
                PieData pieData = new PieData(dataSet);
                pieChart.setData(pieData);
                pieChart.getDescription().setEnabled(true);
                pieChart.setCenterText("Quarterly Recenue");
                pieChart.animateY(1400, Easing.EaseInOutQuad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}