package com.dam.goality;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.dam.goality.fragments.EntrenamientoFragment;
import com.dam.goality.fragments.EstadisticasFragment;
import com.dam.goality.fragments.ListaFragment;
import com.dam.goality.fragments.PartidoFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityStaff extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //    BottomNavigationView bottomNavigation;
    ConstraintLayout cl;
    Fragment selectedFragment = null;
    BottomAppBar bottom_app_bar;
    MaterialToolbar toolbar;
    FloatingActionButton fab;
    TextView titleToolbar;
    FirebaseAuth firebaseAuth;
    BottomSheetDialog bottomSheetDialog;

    // navigationDrawer
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_staff);

        firebaseAuth = FirebaseAuth.getInstance();
        selectedFragment = new PartidoFragment();
        titleToolbar = findViewById(R.id.titleToolbar);

// Navigation Drawr
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        cl = findViewById(R.id.cl);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(listener);
        bottom_app_bar = findViewById(R.id.bottom_app_bar);
        bottom_app_bar.setOnMenuItemClickListener(listener2);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(MainActivityStaff.this, R.style.BottomSheetTheme);
                View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_layout, (ViewGroup) findViewById(R.id.bottom_sheet));

                sheetView.findViewById(R.id.addJugador).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivityStaff.this, AddJugador.class);
                        startActivityForResult(i, 1);
                        bottomSheetDialog.dismiss();
                    }
                });

                sheetView.findViewById(R.id.addCuerpoTecnico).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivityStaff.this, AddCuerpoTecnico.class);
                        startActivityForResult(i, 2);
                        bottomSheetDialog.dismiss();
                    }
                });

                sheetView.findViewById(R.id.addPartido).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivityStaff.this, AddPartido.class);
                        startActivityForResult(i, 3);
                        bottomSheetDialog.dismiss();
                    }
                });

                sheetView.findViewById(R.id.addEntrenamiento).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivityStaff.this, AddEntrenamiento.class);
                        startActivityForResult(i, 4);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();

    }

    // Abre activities tras pulsar en elementos del NavigationDrawer
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_miperfil) {
            Intent i = new Intent(this, MiPerfilActivity.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.nav_cambiar_pass) {
            Intent i = new Intent(this, CambiarPassActivity.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.nav_aboutus) {
            Intent i = new Intent(this, AboutUsActivity.class);
            startActivity(i);


        } else if (item.getItemId() == R.id.nav_logout) {
            MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(this);
            alertDialog.setMessage("??Est??s seguro que deseas salir?");
            alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    firebaseAuth.signOut();
                    finish();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.create().show();
        }

        return true;
    }

    private androidx.appcompat.widget.Toolbar.OnMenuItemClickListener listener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.miPerfil) {
                MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(MainActivityStaff.this);
                alertDialog.setMessage("??Est??s seguro que deseas salir?");
                alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.create().show();

            } else if (item.getItemId() == R.id.editarPerfil) {
                Intent i = new Intent(MainActivityStaff.this, MiPerfilActivity.class);
                startActivityForResult(i, 5);

            } else if (item.getItemId() == R.id.passEquipo) {
                Intent i = new Intent(MainActivityStaff.this, CambiarPassActivity.class);
                startActivityForResult(i, 6);

            }

            return true;
        }
    };

    private androidx.appcompat.widget.Toolbar.OnMenuItemClickListener listener2 = new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.home) {
                titleToolbar.setText("Mis Partidos");
                selectedFragment = new PartidoFragment();

            } else if (item.getItemId() == R.id.calendario) {
                titleToolbar.setText("Mis Entrenamientos");
                selectedFragment = new EntrenamientoFragment();

            } else if (item.getItemId() == R.id.personas) {
                titleToolbar.setText("Mi Equipos");
                selectedFragment = new ListaFragment();

//            } else if (item.getItemId() == R.id.estadisticas) {
//                titleToolbar.setText("Estad??sticas");
//                selectedFragment = new AdministracionFragment();
            } else if (item.getItemId() == R.id.est) {
                selectedFragment = new EstadisticasFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
            }
            return true;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Snackbar.make(cl, "Jugador a??adido exitosamente", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Snackbar.make(cl, "Miembro del cuerpo t??cnico a??adido exitosamente", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        }

        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            Snackbar.make(cl, "Partido a??adido exitosamente", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        }

        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            Snackbar.make(cl, "Entrenamiento a??adido exitosamente", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        }

        if (requestCode == 5 && resultCode == Activity.RESULT_OK) {
            Snackbar.make(cl, "Perfil editado exitosamente", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        }

        if (requestCode == 6 && resultCode == Activity.RESULT_OK) {
            Snackbar.make(cl, "La contrase??a del equipo ha sido actualizada exitosamente", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.primary))
                    .show();
        }

    }

}