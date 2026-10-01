package com.example.navegacion.data.model;

/**
 * Modelo basico que representa un Pokemon individual.
 * Contiene el nombre y la URL con sus detalles segun la respuesta de la PokeAPI.
 */
public class Pokemon {

    private String name;
    private String url;

    public Pokemon() {
    }

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
