package com.example.gustumi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.gustumi.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class ExplorarCategoriasActivity extends AppCompatActivity {

    FirebaseFirestore db;

    ImageView btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorar_categorias_activity_layout);

        btnAtras = (ImageView) findViewById(R.id.btnAtras);

        db = FirebaseFirestore.getInstance();

        btnAtras.setOnClickListener(new View.OnClickListener() {//onClick btn atrás
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }//fin onCreate


    /*--------------MÉTODOS--------------*/


    //-------Método onClick Arroces*/
    public void onClickArroces(View view) {

        Intent intentArroces = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Arroces";
        Bundle bundle = new Bundle();//Bundle para pasar los datos de la categoría elegida mediante el Intent
        bundle.putString("categoria", categoria);

        intentArroces.putExtras(bundle);

        startActivity(intentArroces);

    }//Fin onClickArroces


    //-------Método onClick Bowls
    public void onClickBowls(View view) {

        Intent intentBowls = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Bowls";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentBowls.putExtras(bundle);

        startActivity(intentBowls);
    }//Fin onClickBowls


    //-------Método onClick Carnes
    public void onClickCarnes(View view) {

        Intent intentCarnes = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Carnes";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentCarnes.putExtras(bundle);


        startActivity(intentCarnes);
    }//Fin onClickCarnes

    //-------Método onClick Desayunos
    public void onClickDesayunos(View view) {

        Intent intentDesayunos = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Desayunos";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentDesayunos.putExtras(bundle);


        startActivity(intentDesayunos);
    }//Fin onClickDesayunos


     //-------Método onClick En Poco Tiempo
    public void onClickEnPocoTiempo(View view) {

        Intent intentEnPocoTiempo = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "En poco tiempo";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentEnPocoTiempo.putExtras(bundle);

        startActivity(intentEnPocoTiempo);
    }//Fin onClickEnPocoTiempo


    //-------Método onClick Hamburguesas
    public void onClickHamburguesas(View view) {
        Intent intentHamburguesas = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Hamburguesas";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentHamburguesas.putExtras(bundle);

        startActivity(intentHamburguesas);
    }//Fin onClickHamburguesas

    //-------Método onClick Horno
    public void onClickHorno(View view) {
        Intent intentHorno = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Horno";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentHorno.putExtras(bundle);

        startActivity(intentHorno);
    }//Fin onClickHorno

    //-------Método onClick Legumbres
    public void onClickLegumbres(View view) {
        Intent intentLegumbres = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Legumbres";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentLegumbres.putExtras(bundle);

        startActivity(intentLegumbres);
    }//Fin onClickLegumbres

    //-------Método onClick Panes
    public void onClickPanes(View view) {
        Intent intentPanes = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Panes";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentPanes.putExtras(bundle);

        startActivity(intentPanes);
    }//Fin onClickPanes

    //-------Método onClick Pastas
    public void onClickPastas(View view) {
        Intent intentPastas = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Pastas";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentPastas.putExtras(bundle);

        startActivity(intentPastas);
    }//Fin onClickPastas

    //-------Método onClick Pescados y mariscos
    public void onClickPescadosYMariscos(View view) {
        Intent intentPescadosYMariscos = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Pescados y Mariscos";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentPescadosYMariscos.putExtras(bundle);

        startActivity(intentPescadosYMariscos);
    }//Fin onClickPescadosYMariscos

    //-------Método onClick Pizzas
    public void onClickPizzas(View view) {
        Intent intentPizzas = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Pizzas";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentPizzas.putExtras(bundle);

        startActivity(intentPizzas);
    }//Fin onClickPizzas


    //-------Método onClick Postres
    public void onClickPostres(View view) {
        Intent intentPostres = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Postres";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentPostres.putExtras(bundle);

        startActivity(intentPostres);
    }//Fin onClickPostres

    //-------Método onClick Sopas y cremas
    public void onClickSopasYCremas(View view) {
        Intent intentSopasYCremas = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Sopas y Cremas";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentSopasYCremas.putExtras(bundle);

        startActivity(intentSopasYCremas);
    }//Fin onClickSopasYCremas

    //-------Método onClick Tostadas
    public void onClickTostadas(View view) {
        Intent intentTostadas = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Tostadas";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentTostadas.putExtras(bundle);

        startActivity(intentTostadas);
    }//Fin onClickTostadas

    //-------Método onClick Untables y Salsas
    public void onClickUntablesYSalsas(View view) {
        Intent intentUntablesYSalsas = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Untables y Salsas";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentUntablesYSalsas.putExtras(bundle);

        startActivity(intentUntablesYSalsas);
    }//Fin onClickUntablesYSalsas


    //-------Método onClick Vegetariano
    public void onClickVegetariano(View view) {
        Intent intentVegetariano = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Vegetariano";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentVegetariano.putExtras(bundle);

        startActivity(intentVegetariano);
    }//Fin onClickVegetariano


    //-------Método onClick Verduras
    public void onClickVerduras(View view) {
        Intent intentVerduras = new Intent(this, ResultadoBusquedaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        String categoria = "Verduras";
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);

        intentVerduras.putExtras(bundle);

        startActivity(intentVerduras);
    }//Fin onClickVerduras

}//fin clase ExplorarCategoriasActivity