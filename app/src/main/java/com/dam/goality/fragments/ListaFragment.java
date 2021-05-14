package com.dam.goality.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.dam.goality.R;
import com.google.android.material.tabs.TabLayout;

public class ListaFragment extends Fragment {

    TabLayout tabLayoutAdd;
    FrameLayout flContainer;
    Fragment selectedFragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        tabLayoutAdd = view.findViewById(R.id.tabLayoutAdd);
        tabLayoutAdd.addOnTabSelectedListener(listener);
        flContainer = view.findViewById(R.id.flContainer);

        selectedFragment = new ListaJugadoresFragment();
        getFragmentManager().beginTransaction().replace(R.id.flContainer, selectedFragment).commit();

        return view;
    }

    private TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getPosition() == 0) {
                selectedFragment = new ListaJugadoresFragment();
                getFragmentManager().beginTransaction().replace(R.id.flContainer, selectedFragment).commit();

            } else if (tab.getPosition() == 1) {
                selectedFragment = new ListaCuerpoTecnicoStaff();
                getFragmentManager().beginTransaction().replace(R.id.flContainer, selectedFragment).commit();
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