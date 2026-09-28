package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // URL del endpoint de la API pública que devuelve las tareas (TODOS) en formato JSON
    private static final String URL_API = "https://jsonplaceholder.typicode.com/todos";
    // Identificador para etiquetar y gestionar las peticiones en la cola de Volley
    private static final String REQUEST_TAG = "GET_TODOS";

    // Vistas de la interfaz gráfica
    private ListView lvTodos;
    private ProgressBar progressBar;
    private TextView tvEstado;

    // Cola de peticiones de Volley que administra las solicitudes HTTP asíncronas
    private RequestQueue requestQueue;

    // Lista de cadenas donde guardaremos la información formateada de cada tarea
    private final List<String> listaTodos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajustamos los márgenes de la vista según las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Enlazamos los controles del archivo XML con los atributos de Java
        inicializarVistas();

        // 2. Creamos la cola de peticiones con el contexto de la aplicación
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // 3. Ejecutamos la petición para consultar la API
        consumirApi();
    }

    /**
     * Vincula los elementos visuales definidos en el XML con los objetos Java.
     */
    private void inicializarVistas() {
        lvTodos = findViewById(R.id.lvTodos);
        progressBar = findViewById(R.id.progressBar);
        tvEstado = findViewById(R.id.tvEstado);
    }

    /**
     * Alterna la visibilidad entre el ProgressBar (cargando) y el ListView (contenido).
     * @param cargando true si la solicitud está en curso, false si ya terminó.
     */
    private void mostrarCargando(boolean cargando) {
        progressBar.setVisibility(cargando ? View.VISIBLE : View.GONE);
        lvTodos.setVisibility(cargando ? View.GONE : View.VISIBLE);
    }

    /**
     * Muestra el mensaje de error en el TextView y un Toast informativo.
     * @param mensaje Detalle del error ocurrido.
     */
    private void mostrarError(String mensaje) {
        mostrarCargando(false);
        tvEstado.setText(mensaje);
        tvEstado.setVisibility(View.VISIBLE);
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    /**
     * Procesa los posibles errores devueltos por la librería Volley.
     * @param error Objeto VolleyError con los detalles del fallo.
     */
    private void procesarError(VolleyError error) {
        String mensaje = (error != null) ? error.getMessage() : null;
        if (mensaje == null || mensaje.trim().isEmpty()) {
            mensaje = "verifique la conexión a internet.";
        }
        mostrarError("Error en la solicitud: " + mensaje);
    }

    /**
     * Realiza una petición GET asíncrona a la API usando JsonArrayRequest de Volley.
     */
    private void consumirApi() {
        mostrarCargando(true);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL_API,
                null,
                response -> {
                    // Limpiamos la lista para evitar elementos duplicados
                    listaTodos.clear();
                    try {
                        // Recorremos el arreglo JSON recibido
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject item = response.getJSONObject(i);
                            int id = item.getInt("id");
                            String titulo = item.getString("title");
                            boolean completado = item.getBoolean("completed");

                            // Construimos el texto con un formato claro y legible
                            String texto = "ID: " + id
                                    + "\nTítulo: " + titulo
                                    + "\nCompletado: "
                                    + (completado ? "Sí" : "No");

                            listaTodos.add(texto);
                        }

                        // Creamos un adaptador para poblar el ListView con la lista de textos
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_list_item_1,
                                listaTodos
                        );

                        lvTodos.setAdapter(adapter);
                        mostrarCargando(false);
                        tvEstado.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        mostrarError("No fue posible procesar la respuesta.");
                    }
                },
                this::procesarError
        );

        // Asignamos una etiqueta a la petición para poder cancelarla si la actividad se destruye
        request.setTag(REQUEST_TAG);
        requestQueue.add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Si el usuario sale de la actividad, cancelamos las peticiones pendientes para no malgastar recursos
        if (requestQueue != null) {
            requestQueue.cancelAll(REQUEST_TAG);
        }
    }
}