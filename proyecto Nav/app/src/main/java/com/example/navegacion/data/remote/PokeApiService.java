package com.example.navegacion.data.remote;

import com.example.navegacion.data.model.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfaz que define las operaciones HTTP disponibles contra la PokeAPI.
 * Retrofit genera automaticamente la implementacion de estos metodos en tiempo de ejecucion.
 */
public interface PokeApiService {

    @GET("pokemon")
    Call<PokemonResponse> getPokemon(
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
