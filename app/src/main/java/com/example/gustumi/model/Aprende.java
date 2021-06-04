package com.example.gustumi.model;

public class Aprende {

    String contenido;
    String titulo;
    String imgAprende1;
    String imgAprende2;

    /*CONSTRUCTORES*/
    public Aprende() {
    }



    public Aprende(String contenido, String titulo, String imgAprende, String imgAprende2) {
        this.contenido = contenido;
        this.titulo = titulo;
        this.imgAprende1 = imgAprende1;
        this.imgAprende2 = imgAprende2;
    }

    /*GETTERS & SETTERS*/

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImgAprende1() {
        return imgAprende1;
    }

    public void setImgAprende1(String imgAprende1) {
        this.imgAprende1 = imgAprende1;
    }

    public String getImgAprende2() {
        return imgAprende2;
    }

    public void setImgAprende2(String imgAprende2) {
        this.imgAprende2 = imgAprende2;
    }
}
