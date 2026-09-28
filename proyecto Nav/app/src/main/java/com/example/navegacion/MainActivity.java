package com.example.navegacion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.navegacion.ui.FavoritesFragment;
import com.example.navegacion.ui.HomeFragment;
import com.example.navegacion.ui.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Actividad principal que administra la navegacion inferior (Bottom Navigation)
 * e intercambia los fragmentos en pantalla.
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializamos la vista de navegacion
        initObjects();

        // 2. Configuramos el listener para responder a los clics del menu
        configurarBottomNavigation();

        // 3. Cargamos la pantalla inicial de Inicio por defecto solo la primera vez
        if (savedInstanceState == null) {
            cargarFragment(new HomeFragment());
        }
    }

    /**
     * Vincula el BottomNavigationView del archivo XML.
     */
    private void initObjects() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    /**
     * Retorna la instancia del fragmento correspondiente segun el ID del item pulsado.
     */
    private Fragment obtenerFragment(int itemId) {
        if (itemId == R.id.navigation_home) {
            return new HomeFragment();
        }
        if (itemId == R.id.navigation_favorites) {
            return new FavoritesFragment();
        }
        if (itemId == R.id.navigation_info) {
            return new InfoFragment();
        }
        return null;
    }

    /**
     * Reemplaza el fragmento dentro del FragmentContainerView mediante FragmentTransaction.
     */
    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    /**
     * Configura el listener del BottomNavigationView para cambiar de fragmento.
     */
    private void configurarBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = obtenerFragment(item.getItemId());
            if (fragment == null) {
                return false;
            }
            cargarFragment(fragment);
            return true;
        });
    }
}