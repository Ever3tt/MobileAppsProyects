package com.example.navegacion.data.model;

import java.util.List;

/**
 * Contenedor de respuesta para la PokeAPI.
 * La PokeAPI no retorna una lista directa sino un objeto JSON con metadatos y la lista en results.
 */
public class PokemonResponse {

    private int count;
    private String next;
    private String previous;
    private List<Pokemon> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }
}
