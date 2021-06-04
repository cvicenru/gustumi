package com.example.gustumi.model;

import java.util.List;

public class Receta {

    String imagen;
    String categoria;
    String nombreReceta;
    String tiempoElaboracion;
    List<String> ingredientes;
    List<String> elaboracion;



    /*CONSTRUCTORES*/
    public Receta() {
    }

    public Receta(String imagen, String categoria, String nombreReceta, String tiempoElaboracion, List<String> ingredientes, List<String> elaboracion) {

        this.imagen = imagen;
        this.categoria = categoria;
        this.nombreReceta = nombreReceta;
        this.tiempoElaboracion = tiempoElaboracion;
        this.ingredientes = ingredientes;
        this.elaboracion = elaboracion;
    }


    /*GETTERS & SETTERS*/

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public String getTiempoElaboracion() {
        return tiempoElaboracion;
    }

    public void setTiempoElaboracion(String tiempoElaboracion) {
        this.tiempoElaboracion = tiempoElaboracion;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getElaboracion() {
        return elaboracion;
    }

    public void setElaboracion(List<String> elaboracion) {
        this.elaboracion = elaboracion;
    }

}//fin clase Receta
