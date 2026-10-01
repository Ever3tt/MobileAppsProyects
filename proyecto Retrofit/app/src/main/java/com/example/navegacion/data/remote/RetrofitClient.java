package com.example.navegacion.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que gestiona la instancia unica (Singleton) de Retrofit para la aplicacion.
 */
public class RetrofitClient {

    // URL base de la PokeAPI. Obligatorio terminar con /
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private static Retrofit retrofit;

    private RetrofitClient() {
    }

    /**
     * Metodo estatico para obtener el servicio de la API configurado.
     */
    public static PokeApiService getService() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(PokeApiService.class);
    }
}
