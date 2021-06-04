package com.example.gustumi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.gustumi.R;
import com.example.gustumi.adapters.MostrarRecetasAdapter;
import com.example.gustumi.adapters.RecetaAdapter;
import com.example.gustumi.model.Receta;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MostrarRecetasActivity extends AppCompatActivity {

    RecyclerView recyclerMostrarRecetas;
    MostrarRecetasAdapter mostrarRecetasAdapter;
    FirebaseFirestore firebaseFirestore;

    ImageView btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_recetas_activity_layout);

        recyclerMostrarRecetas = findViewById(R.id.recyclerMostrarRecetas);
        recyclerMostrarRecetas.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();

        //Consulta a la colección Recetas"
        Query query = firebaseFirestore.collection("Recetas");

        FirestoreRecyclerOptions<Receta> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Receta>()
                .setQuery(query, Receta.class).build();

        mostrarRecetasAdapter = new MostrarRecetasAdapter(firestoreRecyclerOptions, this);// la consulta (conjunto de opciones de los datos a mostrar) y el activity donde estoy
        mostrarRecetasAdapter.notifyDataSetChanged();
        recyclerMostrarRecetas.setAdapter(mostrarRecetasAdapter);

        /*Botón atrás*/
        btnAtras = (ImageView) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {//onClick btn atrás
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }//fin onCreate

    /*MÉTODO onStart PARA QUE TRAIGA LOS DATOS DE LA BD*/
    @Override
    protected void onStart() {
        super.onStart();
        mostrarRecetasAdapter.startListening();//Para que comience a mostrar las recetas
    }//FIN onStart()

    /*Método onStop para cuando la aplicación está en segundo plano, deje de consultar los datos*/
    @Override
    protected void onStop() {//Si es usuario no está dentro de la aplicación deje de consultar los datos
        super.onStop();
        mostrarRecetasAdapter.stopListening();//deja de escuchar los cambios
    }//fin onStop
}