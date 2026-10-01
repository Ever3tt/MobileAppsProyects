# MobileAppsProyects 📱

Repositorio con las prácticas de la materia de Aplicaciones Móviles. Acá subimos los proyectos que fuimos haciendo durante el curso, todos desarrollados en Android Studio con Java.

---

## 👥 Integrantes

- Juan Sebastian Prada Jurado
- Juan Camilo Angulo Camelo
- Jhon Sebastian Amado Duarte

🎓 Ingeniería de Software — Promoción 10 | Jornada Nocturna

---

## 📂 Proyectos

### proyecto Volley
Práctica de consumo de API REST usando la librería **Volley**. La app hace una petición GET a la API pública de JSONPlaceholder y trae una lista de tareas (TODOs) que se muestran en pantalla. Incluye un indicador de carga mientras trae los datos y manejo básico de errores por si falla la conexión.

Lo que aprendimos acá fue principalmente como manejar peticiones asíncronas con Volley, el uso de `JsonArrayRequest` y como cancelar peticiones cuando el usuario sale de la pantalla para no gastar recursos.

### proyecto Retrofit
Práctica más completa donde usamos **Retrofit** junto con OkHttp para consumir la PokeAPI. La app tiene navegación inferior con 3 secciones (Inicio, Favoritos e Información) y en la pantalla principal muestra un listado de Pokémon cargado desde la API en un RecyclerView.

A diferencia del proyecto de Volley, acá ya separamos el código en capas (modelo, repositorio, interfaz de red y UI) para tener una estructura más ordenada. También le agregamos un interceptor de OkHttp para ver los logs de las peticiones en el Logcat, lo cual fue bastante util para depurar.

---

## 🛠️ Tecnologías usadas

- Android Studio
- Java
- Volley 1.2.1
- Retrofit 3.0.0 + OkHttp 4.12.0 + Gson
- RecyclerView
- Material Design Components
- ConstraintLayout

---

## 📌 Notas

Los proyectos son prácticas puntuales de las librerías, no una app completa. Cada carpeta es independiente y se abre por separado desde Android Studio.
