package com.dam.goality;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dam.goality.fragments.AddFragment;
import com.dam.goality.fragments.EntrenamientoFragment;
import com.dam.goality.fragments.EstadisticasFragment;
import com.dam.goality.fragments.PartidoFragment;
import com.dam.goality.fragments.ListaFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivityStaff extends AppCompatActivity {

    //    BottomNavigationView bottomNavigation;
    Fragment selectedFragment = null;
    BottomAppBar bottom_app_bar;
    FloatingActionButton fab;
    TextView titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_staff);

//        bottomNavigation = findViewById(R.id.bottomNavigation);
//        bottomNavigation.setOnNavigationItemSelectedListener(listener);

        selectedFragment = new PartidoFragment();
        titleToolbar = findViewById(R.id.titleToolbar);

        bottom_app_bar = findViewById(R.id.bottom_app_bar);
        bottom_app_bar.setOnMenuItemClickListener(listener2);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new AddFragment();
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
                }
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();

    }

    private androidx.appcompat.widget.Toolbar.OnMenuItemClickListener listener2 = new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.home) {
                titleToolbar.setText("Mis Equipos");
                selectedFragment = new PartidoFragment();

            } else if (item.getItemId() == R.id.calendario) {
                titleToolbar.setText("Calendario");
                selectedFragment = new EntrenamientoFragment();

            } else if (item.getItemId() == R.id.add) {
                selectedFragment = new AddFragment();

            } else if (item.getItemId() == R.id.personas) {
                titleToolbar.setText("Mis Jugadores");
                selectedFragment = new ListaFragment();

            } else if (item.getItemId() == R.id.estadisticas) {
                titleToolbar.setText("Estadísticas");
                selectedFragment = new EstadisticasFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
            }
            return true;
        }
    };

}