package com.dam.goality.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dam.goality.R;
import com.dam.goality.model.Equipo;
import com.dam.goality.model.Partido;
import com.dam.goality.model.Password;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdministracionFragment extends Fragment {

    ArrayList<Equipo> listaEquipos;
    List<Partido> listaPartidos;
    Button cargarEquipos;
    Button cargarPassword;

    TextView tvPartidosTotales;
    TextView tvPartidosGanados;
    TextView tvPartidosEmpatados;
    TextView tvPartidosPertidos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estadisticas, container, false);

//        tvPartidosTotales = view.findViewById(R.id.tvPartidosTotales);
        tvPartidosGanados = view.findViewById(R.id.tvPartidosGanados);
        tvPartidosEmpatados = view.findViewById(R.id.tvPartidosEmpatados);
        tvPartidosPertidos = view.findViewById(R.id.tvPartidosPertidos);

        cargarEquipos = view.findViewById(R.id.cargarEquipos);
        cargarEquipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarEquipos();
            }
        });

        cargarPassword = view.findViewById(R.id.cargarPassword);
        cargarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarPass();
            }
        });

        return view;
    }

    private void cargarPass() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Password");

        Password pass = new Password("1", "ABC123");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", pass.getId());
        hashMap.put("password", pass.getPassword());

        reference.child(pass.getId()).setValue(hashMap);
    }


    public void cargarEquipos() {
        listaEquipos = new ArrayList<>();

        Equipo equipo1 = new Equipo("1", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FATLETICO%20CHOPERA%20ALCOBENDAS%2004%20A.png?alt=media&token=099c7c40-45e5-4b89-89be-a795020d0e29", "Atlético Chopera Alcobendas 04 A", "Alcobendas - Fuentelucha");
        Equipo equipo2 = new Equipo("2", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FA.D.%20COMPLUTENSE%20ALCALA%20A.png?alt=media&token=40665c90-1633-432d-b6c8-0f23485dc6ac", "A.D. Complutense Alcala A", "Alcalá - Recinto Ferial");
        Equipo equipo3 = new Equipo("3", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FA.D.%20SPORTING%20HORTALEZA%20A.png?alt=media&token=aa054cb6-6ed6-48a4-b70d-6257c3cb6ed3", "A.D. Sporting Hortaleza A", "Pvo. Mpal. Sporting Hortaleza");
        Equipo equipo4 = new Equipo("4", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FA.D.%20TORREJON%20C.F.%20B.png?alt=media&token=6bf991d3-d5eb-4f5a-9ac7-1fb85600b624", "A.D. Torrejón C.F. 'B'", "Torrejón de Ardoz - Est. Mpal. Las Veredillas");
        Equipo equipo5 = new Equipo("5", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FA.D.%20UNION%20ADARVE%20B.png?alt=media&token=6a5cb17c-278a-4081-8f76-d391fefca9e7", "A.D. Union Adarve 'B'", "Barrio del Pilar - ");
        Equipo equipo6 = new Equipo("6", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FARAVACA%20C.F.%20B.png?alt=media&token=dc95bab7-6ba8-4c63-980c-a053ce01883e", "ARAVACA C.F 'B'", "Aravaca - ");
        Equipo equipo7 = new Equipo("7", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FE.F.%20SIETE%20PICOS%20COLMENAR%20A.png?alt=media&token=ee83b3ef-d4f2-4031-9bb4-944b887dd31d", "Siete Picos Colmenar 'A'", "Colmenar Viejo - ");
        Equipo equipo8 = new Equipo("8", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FC.D.%20CANILLAS%20B.png?alt=media&token=82de7f4b-62a8-4fd4-bb82-14f883238d56", "C.D. Canillas 'B'", "");
        Equipo equipo9 = new Equipo("9", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FC.D.%20CHAMARTIN%20VERGARA%20A.png?alt=media&token=79a5a5e8-6aa3-4b0b-9302-f907b1f352c8", "C.D. Chamartín Vergara C.F. 'A'", "Chamartín Vergara");
        Equipo equipo10 = new Equipo("10", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FC.D.%20NUEVO%20BOADILLA%20A.png?alt=media&token=ddeb5fc8-3113-49c0-8f50-45940de7318e", "C.D. Nuevo Boadillla 'A'", "Boadillla - ");
        Equipo equipo11 = new Equipo("11", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FC.D.%20SAN%20ROQUE%20E.F.F.%20A.png?alt=media&token=2b34c297-d6eb-4f2f-b5f2-c01dbd29c7a1", "C.D. San Roque E.F.F 'A'", "San Roque -");
        Equipo equipo12 = new Equipo("12", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FC.F.%20POZUELO%20DE%20ALARCON%20A.png?alt=media&token=e1e8f90b-16f1-4951-9ed7-5999f6eb0413", "C.F. Pozuelo de Alarcon 'A'", "Pozuelo de Alarcon - ");
        Equipo equipo13 = new Equipo("13", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FCLUB%20UNION%20COLLADO%20VILLALBA-PECUNPAY%20A.png?alt=media&token=6a76d14e-6257-4782-ac74-1732b4b6dcd8", "Club Unión Collado Villalba-Pecunpay 'A'", "Collado Villalba -");
        Equipo equipo14 = new Equipo("14", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FDEPORTIVO%20A.V.%20SANTA%20ANA%20A.png?alt=media&token=a5a17177-c78a-4cbb-a6e6-5c86b372ede5", "D.A.V Santa Ana 'A'", "Santa Ana - ");
        Equipo equipo15 = new Equipo("15", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FESCUELA%20DEP.MORATALAZ%20B.png?alt=media&token=1c37da34-c6b5-4e47-9697-a85cc6d17ccd", "E.D Moratalaz 'B'", "Moratalaz - ");
        Equipo equipo16 = new Equipo("16", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FF.C.%20VILLANUEVA%20DEL%20PARDILLO%20A.png?alt=media&token=1790e8df-663b-451f-b1d5-cc944facc371", "F.C. Villanueva del Pardillo 'A'", "Villanueva del Pardillo - ");
        Equipo equipo17 = new Equipo("17", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FR.S.D.%20ALCALA%20S.A.D.%20B.png?alt=media&token=8bbe7797-7c19-4057-8e31-fc558a33967a", "R.S.D Alcala S.A.D 'B'", "");
        Equipo equipo18 = new Equipo("18", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FU.D.%20SAN%20SEBASTIAN%20REYES%20A.png?alt=media&token=653fede9-1be3-49b1-8c98-f744ffd7d36b", "U.D San Sebastian Reyes 'A'", "San Sebastian Reyes - ");
        Equipo equipo19 = new Equipo("19", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FUNION%20ZONA%20NORTE%20A.png?alt=media&token=816be5be-fc13-44d3-90f9-7b8682c6313d", "Unión Zona Norte 'A'", "Fuencarral - ");
        Equipo equipo20 = new Equipo("20", "https://firebasestorage.googleapis.com/v0/b/goality-753fc.appspot.com/o/Equipos%2FREAL%20MADRID.png?alt=media&token=46a0bda2-89e2-42cf-9ecb-fd19511908fc", "Real Madrid 'A'", "Chamartín - Santiago Bernabeu");

        listaEquipos.add(equipo1);
        listaEquipos.add(equipo2);
        listaEquipos.add(equipo3);
        listaEquipos.add(equipo4);
        listaEquipos.add(equipo5);
        listaEquipos.add(equipo6);
        listaEquipos.add(equipo7);
        listaEquipos.add(equipo8);
        listaEquipos.add(equipo9);
        listaEquipos.add(equipo10);
        listaEquipos.add(equipo11);
        listaEquipos.add(equipo12);
        listaEquipos.add(equipo13);
        listaEquipos.add(equipo14);
        listaEquipos.add(equipo15);
        listaEquipos.add(equipo16);
        listaEquipos.add(equipo17);
        listaEquipos.add(equipo18);
        listaEquipos.add(equipo19);
        listaEquipos.add(equipo20);

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