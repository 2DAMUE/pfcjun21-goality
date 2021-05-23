package com.dam.goality.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.dam.goality.R;
import com.google.android.material.tabs.TabLayout;

public class EstadisticasFragment extends Fragment {

    TabLayout tab;
    FrameLayout flEstadisticas;
    Fragment selectedFragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estadisticas2, container, false);

        tab = view.findViewById(R.id.tab);
        tab.addOnTabSelectedListener(listener);
        flEstadisticas = view.findViewById(R.id.flEstadisticas);

        selectedFragment = new EstadisticasPrueba();
        getFragmentManager().beginTransaction().replace(R.id.flEstadisticas, selectedFragment).commit();

        return view;
    }

    private TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getPosition() == 0) {
                selectedFragment = new EstadisticasPrueba();
                getFragmentManager().beginTransaction().replace(R.id.flEstadisticas, selectedFragment).commit();

            } else if (tab.getPosition() == 1) {
                selectedFragment = new EstadisticasRendimientoFragment();
                getFragmentManager().beginTransaction().replace(R.id.flEstadisticas, selectedFragment).commit();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}