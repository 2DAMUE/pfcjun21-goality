package com.dam.goality.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Pie;
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
import java.util.List;

public class EstadisticasPrueba extends Fragment {

    List<Partido> listaPartidos;
    TextView tvPartidosTotales;
    TextView tvPartidosGanados;
    TextView tvPartidosEmpatados;
    TextView tvPartidosPertidos;

    AnyChartView any_chart_view;
    PieChart pieChart;

    Pie pie;
    List<DataEntry> dataEntries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estadisticas_prueba, container, false);

//        tvPartidosTotales = view.findViewById(R.id.tvPartidosTotales);
        tvPartidosGanados = view.findViewById(R.id.tvPartidosGanados);
        tvPartidosEmpatados = view.findViewById(R.id.tvPartidosEmpatados);
        tvPartidosPertidos = view.findViewById(R.id.tvPartidosPertidos);
        pieChart = view.findViewById(R.id.pieChart);

//        any_chart_view = view.findViewById(R.id.any_chart_view);
        listaPartidos = new ArrayList<>();

        cargarPartidos();
        cargarResultados();
        return view;
    }

    private void cargarResultados() {
        int empatados = 0;
        for (Partido p : listaPartidos) {
            if (p.getGolesContrincante() == p.getGolesMiEquipo()) {
                empatados++;
            }
        }
        Toast.makeText(getContext(), "Empatados: " + empatados, Toast.LENGTH_SHORT).show();
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

                int disputados = 0;
                int sinJugar = 0;
                int empatados = 0;
                int perdidos = 0;
                int ganados = 0;

                for (Partido p : listaPartidos) {
                    if (p.getGolesMiEquipo() == p.getGolesContrincante()) {
                        if (p.getGolesMiEquipo() < 0 && p.getGolesContrincante() < 0) {
                            sinJugar++;
                        }

                        if (p.getGolesMiEquipo() >= 0 && p.getGolesContrincante() >= 0) {
                            empatados++;
                        }
                    } else if (p.getGolesMiEquipo() < p.getGolesContrincante()) {
                        perdidos++;
                    } else if (p.getGolesMiEquipo() > p.getGolesContrincante()) {
                        ganados++;
                    }
                }

                ArrayList<PieEntry> records = new ArrayList<>();
                records.add(new PieEntry(ganados, "Ganados"));
                records.add(new PieEntry(empatados, "Empatados"));
                records.add(new PieEntry(perdidos, "Perdidos"));
                PieDataSet dataSet = new PieDataSet(records, "Pie Chart Report");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setValueTextSize(22f);
                PieData pieData = new PieData(dataSet);
                pieChart.setData(pieData);
                pieChart.getDescription().setEnabled(true);
                pieChart.setCenterText("Quarterly Recenue");
                pieChart.animateY(1400, Easing.EaseInOutQuad);

//                tvPartidosTotales.setText(String.valueOf(ganados + empatados + perdidos));
                tvPartidosGanados.setText(String.valueOf(ganados));
                tvPartidosEmpatados.setText(String.valueOf(empatados));
                tvPartidosPertidos.setText(String.valueOf(perdidos));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}