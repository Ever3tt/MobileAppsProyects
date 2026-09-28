package com.example.navegacion.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navegacion.R;
import com.example.navegacion.data.model.Pokemon;
import com.example.navegacion.data.model.PokemonResponse;
import com.example.navegacion.data.repository.PokemonRepository;
import com.example.navegacion.ui.adapter.PokemonAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento principal (Inicio) que consume la PokeAPI y muestra los Pokemon en un RecyclerView.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerPokemon;
    private CircularProgressIndicator progressIndicator;
    private LinearLayout errorContainer;
    private TextView tvError;
    private MaterialButton btnRetry;

    private PokemonAdapter adapter;
    private PokemonRepository repository;
    private Call<PokemonResponse> currentCall;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Enlazamos los controles visuales del layout XML
        recyclerPokemon = view.findViewById(R.id.recyclerPokemon);
        progressIndicator = view.findViewById(R.id.progressIndicator);
        errorContainer = view.findViewById(R.id.errorContainer);
        tvError = view.findViewById(R.id.tvError);
        btnRetry = view.findViewById(R.id.btnRetry);

        // 2. Inicializamos el adaptador y el repositorio
        adapter = new PokemonAdapter(this::mostrarPokemonSeleccionado);
        repository = new PokemonRepository();

        // 3. Configuramos el RecyclerView con LinearLayoutManager
        recyclerPokemon.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerPokemon.setHasFixedSize(true);
        recyclerPokemon.setAdapter(adapter);

        // 4. Configuramos el boton de reintentar
        btnRetry.setOnClickListener(v -> cargarPokemon());

        // 5. Cargamos los datos de la API
        cargarPokemon();
    }

    private void cargarPokemon() {
        mostrarCargando();

        // Solicitamos 30 Pokemon con un offset de 0
        currentCall = repository.obtenerPokemon(30, 0);

        currentCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, @NonNull Response<PokemonResponse> response) {
                if (!isAdded()) {
                    return;
                }

                PokemonResponse body = response.body();
                if (response.isSuccessful() && body != null && body.getResults() != null) {
                    adapter.actualizarDatos(body.getResults());
                    mostrarContenido();
                } else {
                    mostrarError("No fue posible obtener los Pokemon. Codigo HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonResponse> call, @NonNull Throwable t) {
                if (call.isCanceled() || !isAdded()) {
                    return;
                }
                mostrarError("Error de conexion. Verifique internet e intente nuevamente.");
            }
        });
    }

    private void mostrarPokemonSeleccionado(Pokemon pokemon) {
        Toast.makeText(
                requireContext(),
                "Selecciono: " + pokemon.getName(),
                Toast.LENGTH_SHORT
        ).show();
    }

    private void mostrarCargando() {
        progressIndicator.setVisibility(View.VISIBLE);
        recyclerPokemon.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
    }

    private void mostrarContenido() {
        progressIndicator.setVisibility(View.GONE);
        recyclerPokemon.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
    }

    private void mostrarError(String mensaje) {
        progressIndicator.setVisibility(View.GONE);
        recyclerPokemon.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        tvError.setText(mensaje);
    }

    @Override
    public void onDestroyView() {
        if (currentCall != null) {
            currentCall.cancel();
        }
        recyclerPokemon.setAdapter(null);
        super.onDestroyView();
    }
}
