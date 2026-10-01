package com.example.navegacion.data.repository;

import com.example.navegacion.data.model.PokemonResponse;
import com.example.navegacion.data.remote.PokeApiService;
import com.example.navegacion.data.remote.RetrofitClient;

import retrofit2.Call;

/**
 * El repositorio separa la obtencion de datos de la interfaz de usuario.
 */
public class PokemonRepository {

    private final PokeApiService service;

    public PokemonRepository() {
        this.service = RetrofitClient.getService();
    }

    public Call<PokemonResponse> obtenerPokemon(int limit, int offset) {
        return service.getPokemon(limit, offset);
    }
}
